package com.aarrd.room_designer.model;

import com.aarrd.room_designer.image.IImageRepository;
import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.IItemService;
import com.aarrd.room_designer.item.statistic.download.IItemDownloadRepository;
import com.aarrd.room_designer.item.statistic.download.ItemDownload;
import com.aarrd.room_designer.storage.IStorageService;
import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.StorageProperties;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.IUserService;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;

@Service
public class ModelService implements IModelService
{
    private IStorageService storageService;
    private final String ROOT_LOCATION;
    private final String MODEL;

    private final IModelRepository modelRepository;
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;
    private final IItemDownloadRepository itemDownloadRepository;

    private ArrayList<String> permissibleTypes = new ArrayList<>();

    @Autowired
    public ModelService(IStorageService storageService, StorageProperties storageProperties,
                        IModelRepository modelRepository, IItemRepository itemRepository, IUserRepository userRepository,
                        IItemDownloadRepository itemDownloadRepository)
    {
        this.storageService = storageService;
        this.ROOT_LOCATION = storageProperties.getROOT_LOCATION();
        this.MODEL = storageProperties.getMODEL();

        this.modelRepository = modelRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemDownloadRepository = itemDownloadRepository;

        permissibleTypes.add("model/gltf+json");
    }

    @Override
    public void store(@RequestParam("file") MultipartFile file, Long itemId, Principal principal)
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

        if(modelRepository.findByItemId(itemId) != null)
            throw new StorageException("A Model already exists.");

        Long userId = userRepository.findByEmail(principal.getName()).getUserId();
        storageService.store(file, userId, itemId,EnumSet.of(StorageTypeFlag.MODEL));

        //Insert new data into database.
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + MODEL + itemId + file.getOriginalFilename();
        modelRepository.save(new Model(directory, itemRepository.getOne(itemId)));
    }

    @Override
    public Resource serve(Long modelId)
    {
        Model model = modelRepository.getOne(modelId);
        System.out.println("ModelService :: Serving " + model.getModelDirectory());
        itemDownloadRepository.save(new ItemDownload(new Date(), modelRepository.getOne(modelId).getItem()));
        return storageService.loadResource(model.getModelDirectory());
    }

    @Override
    public HttpStatus delete(Long modelId, Long itemId, Principal principal)
    {
        User user = itemRepository.getOne(itemId).getUser();
        if(!(user.getEmail()).equals(principal.getName()))
            return HttpStatus.UNAUTHORIZED;

        storageService.delete(modelRepository.getOne(modelId).getModelDirectory());
        modelRepository.delete(modelRepository.getOne(modelId));
        return HttpStatus.OK;
    }

    @Override
    public Long relevantModel(Long itemId)
    {
        return modelRepository.findByItemId(itemId).getModelId();
    }

    @Override
    public Boolean modelExists(Long itemId) {
        return modelRepository.findByItemId(itemId) != null;
    }
}
