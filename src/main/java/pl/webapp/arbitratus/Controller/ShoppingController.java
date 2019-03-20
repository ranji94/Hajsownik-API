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
        return this.shoppingService.createList(shoppingList,principal);
    }
    //DODAJ PRODUKT O id={itemid}, w ilości {quantity} do listy numer {shoppinglistid}
    @PutMapping("/shopping/{shoppinglistId}/item/{itemId}/quantity/{quantity}")
    public void createRelationship(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId, @PathVariable(name="quantity") int quantity, Principal principal)
    {
        if(shoppingListRepository.existsShoppinglistById(shoppinglistId))
            shoppingService.createRelationship(shoppinglistId, itemId, quantity, principal);
        else
        {
            System.out.println("Dana lista nie istnieje. Utwórz nową");
        }
    }

    //POBIERZ WSZYSTKIE PRODUKTY PRZYPISANE DO LISTY O NR {SHOPPINGLISTID}
    @GetMapping("/shopping/{shoppinglistId}")
    public List<Item> getAllItemsByList(@PathVariable(name="shoppinglistId") long shoppinglistId, Principal principal)
    {
        return shoppingService.getAllItems(shoppinglistId, principal);
    }

    //DODAJ OSOBY DO WSPÓŁDZIELENIA KOSZTÓW LISTY
    @PutMapping("/shopping/{shoppinglistId}/user/{userId}")
    public void addUserToList(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="userId") long userId, Principal principal)
    {
        shoppingService.assignUserToShoppinglist(shoppinglistId, userId, principal);
    }

    //USUń POWIĄZANIE PRZEDMIOTU Z LISTĄ (JUŻ DZIAŁA)
    @DeleteMapping("/shopping/{shoppinglistId}/item/{itemId}")
    public void deleteAssign(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId, Principal principal)
    {
        shoppingService.deleteItemFromShoppinglist(shoppinglistId,itemId, principal);
    }

    //USUń LISTę ZAKUPOWĄ WRAZ Z POWIĄZANIAMI PRZEDMIOTóW I UżYTKOWNIKÓW DO TEJ LISTY
    @DeleteMapping("/shopping/{shoppinglistId}")
    public void deleteShoppingList(@PathVariable(name="shoppinglistId") long shoppinglistId, Principal principal)
    {
        try {
            shoppingService.deleteShoppinglistWithAllItemAssigns(shoppinglistId, principal);
        } catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Pojawił się wyjątek: "+e.getLocalizedMessage());
        }
    }

    //WYRZUć UŻYTKOWNIKA POWIĄZANEGO Z TĄ LISTĄ
    @DeleteMapping("/shopping/{shoppinglistId}/user/{userId}")
    public void deleteUserFromList(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="userId") long userId, Principal principal)
    {
        try{
        shoppingService.deleteUserFromShoppingListAssigns(shoppinglistId, userId, principal);}
        catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Pojawił się wyjątek "+ e.getLocalizedMessage());
        }
    }
}
