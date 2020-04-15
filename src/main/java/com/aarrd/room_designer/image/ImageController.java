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
@RequestMapping(value = "/api/image")
public class ImageController
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
    @GetMapping("/fetch-image")
    @ResponseBody
    public ResponseEntity<?> serveImage(@RequestParam Long imageId)
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
    public ResponseEntity<?> serveThumbnail(@RequestParam Long itemId)
    {
        Resource file = imageService.serveThumbnail(itemId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    /**
     * Fetch the id of the thumbnail by Item Id.
     * @param itemId (request parameter) ID of the item for the thumbnail.
     * @return
     */
    @GetMapping("/fetch-thumbnail-id")
    public ResponseEntity<?> fetchThumbnailId(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(imageService.fetchThumbnailId(itemId), HttpStatus.OK);
    }

    /**
     * Handles images that are being uploaded by the user.
     * @param files (request parameter) upload file represented as a multipart file.
     * @param itemId (request parameter) ID of the item the image is related to.
     * @param isThumbnail (request parameter) is thumbnail?
     * @param principal currently logged in user.
     * @return HttpStatus
     */
    @PostMapping("/upload")
    public HttpStatus handleImageUpload(@RequestParam("file") List<MultipartFile> files, @RequestParam Long itemId,
                                        @RequestParam Boolean isThumbnail, Principal principal)
    {
        imageService.storeImage(files, isThumbnail, itemId,principal);
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
    public HttpStatus handleDeletion(@RequestParam Long imageId, @RequestParam Boolean isThumbnail,
                                     @RequestParam Long itemId, Principal principal)
    {
        return imageService.delete(imageId, isThumbnail, itemId, principal);
    }

}
