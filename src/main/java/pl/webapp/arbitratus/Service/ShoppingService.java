package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.*;
import pl.webapp.arbitratus.Repository.*;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.List;

@Service
public class ShoppingService {
    @Autowired
    ShoppinglistRepository shoppinglistRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemShoppinglistRepository itemShoppinglistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserShoppinglistRepository userShoppinglistRepository;

    public ShoppingService(ShoppinglistRepository shoppingListRepository, ItemRepository itemRepository, ItemShoppinglistRepository itemShoppinglistRepository, UserRepository userRepository, UserShoppinglistRepository userShoppinglistRepository) {
        this.shoppinglistRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.itemShoppinglistRepository = itemShoppinglistRepository;
        this.userRepository = userRepository;
        this.userShoppinglistRepository = userShoppinglistRepository;
    }

    public Shoppinglist createList(Shoppinglist shoppinglist, Principal principal)
    {
        UserShoppinglist userShoppinglist = new UserShoppinglist();
        userShoppinglist.setShoppinglist(shoppinglist);
        userShoppinglist.setUser(userRepository.findByUsername(principal.getName()));
        userShoppinglist.setOwner(principal.getName());
        userShoppinglistRepository.save(userShoppinglist);
        return shoppinglistRepository.save(shoppinglist);
    }

    // DODAJ PRZEDMIOT O ID {itemId}, DO LISTY ZAKUPOWEJ O ID {shoppinglistId} W ILOSCI QUANTITY
    public void createRelationship(long shoppingListId, long itemId, int quantity, Principal principal) {
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppingListId, uprzywilejowany.getId())
        ||userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppingListId,principal.getName()));
        {
        ItemShoppinglist itemShoppingList = new ItemShoppinglist();
        if (itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppingListId, itemId) != null) {
            itemShoppingList = itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppingListId, itemId);
            itemShoppingList.setQuantity(itemShoppingList.getQuantity() + quantity);
            itemShoppingList.setTotal(itemShoppingList.getQuantity() * itemShoppingList.getItem().getPrice());
            this.itemShoppinglistRepository.save(itemShoppingList);
        } else {
            itemShoppingList.setItem(itemRepository.findItemById(itemId));
            itemShoppingList.setShoppinglist(shoppinglistRepository.findShoppinglistById(shoppingListId));
            itemShoppingList.setQuantity(quantity);
            itemShoppingList.setTotal((quantity * itemRepository.findItemById(itemId).getPrice()));
            this.itemShoppinglistRepository.save(itemShoppingList);
        }
        List<Float> costs = itemShoppinglistRepository.getTotalValues(shoppinglistRepository.findShoppinglistById(shoppingListId));
        float suma = 0;
        for (int i = 0; i < costs.size(); i++) {
            suma += costs.get(i);
        }
        Shoppinglist listaUpdate = shoppinglistRepository.findShoppinglistById(shoppingListId);
        listaUpdate.setListtotal(suma);
        shoppinglistRepository.save(listaUpdate);
        }
    }

    //POBIERZ WSZYSTKIE PRZEDMIOTY PRZYPISANE DO LISTY O ID {shoppinglistId}
    public List<Item> getAllItems(long shoppinglistId, Principal principal) {
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId,uprzywilejowany.getId())
        || userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppinglistId,principal.getName()))
            return itemShoppinglistRepository.getAllItems(shoppinglistRepository.findShoppinglistById(shoppinglistId));
        else
        {
            System.out.println("Ten użytkownik nie ma dostępu do tej listy");
            return null;
        }
    }

    //DO LISTY ZAKUPOWEJ O ID {shoppinglistId} DODAJ UZYTKOWNIKA KTORY BEDZIE WSPOLDZIELIL KOSZTA
    public void assignUserToShoppinglist(long shoppinglistId, long userId, Principal principal) {
        UserShoppinglist userShoppinglist = new UserShoppinglist();
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId,uprzywilejowany.getId())
                || userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppinglistId,principal.getName())) {
            userShoppinglist.setShoppinglist(shoppinglistRepository.findShoppinglistById(shoppinglistId));
            userShoppinglist.setUser(userRepository.findUserById(userId));
            userShoppinglist.setOwner(principal.getName());
            if (!userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId, userId))
                userShoppinglistRepository.save(userShoppinglist);
            else System.out.println("Ten użytkownik już został przypisany do listy o id: " + shoppinglistId);
        }
        else
        {
            System.out.println("Ten użytkownik nie ma dostępu do tej listy");
        }
    }

    //USUN PRZEDMIOT Z LISTY ZAKUPOWEJ (A WLASCIWIE POWIAZANIE PRZEDMIOTU Z LISTA). NARAZIE ZBUGOWANE
    public void deleteItemFromShoppinglist(long shoppinglistId, long itemId, Principal principal) {
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId,uprzywilejowany.getId())
                || userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppinglistId,principal.getName())) {
            ItemShoppinglist itemShoppinglist = itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppinglistId, itemId);
            Shoppinglist shoppinglist = shoppinglistRepository.findShoppinglistById(shoppinglistId);
            shoppinglist.setListtotal(shoppinglist.getListtotal() - itemShoppinglist.getTotal());
            shoppinglist.setItemShoppinglists(null);
            itemShoppinglist.setItem(null);
            itemShoppinglist.setShoppinglist(null);
            itemShoppinglistRepository.delete(itemShoppinglist);
            shoppinglistRepository.save(shoppinglist);
        } else {
            System.out.println("Ten użytkownik nie należy do listy, w związku z tym nie ma prawa usuwać z niej przedmiotów");
        }
    }

    //USUN CALA LISTE I POWIĄZANE Z NIĄ OBIEKTY (ZA WYJĄTKIEM PRZEDMIOTOW I UZYTKOWNIKOW
    public void deleteShoppinglistWithAllItemAssigns(long shoppinglistId, Principal principal) {
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId,uprzywilejowany.getId())
                || userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppinglistId,principal.getName())) {
        List<ItemShoppinglist> itemShoppinglist = itemShoppinglistRepository.findItemShoppinglistsByShoppinglistId(shoppinglistId);
        Shoppinglist shoppinglist = shoppinglistRepository.findShoppinglistById(shoppinglistId);
        List<UserShoppinglist> userShoppinglist = userShoppinglistRepository.findUserShoppinglistsByShoppinglistId(shoppinglistId);
        itemShoppinglist.forEach(itemlist -> itemlist.setShoppinglist(null));
        itemShoppinglist.forEach(itemlist -> itemlist.setItem(null));
        userShoppinglist.forEach(userlist -> userlist.setUser(null));
        userShoppinglist.forEach(userlist -> userlist.setShoppinglist(null));
        itemShoppinglistRepository.deleteAll(itemShoppinglist);
        userShoppinglistRepository.deleteAll(userShoppinglist);
        shoppinglistRepository.delete(shoppinglist);}
        else
        {
            System.out.println("Ten użytkownik nie należy do danej listy zakupowej w związku z czym nie ma prawa jej usunąć");
        }
    }

    public void deleteUserFromShoppingListAssigns(long shoppinglistId, long userId, Principal principal) {
        User uprzywilejowany = userRepository.findByUsername(principal.getName());
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId,uprzywilejowany.getId())
                || userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndOwner(shoppinglistId,principal.getName())) {
            UserShoppinglist userShoppinglist = userShoppinglistRepository.findUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId, userId);
            userShoppinglist.setUser(null);
            userShoppinglist.setShoppinglist(null);
            userShoppinglistRepository.delete(userShoppinglist);}
        else{
            System.out.println("Ten użytkownik nie należy do danej listy zakupowej w związku z czym nie może usunąć użytkowników rozliczanych w liście");
        }
    }
}
