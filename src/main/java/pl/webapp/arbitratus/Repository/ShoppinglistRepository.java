package pl.webapp.arbitratus.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Shoppinglist;

import java.util.List;

@Repository
public interface ShoppinglistRepository extends PagingAndSortingRepository<Shoppinglist, Long> {
    Shoppinglist findShoppinglistById(long id);
    boolean existsShoppinglistById(long id);
    Page<Shoppinglist> findAll(Pageable pageable);
    //@Query("Select s.items From Shoppinglist s where s.items=?1")
    //List<Item> getAllItems(long shoppinglistId);

}
