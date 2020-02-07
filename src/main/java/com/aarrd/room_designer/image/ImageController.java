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
@RequestMapping(value = "/image")
public class ImageController implements IImageController
{
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Retrieve image from server.
     * @param imageId (request parameter) ID of the items images are retrieved.
     * @return ResponseEntity containing the Resource (the image).
     */
    @GetMapping("/fetch-all")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serveImage(@RequestParam Long imageId)
    {
        Resource files = imageService.serveImage(imageId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(files);
    }

    /**
     * Retrieve the thumbnail for the item.
     * @param itemId (request parameter) ID of the item for the thumbnail.
     * @return ResponseEntity containing the Resource (the image).
     */
    @GetMapping("/fetch-thumbnail")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serveThumbnail(@RequestParam Long itemId)
    {
        Resource file = imageService.serveThumbnail(itemId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    /**
     * Handles images that are being uploaded by the user.
     * @param file (request parameter) upload file represented as a multipart file.
     * @param itemId (request parameter) ID of the item the image is related to.
     * @param isThumbnail (request parameter) is thumbnail?
     * @param principal currently logged in user.
     * @return HttpStatus
     */
    @PostMapping("/upload")
    @Override
    public HttpStatus handleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam Long itemId,
                                        @RequestParam Boolean isThumbnail, Principal principal)
    {
        imageService.storeImage(file, isThumbnail, itemId,principal);
        return HttpStatus.CREATED;
    }

    /**
     * Handles deletion of an image in the server.
     * @param imageId (request parameter) ID of the image being deleted.
     * @param isThumbnail (request parameter) is thumbnail?
     * @param itemId (request parameter) ID of the item the image is related to.
     * @param principal currently logged in user.
     * @return HttpStatus
     */
    @DeleteMapping("/delete")
    @Override
    public HttpStatus handleDeletion(@RequestParam Long imageId, @RequestParam Boolean isThumbnail,
                                     @RequestParam Long itemId, Principal principal)
    {
        return imageService.delete(imageId, isThumbnail, itemId, principal);
    }

    /**
     * ID of all the images related to the item.
     * @param itemId (request parameter) ID of the item the images are related to.
     * @return ReponseEntity containing a list of Long.
     */
    @GetMapping("/relevant")
    @Override
    public ResponseEntity<List<Long>> relevantImages(@RequestParam Long itemId)
    {
        return new ResponseEntity<List<Long>>(imageService.relevantImages(itemId), HttpStatus.OK);
    }

    /**
     * Number of images the item possesses.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing an integer.
     */
    @GetMapping("/user-amount")
    @Override
    public ResponseEntity<Integer> numberOfImages(Long itemId)
    {
        return new ResponseEntity<Integer>(imageService.numberOfImages(itemId), HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    @Override
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
