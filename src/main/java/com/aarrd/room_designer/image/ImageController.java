package com.aarrd.room_designer.image;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/images")
public class ImageController implements IImageController
{
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/fetch-all")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serveImage(@RequestParam Long itemId)
    {
        Resource files = imageService.serveImage(itemId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(files);
    }

    @GetMapping("/fetch-thumbnail")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serveThumbnail(@RequestParam Long itemId)
    {
        Resource file = imageService.serveThumbnail(itemId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    @PostMapping("/upload-image")
    @Override
    public HttpStatus handleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam Long itemId,
                                       Principal principal)
    {
        imageService.storeImage(file, false, itemId,principal);
        return HttpStatus.CREATED;
    }

    @PostMapping("/upload-thumbnail-image")
    @Override
    public HttpStatus handleThumbnailUpload(MultipartFile file, Long itemId, Principal principal)
    {
        imageService.storeThumbnail(file, itemId, principal);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/delete-image")
    @Override
    public HttpStatus handleDeletion(@RequestParam Long imageId, @RequestParam Long itemId, Principal principal)
    {
        return imageService.delete(imageId, itemId, principal);
    }

    @GetMapping("/relevant")
    @Override
    public ResponseEntity<List<Long>> relevantImages(Long itemId)
    {
        return new ResponseEntity<List<Long>>(imageService.relevantImages(itemId), HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    @Override
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
