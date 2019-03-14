package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ShoppingListRepository;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    public ItemController(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @PostMapping("/shoppingLists/{shoppingListId}/items")
    public Item createItem(@PathVariable (value="shoppingListId") long shoppingListId, @RequestBody Item item)
    {
            return shoppingListRepository.findById(shoppingListId).map(shoppingList -> {
                item.setShoppinglist(shoppingList);
                if (this.itemRepository.existsItemByName(item.getName())) {
                    if (!this.itemRepository.existsItemByShop(item.getShop())) {
                        return itemRepository.save(item);
                    } else {
                        System.out.println("Taki przedmior już istnieje w bazie!");
                        return itemRepository.save(item);
                    }
                } else {
                    return itemRepository.save(item);
                }
            }).orElseThrow(() -> new ResourceNotFoundException("ShoppingListId " + shoppingListId + " not found"));
    }

    @PutMapping("/shoppingLists/{shoppingListId}/items/{itemId}")
    public Item updateItem(@PathVariable (value = "shoppingListId") Long shoppingListId,
                                 @PathVariable (value = "itemId") Long itemId,
                                 @RequestBody Item itemRequest) {
        if(!shoppingListRepository.existsById(shoppingListId)) {
            throw new ResourceNotFoundException("ListId " + shoppingListId + " not found");
        }

        return itemRepository.findById(itemId).map(item -> {
            item.setName(itemRequest.getName());
            return itemRepository.save(item);
        }).orElseThrow(() -> new ResourceNotFoundException("ItemId " + itemId + "not found"));
    }
}
