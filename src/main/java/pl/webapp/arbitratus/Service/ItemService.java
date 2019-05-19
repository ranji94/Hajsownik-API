package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    public Item addItem(Item item) {
        if(!itemRepository.existsItemByNameAndShop(item.getName(), item.getShop()) && item.getPrice()>0)
        {
            return itemRepository.save(item);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wprowadź poprawne dane");
        }
    }
}
