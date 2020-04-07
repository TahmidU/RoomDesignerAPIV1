package com.aarrd.room_designer.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/item")
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
        System.out.println("ItemController :: Item being added");
        return itemService.addItem(item, principal, catName, typeName);
    }

    /**
     * Fetch single item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch")
    public ResponseEntity<?> fetchItem(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.fetchItem(itemId), HttpStatus.OK);
    }

    /**
     * Fetch the users (using the userId) items.
     * @return ResponseEntity.
     */
    @GetMapping(value = "/fetch/my")
    public ResponseEntity<?> fetchUsersItems(@RequestParam Integer pageNum, @RequestParam(required = false) String itemName,
                                             @RequestParam(required = false) List<Integer> catIds,
                                             @RequestParam(required = false) List<Integer> typeIds,
                                             @RequestParam(required = false) Boolean hasModel, Principal principal)
    {
        System.out.println("ItemController :: Principal Name: " + principal.getName());
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
    @GetMapping(value = "/fetch-all")
    public ResponseEntity<?> fetchItems(@RequestParam Integer pageNum, @RequestParam (required = false) String itemName,
                                        @RequestParam(required = false) List<Integer> catIds,
                                        @RequestParam(required = false) List<Integer> typeIds,
                                        @RequestParam(required = false) Boolean hasModel)
    {
        if(catIds == null)
            System.out.println("ItemController :: catIds is null.");
        else
            System.out.println("ItemController :: catIds is not null.");
        return new ResponseEntity<>(itemService.fetchItems(pageNum, itemName, catIds, typeIds, hasModel),
                HttpStatus.OK);
    }

    /**
     * Fetch all users items.
     * @param principal Currently logged in user.
     * @param pageNum (required parameter) page number.
     * @param itemName (request parameter optional) name of the item (does not have to be exact).
     * @param catIds  (request parameter optional) IDs of the category.
     * @param typeIds  (request parameter optional) IDs of the type.
     * @param hasModel (request parameter optional) if the item has a model.
     * @return ResponseEntity containing Page of items.
     */
    @GetMapping(value = "/fetch/me")
    public ResponseEntity<?> fetchUserItems(Principal principal, @RequestParam Integer pageNum,
                                            @RequestParam(required = false) String itemName,
                                            @RequestParam(required = false) List<Integer> catIds,
                                            @RequestParam(required = false) List<Integer> typeIds,
                                            @RequestParam(required = false) Boolean hasModel)
    {
        return new ResponseEntity<>(itemService.fetchUserItems(principal, pageNum, itemName, catIds, typeIds, hasModel),
                HttpStatus.OK);
    }

    /**
     * Removed Item.
     * @param id (request parameter) ID of the item.
     * @return HttpStatus.
     */
    @DeleteMapping(value = "/remove")
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
    public HttpStatus modifyItem(@RequestBody Item modItem, String catName, String typeName)
    {
        itemService.modifyItem(modItem, catName, typeName);
        return HttpStatus.OK;
    }

    /**
     * Change the items category.
     * @param itemId (request parameter) ID of the item.
     * @param name (request parameter) name of the category.
     * @return HttpStatus.
     */
    @PutMapping(value = "/update/cat")
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
    @PutMapping(value = "/update/type")
    public HttpStatus changeType(@RequestParam Long itemId, @RequestParam String name)
    {
        itemService.changeType(itemId, name);
        return HttpStatus.OK;
    }
}
