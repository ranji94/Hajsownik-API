package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ShoppingListRepository;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ShoppingListRepository shoppingListRepository;

    public Item createNewItem(Item item) {
        if (itemRepository.existsItemByName(item.getName())) {
            if (itemRepository.existsItemByShop(item.getShop())) {
                System.out.println("Ten przedmiot już istneje w bazie");
                return null;
            } else {
                return itemRepository.save(item);
            }
        } else {
            return itemRepository.save(item);
        }
    }

    public Item getItemById(long id)
    {
        return itemRepository.findItemById(id);
    }
}
