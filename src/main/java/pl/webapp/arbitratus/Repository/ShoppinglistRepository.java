package pl.webapp.arbitratus.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Shoppinglist;

@Repository
public interface ShoppinglistRepository extends CrudRepository<Shoppinglist, Long> {
    Shoppinglist findShoppinglistById(long id);
    //@Query("Select s.items From Shoppinglist s where s.items=?1")
    //List<Item> getAllItems(long shoppinglistId);

}
