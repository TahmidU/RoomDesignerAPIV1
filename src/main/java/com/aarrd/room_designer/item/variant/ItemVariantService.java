package com.aarrd.room_designer.item.variant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        return addVariant().getVariantId();
    }

    @Override
    public List<ItemVariant> separateVariants(List<Long> itemIds)
    {
        List<ItemVariant> itemVariants = new ArrayList<>();
        for(Long id: itemIds)
            itemVariants.add(addVariant());

        return itemVariants;
    }

    @Override
    public void removeVariant(Long id) {
        itemVariantRepository.delete(itemVariantRepository.getOne(id));
    }

    @Override
    public ItemVariant addVariant() {
        ItemVariant itemVariant = new ItemVariant();
        return itemVariantRepository.save(itemVariant);
    }
}
