package com.aarrd.room_designer.model;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IModelController
{
    ResponseEntity<?> serverFile(@RequestParam Long modelId);
    HttpStatus handleFileUpload(@RequestParam List<MultipartFile> files,@RequestParam Long itemId, Principal principal);
    HttpStatus handleDeletion(@RequestParam Long modelId, @RequestParam Long itemId, Principal principal);
    ResponseEntity<Long> relevantModel(@RequestParam Long itemId);
    ResponseEntity<Boolean> modelExists(@RequestParam Long itemId);
    ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc);
}
