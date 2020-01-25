package com.aarrd.room_designer.item.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService
{
    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category categoryId(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Long> allCategoryId() {
        List<Long> ids = new ArrayList<>();
        for(Category i: categoryRepository.findAll())
            ids.add(i.getCatId());
        return ids;
    }
}
