package com.aarrd.room_designer.item;

import com.aarrd.room_designer.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/items")
public class ItemController
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
    public ResponseEntity<?> addItem(@RequestBody Item item, Principal principal, @RequestParam String catName,
                              @RequestParam String typeName)
    {
        Log.printMsg(this.getClass(), "Adding new item...");
        return itemService.addItem(item, principal, catName, typeName);
    }

    /**
     * Fetch single item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/{itemId}")
    public ResponseEntity<?> fetchItem(@PathVariable(name = "itemId") Long itemId)
    {
        Log.printMsg(this.getClass(), "Fetch item: " + itemId);
        return new ResponseEntity<>(itemService.fetchItem(itemId), HttpStatus.OK);
    }

    /**
     * Fetch the users (using the userId) items.
     * @param principal Currently logged in user.
     * @param pageNum (required parameter) page number.
     * @param itemName (request parameter optional) name of the item (does not have to be exact).
     * @param catIds  (request parameter optional) IDs of the category.
     * @param typeIds  (request parameter optional) IDs of the type.
     * @param hasModel (request parameter optional) if the item has a model.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/my")
    public ResponseEntity<?> fetchUsersItems(@RequestParam Integer pageNum, @RequestParam(required = false) String itemName,
                                             @RequestParam(required = false) List<Integer> catIds,
                                             @RequestParam(required = false) List<Integer> typeIds,
                                             @RequestParam(required = false) Boolean hasModel, Principal principal)
    {
        Log.printMsg(this.getClass(), "Fetch Users items: " + principal.getName() + ", page: " + pageNum);
        return new ResponseEntity<>(itemService.fetchUserItems(principal, pageNum,
                itemName, catIds, typeIds, hasModel), HttpStatus.OK);
    }

    /**
     * Fetch all items from the database. Paged to prevent retrieving all items at once.
     * @param pageNum (required parameter) page number.
     * @param itemName (request parameter optional) name of the item (does not have to be exact).
     * @param catIds  (request parameter optional) IDs of the category.
     * @param typeIds  (request parameter optional) IDs of the type.
     * @param hasModel (request parameter optional) if the item has a model.
     * @return ResponseEntity containing Page of items.
     */
    @GetMapping(value = "/all")
    public ResponseEntity<?> fetchItems(@RequestParam Integer pageNum, @RequestParam (required = false) String itemName,
                                        @RequestParam(required = false) List<Integer> catIds,
                                        @RequestParam(required = false) List<Integer> typeIds,
                                        @RequestParam(required = false) Boolean hasModel)
    {
        Log.printMsg(this.getClass(), "Fetch all items.");
        return new ResponseEntity<>(itemService.fetchItems(pageNum, itemName, catIds, typeIds, hasModel),
                HttpStatus.OK);
    }

    /**
     * Removed Item.
     * @param id (request parameter) ID of the item.
     * @return HttpStatus.
     */
    @DeleteMapping(value = "/{id}")
    public HttpStatus removeItem(@PathVariable(name = "id") Long id)
    {
        Log.printMsg(this.getClass(), "Remove item with id: " + id);
        itemService.removeItem(id);
        return HttpStatus.OK;
    }

    /**
     * Edit item.
     * @param modItem (request body) Item.
     * @return HttpStatus.
     */
    @PutMapping(value = "/update")
    public HttpStatus modifyItem(@RequestBody Item modItem, String catName, String typeName)
    {
        Log.printMsg(this.getClass(), "Update item with id: " + modItem.getItemId());
        itemService.modifyItem(modItem, catName, typeName);
        return HttpStatus.OK;
    }
}
