package com.aarrd.room_designer.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Data
public class StorageProperties
{
    private String ROOT_LOCATION = "data";
    private String IMAGE = "images\\";
    private String MODEL = "models\\";
}
