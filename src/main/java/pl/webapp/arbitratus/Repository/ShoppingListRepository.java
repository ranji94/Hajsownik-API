package pl.webapp.arbitratus.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.ShoppingList;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
}
