package com.aarrd.room_designer.item.variant;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ItemVariantService implements IItemVariantService
{
    private final IItemVariantRepository itemVariantRepository;

    @Autowired
    public ItemVariantService(IItemVariantRepository itemVariantRepository)
    {
        this.itemVariantRepository = itemVariantRepository;
    }

    @Override
    public Long mergeVariant(List<Long> itemIds)
    {
        for(Long id: itemIds)
            itemVariantRepository.delete(itemVariantRepository.getOne(id));
        return addVariant();
    }

    @Override
    public List<Long> separateVariants(List<Long> itemIds)
    {
        List<Long> itemVariantIds = new ArrayList<>();
        for(Long id: itemIds)
            itemVariantIds.add(addVariant());

        return itemVariantIds;
    }

    @Override
    public void removeVariant(Long id) {
        itemVariantRepository.delete(itemVariantRepository.getOne(id));
    }

    @Override
    public Long addVariant() {
        ItemVariant itemVariant = new ItemVariant();
        itemVariantRepository.save(itemVariant);
        return itemVariant.getVariantId();
    }
}
