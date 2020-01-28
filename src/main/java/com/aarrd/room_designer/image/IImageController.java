package com.aarrd.room_designer.image;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IImageController
{
    ResponseEntity<Resource> serveImage(@RequestParam Long itemId);
    ResponseEntity<Resource> serveThumbnail(@RequestParam Long itemId);
    HttpStatus handleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam Long itemId, Principal principal);
    HttpStatus handleThumbnailUpload(@RequestParam("file") MultipartFile file, @RequestParam Long itemId, Principal principal);
    HttpStatus handleDeletion(@RequestParam Long imageId, @RequestParam Long itemId, Principal principal);
    ResponseEntity<List<Long>> relevantImages(@RequestParam Long itemId);
    ResponseEntity<Integer> numberOfImages(@RequestParam Long itemId);
    ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc);
}
