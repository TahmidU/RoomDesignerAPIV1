package com.aarrd.room_designer.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.security.Principal;
import java.util.EnumSet;
import java.util.stream.Stream;

public interface StorageService
{
    void init();
    void store(MultipartFile file, Principal principle, EnumSet<StorageTypeFlag> flags);
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
}
