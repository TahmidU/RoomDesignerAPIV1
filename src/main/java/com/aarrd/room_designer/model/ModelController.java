package com.aarrd.room_designer.model;

import com.aarrd.room_designer.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/models")
public class ModelController
{
    private final IModelService modelService;

    @Autowired
    public ModelController(IModelService modelService)
    {
        this.modelService = modelService;
    }

    /**
     * Serve model file to the client.
     * @param modelId (request parameter) ID of the model.
     * @return ResponseEntity containing resource.
     */
    @GetMapping(value = "/{modelId}/download", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<?> serverFile(@PathVariable(name = "modelId") Long modelId)
    {
        Log.printMsg(this.getClass(), "Sending model: " + modelId);
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
        Log.printMsg(this.getClass(), "Upload Model. Associate with: " + itemId);
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
    @DeleteMapping("/{modelId}")
    public HttpStatus handleDeletion(@PathVariable(name = "modelId") Long modelId, @RequestParam Long itemId, Principal principal)
    {
        Log.printMsg(this.getClass(), "Delete Model: " + modelId);
        return modelService.delete(modelId, itemId, principal);
    }
}
