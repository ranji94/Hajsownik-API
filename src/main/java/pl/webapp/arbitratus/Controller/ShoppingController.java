package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.Shoppinglist;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.ShoppinglistRepository;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Service.ItemService;
import pl.webapp.arbitratus.Service.ShoppingService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ShoppingController {
    @Autowired
    private ShoppinglistRepository shoppingListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ShoppingService shoppingService;

    public ShoppingController(ItemService itemService, ShoppinglistRepository shoppingListRepository, ShoppingService shoppingService, UserRepository userRepository) {
        this.itemService = itemService;
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingService = shoppingService;
        this.userRepository = userRepository;
    }

    @PostMapping("/shopping")
    public Shoppinglist createList(Shoppinglist shoppingList, Principal principal)
    {
        List<User> listUsers = shoppingList.getUsers();
        String username = principal.getName();
        listUsers.add(userRepository.findByUsername(username));
        shoppingList.setUsers(listUsers);
        return this.shoppingListRepository.save(shoppingList);
    }

    @PutMapping("/shopping/{shoppinglistId}/item/{itemId}/quantity/{quantity}")
    public void createRelationship(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId, @PathVariable(name="quantity") int quantity)
    {
        shoppingService.createRelationship(shoppinglistId, itemId, quantity);
    }

    @GetMapping("/shopping/{shoppinglistId}")
    public List<Item> getAllItemsByList(@PathVariable(name="shoppinglistId") long shoppinglistId)
    {
        return shoppingService.getAllItems(shoppinglistId);
    }

    @PutMapping("/shopping/{shoppinglistId}/user/{userId}")
    public void addUserToList(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="userId") long userId)
    {
        shoppingService.assignUserToShoppinglist(shoppinglistId, userId);
    }
}
