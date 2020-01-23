package com.aarrd.room_designer.item.variant;

import java.util.List;

public interface IItemVariantService
{
    List<Long> separateVariants(List<Long> itemIds);
    Long mergeVariant(List<Long> itemIds);
    void removeVariant(Long id);
    Long addVariant();
}
