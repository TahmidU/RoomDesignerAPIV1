package com.aarrd.room_designer.model;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface IModelController
{
    ResponseEntity<Resource> serverFile(@RequestParam Long modelId, Principal principal);
    HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, Long modelId, Principal principal);
    HttpStatus handleDeletion(@RequestParam Long modelId, @RequestParam Long itemId, Principal principal);
    ResponseEntity<Long> relevantModel(@RequestParam Long itemId);
    ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc);
}
