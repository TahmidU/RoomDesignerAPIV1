package com.aarrd.room_designer.image;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;


public interface IImageService
{
    void store(@RequestParam("file") MultipartFile file, Principal principal);
    Resource serve(String filename, Principal principal);
    void delete(String filename, Principal principal);
}
