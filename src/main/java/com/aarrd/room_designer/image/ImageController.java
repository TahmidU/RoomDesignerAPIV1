package com.aarrd.room_designer.image;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping(value = "/images")
public class ImageController implements IImageController
{
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename, Principal principal)
    {
        Resource file = imageService.serve(filename, principal);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, Principal principal)
    {
        imageService.store(file, principal);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("delete/{filename:.+}")
    public HttpStatus handleDeletion(@PathVariable String filename, Principal principal)
    {
        imageService.delete(filename, principal);
        return HttpStatus.OK;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
