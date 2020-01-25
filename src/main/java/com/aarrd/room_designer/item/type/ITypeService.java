package com.aarrd.room_designer.item.type;


import java.util.List;

public interface ITypeService
{
    Type typeId(String name);
    List<Long> allTypeId();
}
