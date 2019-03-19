package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ShoppingList;
import pl.webapp.arbitratus.Repository.ShoppingListRepository;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Service.ItemService;
import pl.webapp.arbitratus.Service.ShoppingService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShoppingController {
    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ShoppingService shoppingService;

    public ShoppingController(ItemService itemService, ShoppingListRepository shoppingListRepository) {
        this.itemService = itemService;
        this.shoppingListRepository = shoppingListRepository;
    }

    @PostMapping("/shopping")
    public ShoppingList createList(ShoppingList shoppingList, Principal principal)
    {
        List<Item> przedmioty = new ArrayList<>();
        shoppingList.setItems(przedmioty);
        shoppingList.setUser(userRepository.findByUsername(principal.getName()));
        return this.shoppingListRepository.save(shoppingList);
    }

    @PutMapping("/shopping/{shoppingListId}/item/{itemId}")
    public void createRelationship(@PathVariable(name="shoppingListId") long shoppingListId, @PathVariable(name="itemId") long itemId)
    {
        shoppingService.createRelationship(shoppingListId, itemId);
    }
}
