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
    }

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

    @Override
    public void storeThumbnail(MultipartFile file, Long itemId, Principal principal)
    {
        storeImage(file, true, itemId, principal);
    }

    @Override
    public Resource serveImage(Long imageId)
    {
        String directory = imageRepository.getOne(imageId).getImageDirectory();
        System.out.println("ImageService :: Serving " + directory);
        return storageService.loadResource(directory);
    }

    @Override
    public Resource serveThumbnail(Long itemId)
    {
        return storageService.loadResource(imageRepository.findDirByItemId(itemId));
    }

    @Override
    public HttpStatus delete(Long imageId, Long itemId, Principal principal)
    {
        User user = itemRepository.getOne(itemId).getUser();
        if(!(user.getEmail()).equals(principal.getName()))
            return HttpStatus.UNAUTHORIZED;

        storageService.delete(imageRepository.getOne(imageId).getImageDirectory());
        imageRepository.delete(imageRepository.getOne(imageId));
        return HttpStatus.OK;
    }

    @Override
    public List<Long> relevantImages(Long itemId)
    {
        List<Long> imageIds = new ArrayList<>();
        for (Image i : imageRepository.findByItemId(itemId))
            imageIds.add(i.getImageId());
        return imageIds;
    }
}
