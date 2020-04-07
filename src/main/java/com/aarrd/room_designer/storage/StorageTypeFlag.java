package com.aarrd.room_designer.storage;

import java.util.EnumSet;

public enum StorageTypeFlag
{
    IMAGE, MODEL, THUMBNAIL;

    public static final EnumSet<StorageTypeFlag> ALL_OPTIONS = EnumSet.allOf(StorageTypeFlag.class);
}
