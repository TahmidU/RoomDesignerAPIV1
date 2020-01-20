package com.aarrd.room_designer.storage;

import com.aarrd.room_designer.user.UserService;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
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
public class FileSystemStorageService implements StorageService
{
    private final Path ROOT_LOCATION;
    private final String IMAGE = "images\\";
    private final String MODEL = "models\\";

    private UserService userService;

    private ArrayList<String> permissibleTypes;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, UserService userService, ArrayList<String> permissibleTypes) {
        this.ROOT_LOCATION = Paths.get(properties.getLocation());
        this.userService = userService;
        this.permissibleTypes = permissibleTypes;
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
    public void store(MultipartFile file, Principal principal, EnumSet<StorageTypeFlag> flags)
    {
        String location = ROOT_LOCATION.toString() + "\\" + userService.getID(principal.getName());

        //Check if the file has a permissible content type.
        System.out.println("FileSystemStorageService :: Uploading file type: " + file.getContentType());
        boolean typeFound = false;
        for(int i = 0; i < permissibleTypes.size(); i++)
        {
            if(permissibleTypes.get(i).equals(file.getContentType()))
                typeFound = true;
        }
        if(!typeFound)
            throw new StorageException("File is not an acceptable type: " + file.getOriginalFilename());

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
    public Path load(String filename) {
        return ROOT_LOCATION.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try
        {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new StorageFileNotFoundException("Could not read file: " + filename);

        }catch (MalformedURLException e)
        {
            throw new StorageFileNotFoundException("Could not read file " + filename, e);
        }
    }

    @Override
    public void deleteAll()
    {
        FileSystemUtils.deleteRecursively(ROOT_LOCATION.toFile());
    }
}
