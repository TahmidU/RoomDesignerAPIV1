package com.aarrd.room_designer.item.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService implements ITypeService
{
    private final ITypeRepository typeRepository;

    @Autowired
    public TypeService(ITypeRepository typeRepository)
    {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type typeId(String name)
    {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Long> allTypeId()
    {
        List<Long> ids = new ArrayList<>();
        for(Type i: typeRepository.findAll())
            ids.add(i.getTypeId());
        return ids;
    }
}
