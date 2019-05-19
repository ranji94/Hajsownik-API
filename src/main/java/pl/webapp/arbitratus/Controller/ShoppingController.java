package pl.webapp.arbitratus.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ItemShoppinglist;
import pl.webapp.arbitratus.Entity.Shoppinglist;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.ShoppinglistRepository;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Security.JwtTokenProvider;
import pl.webapp.arbitratus.Service.ItemService;
import pl.webapp.arbitratus.Service.ObligationService;
import pl.webapp.arbitratus.Service.ShoppingService;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ShoppingController {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private ShoppinglistRepository shoppingListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ShoppingService shoppingService;
    @Autowired
    ObligationService obligationService;

    public ShoppingController(ObligationService obligationService, ItemService itemService, ShoppinglistRepository shoppingListRepository, ShoppingService shoppingService, UserRepository userRepository) {
        this.obligationService = obligationService;
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

    @PostMapping("/shopping/{shoppinglistId}/obligations/calculate")
    public void calculateListObligations(@PathVariable(name="shoppinglistId") long shoppinglistId)
    {
        this.obligationService.calculateListObligations(shoppinglistId);
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

    @GetMapping("/items/getall")
    public List<Item> getAllItems(){
        return shoppingService.getAllItems();
    }

    @PostMapping("/items/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        try {
            return new ResponseEntity<Item>(itemService.addItem(item), HttpStatus.CREATED);
        } catch(IllegalArgumentException | NullPointerException e)
        {
            logger.error("Wystąpił wyjątek "+e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wprowadź poprawne dane", e);
        }
    }

    @GetMapping("/users/current")
    public String getCurrentUser(Principal principal){
        try{
        return principal.getName();} catch (NullPointerException e){
            logger.error("Brak zalogowanego użytkownika");
            return null;
        }
    }

    //POBIERZ WSZYSTKIE PRODUKTY PRZYPISANE DO LISTY O NR {SHOPPINGLISTID}
    @GetMapping("/shopping/{shoppinglistId}")
    public List<Item> getAllItemsByList(@PathVariable(name="shoppinglistId") long shoppinglistId, Principal principal)
    {
        try {
            return shoppingService.getAllItems(shoppinglistId, principal);
        } catch(IndexOutOfBoundsException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/shopping/{shoppinglistId}/getTotal")
    public float getListTotal(@PathVariable(name="shoppinglistId") long shoppinglistId){
        return shoppingService.getListTotal(shoppinglistId);
    }

    @GetMapping("/shopping/{shoppinglistId}/item/{itemId}/quantity")
    public ItemShoppinglist getItemListRelation(@PathVariable(name="shoppinglistId") long shoppinglistId, @PathVariable(name="itemId") long itemId){
        return shoppingService.getQuantity(shoppinglistId, itemId);
    }

    //Wszyscy użytkownicy współdzielący daną listę
    @GetMapping("/shopping/{shoppinglistId}/users")
    public List<User> getAllUsersByList(@PathVariable(name="shoppinglistId") long shoppinglistId)
    {
        return shoppingService.getAllUsersByList(shoppinglistId);
    }

    @GetMapping("/shopping/{shoppinglistId}/isOwner")
    public boolean isOwner(@PathVariable(name="shoppinglistId") long shoppinglistId, Principal principal){
        return shoppingService.isOwner(shoppinglistId, principal);
    }

    //POKAZ WSZYSTKIE LISTY KTÓRE STWORZYLSMY LUB Z KTORYCH JESTESMY ROZLICZANI
    @GetMapping("/shopping")
    public List<Shoppinglist> getAllShoppingLists(Principal principal)
    {
        return shoppingService.getAllShoppinglists(principal);
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
    @CrossOrigin(origins = "http://localhost:3000")
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
