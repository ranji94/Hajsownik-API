package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.ObligationShoppinglist;

import java.util.List;

@Repository
public interface ObligationShoppinglistRepository extends JpaRepository<ObligationShoppinglist, Long> {
    List<ObligationShoppinglist> findObligationShoppinglistsByShoppinglistId(long shoppinglistId);
}
