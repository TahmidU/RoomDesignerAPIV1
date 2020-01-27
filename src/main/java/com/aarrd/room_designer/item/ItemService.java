package com.aarrd.room_designer.item;

import com.aarrd.room_designer.item.category.ICategoryRepository;
import com.aarrd.room_designer.item.category.ICategoryService;
import com.aarrd.room_designer.item.type.ITypeRepository;
import com.aarrd.room_designer.item.type.ITypeService;
import com.aarrd.room_designer.item.variant.IItemVariantService;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.IUserService;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService implements IItemService
{
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ITypeRepository typeRepository;

    private final IItemVariantService itemVariantService;


    @Autowired
    public ItemService(IItemRepository itemRepository, IItemVariantService itemVariantService,
                       ICategoryRepository categoryRepository, ITypeRepository typeRepository, IUserRepository userRepository)
    {
        this.itemRepository = itemRepository;

        this.itemVariantService = itemVariantService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void addItem(Item item, Principal principal, String catName, String typeName)
    {
        item.setUser(userRepository.findByEmail(principal.getName()));
        item.setItemVariant(itemVariantService.addVariant());
        item.setCategory(categoryRepository.findByName(catName));
        item.setType(typeRepository.findByName(typeName));
        itemRepository.save(item);
    }

    @Override
    public Item fetchItem(Long id)
    {
        return itemRepository.getOne(id);
    }

    @Override
    public List<Item> fetchUserItems(Long userId)
    {
        return itemRepository.findByUserId(userId);
    }

    @Override
    public void removeItem(Long id)
    {
        Item item = itemRepository.getOne(id);
        ItemVariant itemVariant = item.getItemVariant();
        itemRepository.delete(item);
        itemVariantService.removeVariant(itemVariant.getVariantId());
    }

    @Override
    public void modifyItem(Item modItem)
    {
        Item item = itemRepository.getOne(modItem.getItemId());
        item.setName(modItem.getName());
        item.setDescription(modItem.getDescription());

        itemRepository.save(item);
    }

    @Override
    public void changeCategory(Long itemId, String name)
    {
        Item item = itemRepository.getOne(itemId);
        item.setCategory(categoryRepository.findByName(name));
        itemRepository.save(item);
    }

    @Override
    public void changeType(Long itemId, String name)
    {
        Item item = itemRepository.getOne(itemId);
        item.setType(typeRepository.findByName(name));
        itemRepository.save(item);
    }

    @Override
    public void mergeVariants(List<Long> itemIds)
    {
        List<Item> items = new ArrayList<>();
        for(Long iid : itemIds)
            items.add(itemRepository.getOne(iid));

        ItemVariant newItemVariant = itemVariantService.addVariant();

        List<Long> oldVariants = new ArrayList<>();
        for(Item i : items)
        {
            oldVariants.add(i.getItemVariant().getVariantId());
            i.setItemVariant(newItemVariant);
        }

        for(Long o : oldVariants)
            itemVariantService.removeVariant(o);
    }

    @Override
    public void separateVariants(List<Long> itemIds)
    {
        List<Item> items = new ArrayList<>();
        for(Long i : itemIds)
            items.add(itemRepository.getOne(i));

        Long sharedVId = items.get(0).getItemVariant().getVariantId();

        List<ItemVariant> variantIds = itemVariantService.separateVariants(itemIds);
        int count = 0;
        for(Item i: items)
        {
            i.setItemVariant(variantIds.get(count));
            count++;
        }

        itemRepository.saveAll(items);
        itemVariantService.removeVariant(sharedVId);
    }

    @Override
    public User getUser(Long itemId)
    {
        return itemRepository.getOne(itemId).getUser();
    }
}
