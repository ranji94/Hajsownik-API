package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsItemByName(String name);
    boolean existsItemByShop(String shop);
    boolean existsItemByNameAndShop(String name, String shop);
    Item findItemById(long id);
}
