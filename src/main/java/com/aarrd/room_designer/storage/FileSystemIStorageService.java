package com.aarrd.room_designer.storage;

import com.aarrd.room_designer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileSystemIStorageService implements IStorageService
{
    private final Path ROOT_LOCATION;
    private final String IMAGE;
    private final String MODEL;

    @Autowired
    public FileSystemIStorageService(StorageProperties properties)
    {
        this.ROOT_LOCATION = Paths.get(properties.getROOT_LOCATION());
        this.IMAGE = properties.getIMAGE();
        this.MODEL = properties.getMODEL();
    }

    @Override
    public void init() {
        try
        {
            if(!Files.exists(ROOT_LOCATION))
                Files.createDirectory(ROOT_LOCATION);
        }catch (IOException e)
        {
            throw new StorageException("Could not initialize storage, ", e);
        }
    }

    @Override
    public void store(MultipartFile file, Long Id, EnumSet<StorageTypeFlag> flags)
    {
        String location = ROOT_LOCATION.toString() + "\\" + Id;

        //Check which flag is set.
        if(flags.contains(StorageTypeFlag.IMAGE))
            location = location + "\\" + IMAGE;
        else if(flags.contains(StorageTypeFlag.MODEL))
            location = location + "\\" + MODEL;
        else
            throw new StorageException("Failed to store file. Problem naming the file. " + file.getOriginalFilename());

        Path locationPath = Paths.get(location);
        System.out.println(location);

        try
        {
            if(file.isEmpty())
            {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }else if(!Files.exists(locationPath))
            {
                Files.createDirectories(locationPath);
            }
            System.out.println(file.getContentType());
            Files.copy(file.getInputStream(), locationPath.resolve(file.getOriginalFilename()));
        }catch (IOException e)
        {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll()
    {
        try
        {
            return Files.walk(ROOT_LOCATION, 1)
                    .filter(path -> !path.equals(ROOT_LOCATION))
                    .map(ROOT_LOCATION::relativize);
        }catch (IOException e)
        {
            throw new StorageException("Failed to read stored files ", e);
        }
    }

    @Override
    public Resource loadAsResource(String path) {
        try
        {
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new StorageFileNotFoundException("Could not read file: " + path);

        }catch (MalformedURLException e)
        {
            throw new StorageFileNotFoundException("Could not read file " + path, e);
        }
    }

    @Override
    public void delete(String path)
    {
        Path filePath = Paths.get(path);
        if(Files.exists(filePath)) {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            throw new StorageException("Deletion failed. File "+path+" does not exist.");
    }
}
