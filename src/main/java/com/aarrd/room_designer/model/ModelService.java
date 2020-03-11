package com.aarrd.room_designer.model;

import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.item.statistic.download.IItemDownloadRepository;
import com.aarrd.room_designer.item.statistic.download.ItemDownload;
import com.aarrd.room_designer.storage.IStorageService;
import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.StorageProperties;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    }

    /**
     * Handle storing model.
     * @param files list of multipart files.
     * @param itemId ID of the item.
     * @param principal currently logged in user.
     */
    @Override
    public void store(@RequestParam List<MultipartFile> files, Long itemId, Principal principal)
    {

        if(modelRepository.findByItemId(itemId) != null)
            throw new StorageException("A Model already exists.");

        if(files.size() < 2)
            throw new StorageException("Missing files.");

        Long userId = userRepository.findByEmail(principal.getName()).getUserId();
        for(MultipartFile file : files)
            storageService.store(file, userId, itemId,EnumSet.of(StorageTypeFlag.MODEL));

        //Insert new data into database.
        Item item = itemRepository.getOne(itemId);
        item.setHasModel(true);
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + itemId + "\\" + MODEL;
        itemRepository.save(item);
        modelRepository.save(new Model(directory, item));
    }

    /**
     * Retrieve and serve file to client.
     * @param modelId ID of the model.
     * @return Resource (the model).
     */
    @Override
    public byte[] serve(Long modelId)
    {
        Model model = modelRepository.getOne(modelId);
        System.out.println("ModelService :: Serving " + modelId);
        itemDownloadRepository.save(new ItemDownload(new Date(), modelRepository.getOne(modelId).getItem()));

        String[] pathNames = (new File(model.getDirectory())).list();
        try {
            return storageService.loadMultipleResourcesInZip(pathNames, modelId.toString(), model.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handle deletion of the model.
     * @param modelId ID of the model.
     * @param itemId ID of the item.
     * @param principal currently logged in user.
     * @return HttpStatus.
     */
    @Override
    public HttpStatus delete(Long modelId, Long itemId, Principal principal)
    {
        Item item = itemRepository.getOne(itemId);
        User user = item.getUser();
        if(!(user.getEmail()).equals(principal.getName()))
            return HttpStatus.UNAUTHORIZED;


        storageService.delete(ROOT_LOCATION+"\\"+user.getUserId()+"\\"+itemId+"\\"+MODEL);

        item.setHasModel(false);
        itemRepository.save(item);
        modelRepository.delete(modelRepository.getOne(modelId));
        return HttpStatus.OK;
    }

    /**
     * Find the id of the model for a given item.
     * @param itemId ID of the item.
     * @return Long (id of the model).
     */
    @Override
    public Long relevantModel(Long itemId)
    {
        return modelRepository.findByItemId(itemId).getModelId();
    }

    /**
     * Check if a model exists for a given model.
     * @param itemId ID of the item.
     * @return Boolean.
     */
    @Override
    public Boolean modelExists(Long itemId) {
        return modelRepository.findByItemId(itemId) != null;
    }
}
