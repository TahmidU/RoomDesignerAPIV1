package com.aarrd.room_designer.model;

import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.EnumSet;
import java.util.List;

public interface IModelService
{
    void store(@RequestParam List<MultipartFile> file, Long itemId, Principal principal);
    byte[] serve(Long modelId);
    HttpStatus delete(Long modelId, Long itemId, Principal principal);
    Long relevantModel(Long itemId);
    Boolean modelExists(Long itemId);
}
