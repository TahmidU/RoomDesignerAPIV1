package com.aarrd.room_designer.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.security.Principal;
import java.util.EnumSet;
import java.util.stream.Stream;

public interface IStorageService
{
    void init();
    void store(MultipartFile file, Long Id, EnumSet<StorageTypeFlag> flags);
    Stream<Path> loadAll();
    Resource loadAsResource(String path);
    void delete(String path);
}
