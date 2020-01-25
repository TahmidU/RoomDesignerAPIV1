package com.aarrd.room_designer.image;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface IImageController
{
    ResponseEntity<Resource> serverFile(@PathVariable String filename, Principal principal);
    HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, Principal principal);
    HttpStatus handleDeletion(@PathVariable String filename, Principal principal);
    ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc);
}
