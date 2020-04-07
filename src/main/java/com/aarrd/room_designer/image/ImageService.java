package com.aarrd.room_designer.image;

import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.storage.IStorageService;
import com.aarrd.room_designer.storage.StorageProperties;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.*;

@Service
public class ImageService implements IImageService
{
    private IStorageService storageService;
    private final String ROOT_LOCATION;
    private final String IMAGE;
    private final String THUMBNAIL;

    private final IImageRepository imageRepository;
    private final IUserRepository userRepository;
    private final IItemRepository itemRepository;

    @Autowired
    public ImageService(IStorageService storageService, IUserRepository userRepository, StorageProperties storageProperties,
                        IImageRepository imageRepository, IItemRepository itemRepository)
    {
        this.storageService = storageService;
        this.ROOT_LOCATION = storageProperties.getROOT_LOCATION();
        this.IMAGE = storageProperties.getIMAGE();
        this.THUMBNAIL = storageProperties.getTHUMBNAIL();

        this.imageRepository = imageRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    /**
     * Handles images that are being uploaded by the user.
     * @param files upload file represented as a multipart file.
     * @param isThumbnail is thumbnail?
     * @param itemId ID of the item the image is related to.
     * @param principal currently logged in user.
     */
    @Override
    public void storeImage(List<MultipartFile> files, Boolean isThumbnail, Long itemId, Principal principal)
    {
        Long userId = userRepository.findByEmail(principal.getName()).getUserId();
        Item item = itemRepository.getOne(itemId);

        //Check file type...
        for(MultipartFile file : files) {

            System.out.println("ImageService :: Uploading file type: " + file.getContentType());

            String directory = ROOT_LOCATION + "\\" + userId + "\\" + itemId + "\\";
            if(isThumbnail)
            {
                storageService.store(file, userId, itemId, EnumSet.of(StorageTypeFlag.THUMBNAIL));
                directory = directory + THUMBNAIL + file.getOriginalFilename();
            }

            else {
                storageService.store(file, userId, itemId, EnumSet.of(StorageTypeFlag.IMAGE));
                directory = directory + IMAGE + file.getOriginalFilename();
            }

            //Insert new data into database.
            imageRepository.save(new Image(new Date(), directory, isThumbnail, item));
        }
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
     * Serve thumbnail to the client.
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
     * Server the thumbnails Id.
     * @param itemId Item id
     * @return Long Id of the thumbnail. -1 is returned if nothing is found.
     */
    @Override
    public Long fetchThumbnailId(Long itemId)
    {
        for (Image i : imageRepository.findImageByItemId(itemId))
        {
            if(i.getIsThumbnail())
                return i.getImageId();
        }
        return (long) -1;
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
}
