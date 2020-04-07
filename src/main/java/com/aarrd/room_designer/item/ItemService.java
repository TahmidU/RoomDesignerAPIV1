package com.aarrd.room_designer.item;

import com.aarrd.room_designer.item.category.ICategoryRepository;
import com.aarrd.room_designer.item.statistic.view.IItemViewRepository;
import com.aarrd.room_designer.item.statistic.view.ItemView;
import com.aarrd.room_designer.item.type.ITypeRepository;
import com.aarrd.room_designer.item.type.Type;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.*;

@Service
public class ItemService implements IItemService
{
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ITypeRepository typeRepository;
    private final IItemViewRepository itemViewRepository;
    private final IEnhancedItemRepository enhancedItemRepository;

    @Autowired
    public ItemService(IItemRepository itemRepository,
                       ICategoryRepository categoryRepository, ITypeRepository typeRepository,
                       IItemViewRepository itemViewRepository, IUserRepository userRepository,
                       IEnhancedItemRepository enhancedItemRepository)
    {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
        this.itemViewRepository = itemViewRepository;
        this.enhancedItemRepository = enhancedItemRepository;
    }

    /**
     * Add item to database.
     * @param item item.
     * @param principal currently logged in user.
     * @param catName category name.
     * @param typeName type name.
     */
    @Override
    public ResponseEntity<?> addItem(Item item, Principal principal, String catName, String typeName)
    {
        System.out.println("ItemService :: Adding Item Cat: " + catName);
        System.out.println("ItemService :: Adding Item Type: " + typeName);

        item.setUser(userRepository.findByEmail(principal.getName()));
        item.setCategory(categoryRepository.findByName(catName));
        item.setType(typeRepository.findByName(typeName));
        item.setDate(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
        System.out.println("ItemService :: Item being saved: " + item.getName());
        Item newItem = itemRepository.save(item);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId", newItem.getItemId());
        return new ResponseEntity<>(jsonObject.toJSONString(), HttpStatus.OK);
    }

    /**
     * Fetch item by item ID.
     * @param itemId ID of the item.
     * @return Object.
     */
    @Override
    public Item fetchItem(Long itemId)
    {
        Item item = itemRepository.findByItemId(itemId);
        itemViewRepository.save(new ItemView(new Date(), item));
        return item;
    }

    /**
     * Fetch all items from the database. Paged to prevent retrieving all items at once.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catIds IDs of the category.
     * @param typeIds IDs of the type.
     * @param hasModel if the item has a model.
     * @return Page of items.
     */
    @Override
    public List<Item> fetchItems(Integer pageNum, String itemName, List<Integer> catIds, List<Integer> typeIds,
                                 Boolean hasModel)
    {
        return enhancedItemRepository.findAllItems(pageNum,itemName,catIds,typeIds,hasModel);
    }

    /**
     * Fetch all users items.
     * @param principal Currently logged in user.
     * @param pageNum (required parameter) page number.
     * @param itemName (request parameter optional) name of the item (does not have to be exact).
     * @param catIds  (request parameter optional) IDs of the category.
     * @param typeIds  (request parameter optional) IDs of the type.
     * @param hasModel (request parameter optional) if the item has a model.
     * @return Page of items.
     */
    @Override
    public List<Item> fetchUserItems(Principal principal, Integer pageNum, String itemName, List<Integer> catIds,
                                           List<Integer> typeIds, Boolean hasModel)
    {
        User user = userRepository.findByEmail(principal.getName());
        return enhancedItemRepository.findAllUserItems(pageNum, itemName, catIds, typeIds, hasModel, user.getUserId());
    }

    /**
     * Remove item from database.
     * @param itemId ID of item
     */
    @Override
    public void removeItem(Long itemId)
    {
        Item item = itemRepository.getOne(itemId);
        itemRepository.delete(item);
    }

    /**
     * Edit existing item in database.
     * @param modItem item.
     */
    @Override
    public void modifyItem(Item modItem, String catName, String typeName)
    {
        System.out.println("ItemService :: Cat Name: " + catName + ", Type Name: " + typeName);

        Item item = itemRepository.getOne(modItem.getItemId());
        item.setName(modItem.getName());
        item.setDescription(modItem.getDescription());
        item.setCategory(categoryRepository.findByName(catName));
        item.setType(typeRepository.findByName(typeName));

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
}
