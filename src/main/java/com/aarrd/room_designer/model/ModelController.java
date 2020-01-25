package com.aarrd.room_designer.model;

import com.aarrd.room_designer.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping(value = "/models")
public class ModelController implements IModelController
{
    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService)
    {
        this.modelService = modelService;
    }

    @GetMapping("/fetch")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serverFile(Long modelId, Principal principal)
    {
        Resource file = modelService.serve(modelId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    @PostMapping("/upload")
    @Override
    public HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long modelId, Principal principal)
    {
        modelService.store(file, modelId, principal);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/delete")
    @Override
    public HttpStatus handleDeletion(@RequestParam Long modelId, @RequestParam Long itemId, Principal principal)
    {
        return modelService.delete(modelId, itemId, principal);
    }

    @GetMapping("/relevant")
    @Override
    public ResponseEntity<Long> relevantModel(@RequestParam Long itemId)
    {
        return new ResponseEntity<Long>(modelService.relevantModel(itemId), HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
