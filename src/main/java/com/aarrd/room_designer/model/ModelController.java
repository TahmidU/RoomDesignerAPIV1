package com.aarrd.room_designer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/model")
public class ModelController
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
     * @return ResponseEntity containing resource.
     */
    @GetMapping(value = "/fetch", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<?> serverFile(@RequestParam Long modelId)
    {
        return new ResponseEntity<>(modelService.serve(modelId), HttpStatus.OK);
    }

    /**
     * Handles model uploads to the server.
     * @param files (request parameter) list of multipart file.
     * @param itemId (request parameter) ID of the model.
     * @param principal currently logged in user.
     * @return HttpStatus.
     */
    @PostMapping("/upload")
    public HttpStatus handleFileUpload(@RequestParam("file") List<MultipartFile> files, @RequestParam Long itemId,
                                       Principal principal)
    {
        modelService.store(files, itemId, principal);
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
    public ResponseEntity<Long> relevantModel(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(modelService.relevantModel(itemId), HttpStatus.OK);
    }

    /**
     * Check if model exists for the given item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containg a boolean.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> modelExists(Long itemId)
    {
        return new ResponseEntity<>(modelService.modelExists(itemId), HttpStatus.OK);
    }
}
