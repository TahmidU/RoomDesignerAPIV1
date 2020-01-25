package com.aarrd.room_designer.model;

import com.aarrd.room_designer.image.IImageRepository;
import com.aarrd.room_designer.storage.IStorageService;
import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.StorageProperties;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import com.aarrd.room_designer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;

@Service
public class ModelService implements IModelService
{
    private IStorageService storageService;
    private final String ROOT_LOCATION;
    private final String MODEL;
    private UserService userService;

    private IModelRepository modelRepository;

    private ArrayList<String> permissibleTypes = new ArrayList<>();

    @Autowired
    public ModelService(IStorageService storageService, UserService userService, StorageProperties storageProperties,
                        IModelRepository modelRepository)
    {
        this.storageService = storageService;
        this.ROOT_LOCATION = storageProperties.getROOT_LOCATION();
        this.MODEL = storageProperties.getMODEL();

        this.userService = userService;

        this.modelRepository = modelRepository;

        permissibleTypes.add("model/gltf+json");
    }

    public void store(@RequestParam("file") MultipartFile file, Principal principal)
    {
        //Check file type...
        System.out.println("ImageService :: Uploading file type: " + file.getContentType());
        boolean typeFound = false;
        for (String permissibleType : permissibleTypes) {
            if (permissibleType.equals(file.getContentType()))
                typeFound = true;
        }
        if(!typeFound)
            throw new StorageException("File is not an acceptable type: " + file.getOriginalFilename());

        Long userId = userService.getID(principal.getName());
        storageService.store(file, userId, EnumSet.of(StorageTypeFlag.MODEL));

        //Insert new data into database.
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + MODEL + file.getOriginalFilename();
        //modelRepository.save(new Image(new Date(), directory));
    }

    public Resource serve(String filename, Principal principal)
    {
        Long userId = userService.getID(principal.getName());
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + MODEL + filename;
        System.out.println("ModelService :: Serving " + directory);
        return storageService.loadAsResource(directory);
    }

    public void delete(String filename, Principal principal)
    {
        Long userId = userService.getID(principal.getName());
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + MODEL + filename;
        storageService.delete(directory);
    }
}
