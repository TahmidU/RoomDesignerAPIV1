package com.aarrd.room_designer.image;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;


public interface IImageService
{
    void storeImage(MultipartFile file, Boolean isThumbnail, Long itemId, Principal principal);
    Resource serveImage(Long imageId);
    Resource serveThumbnail(Long itemId);
    HttpStatus delete(Long imageId, Boolean isThumbnail, Long itemId, Principal principal);
    List<Long> relevantImages(Long itemId);
    Integer numberOfImages(Long itemId);
}
