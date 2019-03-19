package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ShoppingList;

import java.util.List;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    public ShoppingList findShoppingListById(long id);
    @Query("Select s.items From ShoppingList s where s.items=?1")
    List<Item> getAllItems(long shoppingListId);
}
