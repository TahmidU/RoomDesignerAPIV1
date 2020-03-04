package com.aarrd.room_designer.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.EnumSet;
import java.util.stream.Stream;

public interface IStorageService
{
    void init();
    void store(MultipartFile file, Long userId, Long itemId, EnumSet<StorageTypeFlag> flags);
    Stream<Path> loadAll();
    Resource loadResource(String path);
    byte[] loadMultipleResourcesInZip(String[] pathNames, String zipName, String directory) throws IOException;
    void delete(String path);
}
