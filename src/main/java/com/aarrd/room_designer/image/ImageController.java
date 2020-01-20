package com.aarrd.room_designer.image;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import com.aarrd.room_designer.storage.StorageService;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.EnumSet;

@RestController
@RequestMapping(value = "/images")
public class ImageController
{
    private final StorageService storageService;

    @Autowired
    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

/*    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename)
    {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                file.getFilename() + "\"").body(file);
    }*/

    @PostMapping("/upload")
    public HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, Principal principal)
    {
        storageService.store(file, principal, EnumSet.of(StorageTypeFlag.IMAGE));
        return HttpStatus.CREATED;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
