package com.aarrd.room_designer.model;

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
@RequestMapping(value = "/model")
public class ModelController implements IModelController
{
    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService)
    {
        this.modelService = modelService;
    }

    /**
     * Serve model file to the client.
     * @param modelId (request parameter) ID of the model.
     * @param principal currently logged in user.
     * @return ResponseEntity containing resource.
     */
    @GetMapping("/fetch")
    @ResponseBody
    @Override
    public ResponseEntity<Resource> serverFile(@RequestParam Long modelId, Principal principal)
    {
        Resource file = modelService.serve(modelId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    /**
     * Handles model uploads to the server.
     * @param file (request parameter) multipart file.
     * @param modelId (request parameter) ID of the model.
     * @param principal currently logged in user.
     * @return HttpStatus.
     */
    @PostMapping("/upload")
    @Override
    public HttpStatus handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long modelId,
                                       Principal principal)
    {
        modelService.store(file, modelId, principal);
        return HttpStatus.CREATED;
    }

    /**
     * Handle deletion of the model.
     * @param modelId (request parameter) ID of the model.
     * @param itemId (request parameter) ID of the item.
     * @param principal currently logged in user.
     * @return HttpStatus.
     */
    @DeleteMapping("/remove")
    @Override
    public HttpStatus handleDeletion(@RequestParam Long modelId, @RequestParam Long itemId, Principal principal)
    {
        return modelService.delete(modelId, itemId, principal);
    }

    /**
     * Retrieve the model associated with the item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing long (id of the model).
     */
    @GetMapping("/relevant")
    @Override
    public ResponseEntity<Long> relevantModel(@RequestParam Long itemId)
    {
        return new ResponseEntity<Long>(modelService.relevantModel(itemId), HttpStatus.OK);
    }

    /**
     * Check if model exists for the given item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containg a boolean.
     */
    @GetMapping("/exists")
    @Override
    public ResponseEntity<Boolean> modelExists(Long itemId)
    {
        return new ResponseEntity<Boolean>(modelService.modelExists(itemId), HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
