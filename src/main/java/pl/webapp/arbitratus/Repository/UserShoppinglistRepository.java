package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.UserShoppinglist;

@Repository
public interface UserShoppinglistRepository extends JpaRepository<UserShoppinglist, Long> {
}
