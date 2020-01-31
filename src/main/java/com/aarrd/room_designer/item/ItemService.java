package com.aarrd.room_designer.item;

import com.aarrd.room_designer.item.category.ICategoryRepository;
import com.aarrd.room_designer.item.type.ITypeRepository;
import com.aarrd.room_designer.item.variant.IItemVariantRepository;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     * Add item to database.
     * @param item item.
     * @param principal currently logged in user.
     * @param catName category name.
     * @param typeName type name.
     */
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

    /**
     * Fetch item by item ID.
     * @param itemId ID of the item.
     * @return Object.
     */
    @Override
    public Object fetchItem(Long itemId)
    {
        return itemRepository.findByItemId(itemId);
    }

    /**
     * Fetch users uploaded items.
     * @param userId ID of the user.
     * @return List of objects.
     */
    @Override
    public List<Object[]> fetchUserItems(Long userId)
    {
        return itemRepository.findByUserId(userId);
    }

    /**
     * Fetch all items with the category criterion. Paged to prevent retrieving all items at once.
     * @param catName category name.
     * @param pageNum page number.
     * @return List of objects.
     */
    @Override
    public List<Object[]> fetchItemsByCat(String catName, Integer pageNum)
    {
        return itemRepository.findByCategory(catName, PageRequest.of(pageNum, 9,
                Sort.by(Sort.Direction.ASC, "date")));
    }

    /**
     * Remove item from database.
     * @param itemId ID of item
     */
    @Override
    public void removeItem(Long itemId)
    {
        Item item = itemRepository.getOne(itemId);
        ItemVariant itemVariant = item.getItemVariant();
        itemRepository.delete(item);
        removeVariant(itemVariant.getVariantId());
    }

    /**
     * Edit existing item in database.
     * @param modItem item.
     */
    @Override
    public void modifyItem(Item modItem)
    {
        Item item = itemRepository.getOne(modItem.getItemId());
        item.setName(modItem.getName());
        item.setDescription(modItem.getDescription());

        itemRepository.save(item);
    }

    /**
     * Change category of existing item.
     * @param itemId ID of item.
     * @param name category name.
     */
    @Override
    public void changeCategory(Long itemId, String name)
    {
        Item item = itemRepository.getOne(itemId);
        item.setCategory(categoryRepository.findByName(name));
        itemRepository.save(item);
    }

    /**
     * Change type of existing item.
     * @param itemId ID of item.
     * @param name type name.
     */
    @Override
    public void changeType(Long itemId, String name)
    {
        Item item = itemRepository.getOne(itemId);
        item.setType(typeRepository.findByName(name));
        itemRepository.save(item);
    }

    /**
     * Merge the item variant ids of the following items.
     * @param itemIds ID of the items.
     */
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

    /**
     * Seperate the item variant ids of the following items.
     * @param itemIds ID of the items.
     */
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

    /**
     * Get the item variant of the item.
     * @param itemId ID of the item.
     * @return Long (item variant id).
     */
    @Override
    public Long getVariantId(Long itemId)
    {
        return itemRepository.findVariantIdByItemId(itemId);
    }

    /**
     * Fetch all the variants of the item.
     * @param itemId ID of the item.
     * @return List of objects.
     */
    @Override
    public List<Object[]> fetchItemVariants(Long itemId)
    {
        Item item = itemRepository.getOne(itemId);
        return itemRepository.findByVariantId(item.getItemVariant().getVariantId());
    }

    /**
     * Return the user of the item.
     * @param itemId ID of the item.
     * @return User.
     */
    @Override
    public User getUser(Long itemId)
    {
        return itemRepository.getOne(itemId).getUser();
    }

    /**
     * Fetch all item. Paged to prevent retrieval of items all at once.
     * @param pageNum page number.
     * @return List of objects.
     */
    @Override
    public List<Object[]> fetchItems(Integer pageNum)
    {
        return itemRepository.findAllItems(PageRequest.of(pageNum, 9,
                Sort.by(Sort.Direction.ASC, "date")));
    }

    /**
     * Add an item variant.
     * @return ItemVariant.
     */
    private ItemVariant addVariant()
    {
        ItemVariant itemVariant = new ItemVariant();
        return itemVariantRepository.save(itemVariant);
    }

    /**
     * Remove an item variant.
     * @param id ID of the item variant.
     */
    private void removeVariant(Long id) {
        itemVariantRepository.delete(itemVariantRepository.getOne(id));
    }
}
