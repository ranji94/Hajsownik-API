package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ItemShoppinglist;
import pl.webapp.arbitratus.Entity.Shoppinglist;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ItemShoppinglistRepository;
import pl.webapp.arbitratus.Repository.ShoppinglistRepository;
import pl.webapp.arbitratus.Repository.UserRepository;

import java.util.*;

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

    public ShoppingService(ShoppinglistRepository shoppingListRepository, ItemRepository itemRepository, ItemShoppinglistRepository itemShoppinglistRepository) {
        this.shoppinglistRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.itemShoppinglistRepository = itemShoppinglistRepository;
    }

    public void createRelationship(long shoppingListId, long itemId, int quantity)
    {
        ItemShoppinglist itemShoppingList = new ItemShoppinglist();
        itemShoppingList.setItem(itemRepository.findItemById(itemId));
        itemShoppingList.setShoppinglist(shoppinglistRepository.findShoppinglistById(shoppingListId));
        itemShoppingList.setQuantity(quantity);
        itemShoppingList.setTotal((quantity*itemRepository.findItemById(itemId).getPrice()));
        //Item item = itemRepository.findItemById(itemId);
        //list.getItems().add(item);
        //this.shoppingListRepository.save(list);
        this.itemShoppinglistRepository.save(itemShoppingList);
    }

    public List<Item> getAllItems(long shoppinglistId)
    {
        return itemShoppinglistRepository.getAllItems(shoppinglistRepository.findShoppinglistById(shoppinglistId));
    }

    public void assignUserToShoppinglist(long shoppinglistId, long userId)
    {
        Shoppinglist lista = shoppinglistRepository.findShoppinglistById(shoppinglistId);
        User user = userRepository.findUserById(userId);
        lista.getUsers().add(user);
        this.shoppinglistRepository.save(lista);
    }
}
