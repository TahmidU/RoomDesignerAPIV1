package com.aarrd.room_designer.item;

import com.aarrd.room_designer.item.category.ICategoryRepository;
import com.aarrd.room_designer.item.type.ITypeRepository;
import com.aarrd.room_designer.item.variant.IItemVariantRepository;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemService implements IItemService
{
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ITypeRepository typeRepository;
    private final IItemVariantRepository itemVariantRepository;


    @Autowired
    public ItemService(IItemRepository itemRepository, IItemVariantRepository itemVariantRepository,
                       ICategoryRepository categoryRepository, ITypeRepository typeRepository, IUserRepository userRepository)
    {
        this.itemRepository = itemRepository;
        this.itemVariantRepository = itemVariantRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void addItem(Item item, Principal principal, String catName, String typeName)
    {
        item.setUser(userRepository.findByEmail(principal.getName()));
        item.setItemVariant(addVariant());
        item.setCategory(categoryRepository.findByName(catName));
        item.setType(typeRepository.findByName(typeName));
        item.setDate(new Date());
        itemRepository.save(item);
    }

    @Override
    public Object fetchItem(Long itemId)
    {
        return itemRepository.findByItemId(itemId);
    }

    @Override
    public List<Object[]> fetchUserItems(Long userId)
    {
        return itemRepository.findByUserId(userId);
    }

    @Override
    public void removeItem(Long itemId)
    {
        Item item = itemRepository.getOne(itemId);
        ItemVariant itemVariant = item.getItemVariant();
        itemRepository.delete(item);
        removeVariant(itemVariant.getVariantId());
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

        ItemVariant newItemVariant = addVariant();

        List<Long> oldVariants = new ArrayList<>();
        for(Item i : items)
        {
            oldVariants.add(i.getItemVariant().getVariantId());
            i.setItemVariant(newItemVariant);
        }

        for(Long o : oldVariants)
            removeVariant(o);
    }

    @Override
    public void separateVariants(List<Long> itemIds)
    {
        List<Item> items = new ArrayList<>();
        for(Long i : itemIds)
            items.add(itemRepository.getOne(i));

        Long sharedVId = items.get(0).getItemVariant().getVariantId();

        List<ItemVariant> variantIds = new ArrayList<>();
        for(Long id: itemIds)
            variantIds.add(addVariant());

        int count = 0;
        for(Item i: items)
        {
            i.setItemVariant(variantIds.get(count));
            count++;
        }

        itemRepository.saveAll(items);
        removeVariant(sharedVId);
    }

    @Override
    public List<Object[]> fetchItemVariants(Long itemId)
    {
        Item item = itemRepository.getOne(itemId);
        return itemRepository.findByVariantId(item.getItemVariant().getVariantId());
    }

    @Override
    public User getUser(Long itemId)
    {
        return itemRepository.getOne(itemId).getUser();
    }

    @Override
    public List<Object[]> fetchItems(Integer pageNum)
    {
        return itemRepository.findAllItems(PageRequest.of(pageNum, 2));
    }

    private ItemVariant addVariant()
    {
        ItemVariant itemVariant = new ItemVariant();
        return itemVariantRepository.save(itemVariant);
    }

    private void removeVariant(Long id) {
        itemVariantRepository.delete(itemVariantRepository.getOne(id));
    }
}
