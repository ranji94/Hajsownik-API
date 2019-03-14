package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ShoppingList;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ShoppingListRepository;

import java.util.Optional;
import java.util.Set;

@RestController
public class ShoppingController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    public ShoppingController(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @PostMapping("/shopping")
    public ShoppingList createList(@RequestBody ShoppingList shoppingList)
    {
        return this.shoppingListRepository.save(shoppingList);
    }
}
