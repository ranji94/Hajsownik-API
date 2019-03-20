package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.Shoppinglist;
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
    //UTWÓRZ NOWĄ PUSTĄ LISTĘ
    @PostMapping("/shopping")
    public Shoppinglist createList(Shoppinglist shoppingList, Principal principal)
    {

        return this.shoppingListRepository.save(shoppingList);
    }
    //DODAJ PRODUKT O id={itemid}, w ilości {quantity} do listy numer {shoppinglistid}
    @PutMapping("/shopping/{shoppinglistId}/item/{itemId}/quantity/{quantity}")
    public void createRelationship(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId, @PathVariable(name="quantity") int quantity)
    {
        if(shoppingListRepository.existsShoppinglistById(shoppinglistId))
            shoppingService.createRelationship(shoppinglistId, itemId, quantity);
        else
        {
            System.out.println("Dana lista nie istnieje. Utwórz nową");
        }
    }

    //POBIERZ WSZYSTKIE PRODUKTY PRZYPISANE DO LISTY O NR {SHOPPINGLISTID}
    @GetMapping("/shopping/{shoppinglistId}")
    public List<Item> getAllItemsByList(@PathVariable(name="shoppinglistId") long shoppinglistId)
    {
        return shoppingService.getAllItems(shoppinglistId);
    }

    //DODAJ OSOBY DO WSPÓŁDZIELENIA KOSZTÓW LISTY
    @PutMapping("/shopping/{shoppinglistId}/user/{userId}")
    public void addUserToList(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="userId") long userId, Principal principal)
    {
        shoppingService.assignUserToShoppinglist(shoppinglistId, userId, principal);
    }

    //USUń POWIĄZANIE PRZEDMIOTU Z LISTĄ (narazie zbugowane)
    @DeleteMapping("/shopping/{shoppinglistId}/item/{itemId}")
    public void deleteAssign(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId)
    {
        shoppingService.deleteItemFromShoppinglist(shoppinglistId,itemId);
    }
}
