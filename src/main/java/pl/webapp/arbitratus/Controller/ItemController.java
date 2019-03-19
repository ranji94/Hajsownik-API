package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Service.ItemService;

@RestController
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items/create")
    public Item createNewItem(@RequestBody Item item)
    {
        return itemService.createNewItem(item);
    }
}
