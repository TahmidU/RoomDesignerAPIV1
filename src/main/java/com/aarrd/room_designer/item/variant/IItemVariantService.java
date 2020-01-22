package com.aarrd.room_designer.item.variant;

import java.util.List;

public interface IItemVariantService
{
    void separateVariants(Long id, List<Long> itemIds);
    void mergeVariant(List<Long> itemIds);
    void addVariant();
}
