package com.aarrd.room_designer.image;

import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.IItemService;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.IStorageService;
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

import java.io.File;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ImageService implements IImageService
{
    private IStorageService storageService;
    private final String ROOT_LOCATION;
    private final String IMAGE;

    private final IImageRepository imageRepository;
    private final IUserRepository userRepository;
    private final IItemRepository itemRepository;

    private ArrayList<String> permissibleTypes = new ArrayList<>();

    @Autowired
    public ImageService(IStorageService storageService, IUserRepository userRepository, StorageProperties storageProperties,
                        IImageRepository imageRepository, IItemRepository itemRepository)
    {
        this.storageService = storageService;
        this.ROOT_LOCATION = storageProperties.getROOT_LOCATION();
        this.IMAGE = storageProperties.getIMAGE();

        this.imageRepository = imageRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;

        permissibleTypes.add("image/jpeg");
        permissibleTypes.add("image/png");
        permissibleTypes.add("image/webp");
    }

    /**
     * Handles images that are being uploaded by the user.
     * @param file upload file represented as a multipart file.
     * @param isThumbnail is thumbnail?
     * @param itemId ID of the item the image is related to.
     * @param principal currently logged in user.
     */
    @Override
    public void storeImage(MultipartFile file, Boolean isThumbnail, Long itemId, Principal principal)
    {
        Long userId = userRepository.findByEmail(principal.getName()).getUserId();
        Item item = itemRepository.getOne(itemId);

        //Check file type...
        System.out.println("ImageService :: Uploading file type: " + file.getContentType());
        boolean typeFound = false;
        for (String permissibleType : permissibleTypes)
        {
            if (permissibleType.equals(file.getContentType()))
                typeFound = true;
        }
        if(!typeFound)
            throw new StorageException("File is not an acceptable type: " + file.getOriginalFilename());

        storageService.store(file, userId, itemId, EnumSet.of(StorageTypeFlag.IMAGE));

        //Insert new data into database.
        String directory = ROOT_LOCATION + "\\" + userId + "\\" + itemId + "\\" + IMAGE + file.getOriginalFilename();
        imageRepository.save(new Image(new Date(), directory, isThumbnail, item));
    }

    /**
     * Serve the image to the client.
     * @param imageId ID of the image being served.
     * @return Resource (the image).
     */
    @Override
    public Resource serveImage(Long imageId)
    {
        String directory = imageRepository.findDirByImageId(imageId);
        System.out.println("ImageService :: Serving " + directory);
        return storageService.loadResource(directory);
    }

    /**
     * Server thumbnail to the client.
     * @param itemId ID of the thumbnails item.
     * @return Resource (the image).
     */
    @Override
    public Resource serveThumbnail(Long itemId)
    {
        String directory = imageRepository.findDirByItemIdAndThumbnail(itemId);
        System.out.println("ImageService :: Serving " + directory);
        return storageService.loadResource(directory);
    }

    /**
     * Delete image from server.
     * @param imageId Id of the image to be deleted. If the image is a thumbnail this is redundant.
     * @param isThumbnail is Thumbnail?
     * @param itemId Id of the item the image is associated with.
     * @param principal currently logged in user.
     * @return HttpStatus.
     */
    @Override
    public HttpStatus delete(Long imageId, Boolean isThumbnail, Long itemId, Principal principal)
    {
        User user = itemRepository.getOne(itemId).getUser();
        if(!(user.getEmail()).equals(principal.getName()))
            return HttpStatus.UNAUTHORIZED;

        //If its a thumbnail the imageId is not used and becomes redundant.
        Image image;
        if(!isThumbnail)
            image = imageRepository.getOne(imageId); // normal image...
        else
            image = imageRepository.findThumbnailImage(itemId); //Thumbnail...

        System.out.println("ImageService :: Deleting " + image.getImageDirectory());
        storageService.delete(image.getImageDirectory());
        imageRepository.delete(image);
        return HttpStatus.OK;
    }

    /**
     * Retrieve all image ids associated with item.
     * @param itemId Id of the item.
     * @return List of longs (image ids).
     */
    @Override
    public List<Long> relevantImages(Long itemId)
    {
        System.out.println("ImageService :: Retrieving relevant images for " + itemId);
        return new ArrayList<>(imageRepository.findImageIdByItemId(itemId));
    }

    /**
     * Retrieve the number of images related to item.
     * @param itemId Id of the item.
     * @return Integer.
     */
    @Override
    public Integer numberOfImages(Long itemId)
    {
        System.out.println("ImageService :: Retrieving num of images for " + itemId);
        return imageRepository.findImageIdByItemId(itemId).size();
    }
}
