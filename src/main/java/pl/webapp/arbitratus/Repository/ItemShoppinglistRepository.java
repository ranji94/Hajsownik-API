package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Item;
import pl.webapp.arbitratus.Entity.ItemShoppinglist;
import pl.webapp.arbitratus.Entity.Shoppinglist;

import java.util.List;

@Repository
public interface ItemShoppinglistRepository extends JpaRepository<ItemShoppinglist, Long> {
    ItemShoppinglist findItemShoppinglistByShoppinglistId(long shoppinglistid);
    ItemShoppinglist findItemShoppinglistByShoppinglistIdAndItemId(long shoppinglistId, long itemId);
    List<ItemShoppinglist> findItemShoppinglistsByShoppinglistId(long shoppinglistId);
    //List<ItemShoppinglist> findItemShoppinglistsByShoppinglistId(long shoppinglistid);

    @Query(value = "SELECT i.item FROM ItemShoppinglist i WHERE i.shoppinglist = :shoppingid")
    List<Item> getAllItems(@Param("shoppingid")Shoppinglist shoppinglist);

    @Query(value = "SELECT i.total FROM ItemShoppinglist i WHERE i.shoppinglist = :shoppingid")
    List<Float> getTotalValues(@Param("shoppingid")Shoppinglist shoppinglist);

}
