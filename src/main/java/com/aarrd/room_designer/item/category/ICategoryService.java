package com.aarrd.room_designer.item.category;

import java.util.List;

public interface ICategoryService
{
    Category categoryId(String name);
    List<Long> allCategoryId();
}
