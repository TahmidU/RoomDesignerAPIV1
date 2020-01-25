package com.aarrd.room_designer.model;

import com.aarrd.room_designer.storage.StorageException;
import com.aarrd.room_designer.storage.StorageTypeFlag;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.EnumSet;

public interface IModelService
{
    void store(@RequestParam("file") MultipartFile file, Principal principal);
    Resource serve(String filename, Principal principal);
    void delete(String filename, Principal principal);
}
