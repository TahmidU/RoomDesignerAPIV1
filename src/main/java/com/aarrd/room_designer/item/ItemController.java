package com.aarrd.room_designer.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/item")
public class ItemController implements IItemController
{
    private final IItemService itemService;

    @Autowired
    public ItemController(IItemService itemService)
    {
        this.itemService = itemService;
    }

    /**
     * Add item.
     * @param item (request body) item.
     * @param principal user currently logged in.
     * @param catName (request parameter) category name.
     * @param typeName (request parameter) type name.
     * @return HttpStatus.
     */
    @PostMapping(value = "/add")
    @Override
    public HttpStatus addItem(@RequestBody Item item, Principal principal, @RequestParam String catName,
                              @RequestParam String typeName)
    {
        itemService.addItem(item, principal, catName, typeName);
        return HttpStatus.OK;
    }

    /**
     * Fetch single item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch-item")
    @Override
    public ResponseEntity<?> fetchItem(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.fetchItem(itemId), HttpStatus.OK);
    }

    /**
     * Fetch the users (using the userId) items.
     * @param userId (request parameter) ID of the user.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch-items-userid")
    @Override
    public ResponseEntity<?> fetchItemsByUserId(@RequestParam Long userId)
    {
        return new ResponseEntity<>(itemService.fetchUserItems(userId), HttpStatus.OK);
    }

    /**
     * Fetch items based on category. Paged to prevent retrieving all items at once.
     * @param catName (request parameter) category name.
     * @param pageNum (request parameter) page number.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch-by-cat")
    @Override
    public ResponseEntity<?> fetchItemByCategory(@RequestParam String catName, @RequestParam Integer pageNum)
    {
        return new ResponseEntity<>(itemService.fetchItemsByCat(catName, pageNum), HttpStatus.OK);
    }

    /**
     * Removed Item.
     * @param id (request parameter) ID of the item.
     * @return HttpStatus.
     */
    @DeleteMapping(value = "/remove")
    @Override
    public HttpStatus removeItem(@RequestParam Long id)
    {
        itemService.removeItem(id);
        return HttpStatus.OK;
    }

    /**
     * Edit item.
     * @param modItem (request body) Item.
     * @return HttpStatus.
     */
    @PutMapping(value = "/update")
    @Override
    public HttpStatus modifyItem(@RequestBody Item modItem)
    {
        itemService.modifyItem(modItem);
        return HttpStatus.OK;
    }

    /**
     * Change the items category.
     * @param itemId (request parameter) ID of the item.
     * @param name (request parameter) name of the category.
     * @return HttpStatus.
     */
    @PutMapping(value = "/change-cat")
    @Override
    public HttpStatus changeCategory(@RequestParam Long itemId, @RequestParam String name)
    {
        itemService.changeCategory(itemId, name);
        return HttpStatus.OK;
    }

    /**
     * Change the items type.
     * @param itemId (request parameter) ID of the item.
     * @param name (request parameter) name of the type.
     * @return HttpStatus.
     */
    @PutMapping(value = "/change-type")
    @Override
    public HttpStatus changeType(@RequestParam Long itemId, @RequestParam String name)
    {
        itemService.changeType(itemId, name);
        return HttpStatus.OK;
    }

    /**
     * Merge the variant id of the items.
     * @param itemIds (request parameter) List of item IDs.
     * @return HttpStatus.
     */
    @PutMapping(value = "/merge-var")
    @Override
    public HttpStatus mergeVariants(@RequestParam List<Long> itemIds)
    {
        itemService.mergeVariants(itemIds);
        return HttpStatus.OK;
    }

    /**
     * Separate the variant id of the items.
     * @param itemIds (request parameter) List of item IDs.
     * @return HttpStatus.
     */
    @PutMapping(value = "/separate-var")
    @Override
    public HttpStatus separateVariants(@RequestParam List<Long> itemIds)
    {
        itemService.separateVariants(itemIds);
        return HttpStatus.OK;
    }

    /**
     * Get the items variant Id.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing long.
     */
    @GetMapping(value = "/get-var-id")
    @Override
    public ResponseEntity<Long> getVariantId(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.getVariantId(itemId), HttpStatus.OK);
    }

    /**
     * Fetch items by item variant id.
     * @param itemId (request parameter) item variant ids
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch-variants")
    @Override
    public ResponseEntity<?> fetchItemVariant(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.fetchItemVariants(itemId), HttpStatus.OK);
    }

    /**
     * Fetch all items from the database. Paged to prevent retrieving all items at once.
     * @param pageNum (required parameter) page number.
     * @param itemName (request parameter optional) name of the item (does not have to be exact).
     * @param catId  (request parameter optional) ID of the category.
     * @param typeId  (request parameter optional) ID of the type.
     * @param hasModel (request parameter optional) if the item has a model.
     * @return ResponseEntity containing Page of items.
     */
    @GetMapping(value = "/fetch-all")
    @Override
    public ResponseEntity<?> fetchItems(@RequestParam Integer pageNum, @RequestParam (required = false) String itemName,
                                        @RequestParam(required = false) Integer catId,
                                        @RequestParam(required = false) Integer typeId,
                                        @RequestParam(required = false) Boolean hasModel)
    {
        return new ResponseEntity<>(itemService.fetchItems(pageNum, itemName, catId, typeId, hasModel),
                HttpStatus.OK);
    }
}
