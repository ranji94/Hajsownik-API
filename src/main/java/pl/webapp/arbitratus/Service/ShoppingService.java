package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ShoppingList;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ShoppingListRepository;

import java.util.Map;

@Service
public class ShoppingService {
    @Autowired
    ShoppingListRepository shoppingListRepository;
    @Autowired
    ItemRepository itemRepository;

    public ShoppingService(ShoppingListRepository shoppingListRepository, ItemRepository itemRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
    }

    public void createRelationship(long shoppingListId, long itemId)
    {
        ShoppingList list = shoppingListRepository.findShoppingListById(shoppingListId);
        Item item = itemRepository.findItemById(itemId);
        list.getItems().add(item);
        this.shoppingListRepository.save(list);
    }
}
