package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Repository.ItemRepository;
import pl.webapp.arbitratus.Repository.ShoppinglistRepository;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ShoppinglistRepository shoppinglistRepository;

    //DODANIE (ZWERYFIKOWANEGO W PRZYSZLOSCI) OBIEKTU DO OGÓLNEJ POWSZECHNEJ BAZY DANYCH Z PRODUKTAMI
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

    //POBIERZ ELEMENT PO ID
    public Item getItemById(long id)
    {
        return itemRepository.findItemById(id);
    }

    //POBIERZ WSZYTSTKIE  -  TO POTRZEBNE DO WYSZUKIWARKI PRZEDMIOTÓW PO STRONIE FRONTENDU
    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }


}
