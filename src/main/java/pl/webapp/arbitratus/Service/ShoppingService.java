package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ItemShoppinglist;
import pl.webapp.arbitratus.Entity.Shoppinglist;
import pl.webapp.arbitratus.Entity.UserShoppinglist;
import pl.webapp.arbitratus.Repository.*;

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

    // DODAJ PRZEDMIOT O ID {itemId}, DO LISTY ZAKUPOWEJ O ID {shoppinglistId} W ILOSCI QUANTITY
    public void createRelationship(long shoppingListId, long itemId, int quantity)
    {
        ItemShoppinglist itemShoppingList = new ItemShoppinglist();
        if(itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppingListId,itemId)!=null)
        {
            itemShoppingList = itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppingListId,itemId);
            itemShoppingList.setQuantity(itemShoppingList.getQuantity() + quantity);
            itemShoppingList.setTotal(itemShoppingList.getQuantity() * itemShoppingList.getItem().getPrice());
            this.itemShoppinglistRepository.save(itemShoppingList);
        }
        else {
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

    //POBIERZ WSZYSTKIE PRZEDMIOTY PRZYPISANE DO LISTY O ID {shoppinglistId}
    public List<Item> getAllItems(long shoppinglistId)
    {
        return itemShoppinglistRepository.getAllItems(shoppinglistRepository.findShoppinglistById(shoppinglistId));
    }

    //DO LISTY ZAKUPOWEJ O ID {shoppinglistId} DODAJ UZYTKOWNIKA KTORY BEDZIE WSPOLDZIELIL KOSZTA
    public void assignUserToShoppinglist(long shoppinglistId, long userId, Principal principal)
    {
        UserShoppinglist userShoppinglist = new UserShoppinglist();
        userShoppinglist.setShoppinglist(shoppinglistRepository.findShoppinglistById(shoppinglistId));
        userShoppinglist.setUser(userRepository.findUserById(userId));
        userShoppinglist.setOwner(principal.getName());
        userShoppinglistRepository.save(userShoppinglist);
    }

    //USUN PRZEDMIOT Z LISTY ZAKUPOWEJ (A WLASCIWIE POWIAZANIE PRZEDMIOTU Z LISTA). NARAZIE ZBUGOWANE
    public void deleteItemFromShoppinglist(long shoppinglistId, long itemId)
    {
        ItemShoppinglist itemShoppinglist = itemShoppinglistRepository.findItemShoppinglistByShoppinglistIdAndItemId(shoppinglistId,itemId);
        Shoppinglist shoppinglist = shoppinglistRepository.findShoppinglistById(shoppinglistId);
        shoppinglist.setListtotal(shoppinglist.getListtotal()-itemShoppinglist.getTotal());
        itemShoppinglistRepository.deleteById(itemShoppinglist.getId());
        shoppinglistRepository.save(shoppinglist);
    }
}
