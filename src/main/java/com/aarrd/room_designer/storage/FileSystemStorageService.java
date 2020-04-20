package com.aarrd.room_designer.storage;

import com.aarrd.room_designer.user.UserService;
import com.aarrd.room_designer.util.Log;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Primary
public class FileSystemStorageService implements IStorageService
{
    private final Path ROOT_LOCATION;
    private final String IMAGE;
    private final String MODEL;
    private final String THUMBNAIL;
    
    @Autowired
    public FileSystemStorageService(StorageProperties properties)
    {
        this.ROOT_LOCATION = Paths.get(properties.getROOT_LOCATION());
        this.IMAGE = properties.getIMAGE();
        this.MODEL = properties.getMODEL();
        this.THUMBNAIL = properties.getTHUMBNAIL();
    }

    /**
     * Initialise the root location.
     */
    @Override
    public void init() {
        try
        {
            if(!Files.exists(ROOT_LOCATION)) {
                Log.printMsg(this.getClass(), "Creating new data directory...");
                Files.createDirectory(ROOT_LOCATION);
                Log.printMsg(this.getClass(), "New data directory created!");
            }else
                Log.printMsg(this.getClass(), "Directory exists already.");
        }catch (IOException e)
        {
            throw new StorageException("Could not initialize storage, ", e);
        }
    }

    /**
     * Store the multipart file.
     * @param file multipart file.
     * @param userId ID of the user.
     * @param itemId ID of the item.
     * @param flags IMAGE or MODEL.
     */
    @Override
    public void store(MultipartFile file, Long userId, Long itemId, EnumSet<StorageTypeFlag> flags)
    {
        String location = ROOT_LOCATION.toString() + "\\" + userId + "\\" + itemId;

        //Check which flag is set.
        if(flags.contains(StorageTypeFlag.IMAGE))
            location = location + "\\" + IMAGE;
        else if(flags.contains(StorageTypeFlag.MODEL))
            location = location + "\\" + MODEL;
        else if(flags.contains(StorageTypeFlag.THUMBNAIL))
            location = location + "\\" + THUMBNAIL;

        else
            throw new StorageException("Failed to store file. Problem naming the file. " + file.getOriginalFilename());

        Path locationPath = Paths.get(location);
        Log.printMsg(this.getClass(), "Storing file at: " + locationPath);

        try
        {
            if(file.isEmpty())
            {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }else if(!Files.exists(locationPath))
            {
                Files.createDirectories(locationPath);
            }
            Files.copy(file.getInputStream(), locationPath.resolve(file.getOriginalFilename()));
        }catch (IOException e)
        {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    /**
     * Load the paths of everything from the root location.
     * @return Stream containing paths.
     */
    @Override
    public Stream<Path> loadAll()
    {
        try
        {
            Log.printMsg(this.getClass(), "Loading All files...");
            return Files.walk(ROOT_LOCATION, 1)
                    .filter(path -> !path.equals(ROOT_LOCATION))
                    .map(ROOT_LOCATION::relativize);
        }catch (IOException e)
        {
            throw new StorageException("Failed to read stored files ", e);
        }
    }

    /**
     * Load the resource.
     * @param filename path of the file as well as its name.
     * @return Resource.
     */
    @Override
    public Resource loadResource(String filename) {
        try
        {
            Log.printMsg(this.getClass(), "Fetch resource from storage: " + filename);
            Path file = Paths.get(filename);
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

    /**
     * Go To file directory, zip the contents and return the zip.
     * @param pathNames content of path.
     * @param zipName name of zip.
     * @param directory directory.
     * @return zip in byte array.
     * @throws IOException
     */
    @Override
    public byte[] loadMultipleResourcesInZip(String[] pathNames, String zipName, String directory) throws IOException {

        Log.printMsg(this.getClass(), "Retriving from storage and zipping: " + directory);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        for(String pathName : pathNames)
        {
            System.out.println("File System :: " + directory+"\\"+pathName);

            FileInputStream fileInputStream = new FileInputStream(new File(directory + "\\" +pathName));
            ZipEntry zipEntry = new ZipEntry(pathName);
            zipOutputStream.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length=fileInputStream.read(bytes)) >= 0)
                zipOutputStream.write(bytes,0,length);
            fileInputStream.close();
        }
        zipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Delete the resource.
     * @param path path of the file as well as its name.
     */
    @Override
    public void delete(String path)
    {
        Log.printMsg(this.getClass(), "Delete file path: " + path);
        Path filePath = Paths.get(path);
        if(Files.exists(filePath))
        {
            try {
                FileUtils.deleteDirectory(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            throw new StorageException("Deletion failed. File "+path+" does not exist.");

    }
}
