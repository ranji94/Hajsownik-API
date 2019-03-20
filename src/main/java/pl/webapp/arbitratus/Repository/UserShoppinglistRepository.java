package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.UserShoppinglist;

import java.util.List;

@Repository
public interface UserShoppinglistRepository extends JpaRepository<UserShoppinglist, Long> {
    List<UserShoppinglist> findUserShoppinglistsByShoppinglistId(long shoppinglistId);
    UserShoppinglist findUserShoppinglistByShoppinglistIdAndUserId(long shoppinglistId, long userId);
    boolean existsUserShoppinglistByShoppinglistIdAndUserId(long shoppinglistId, long userId);
    boolean existsUserShoppinglistByShoppinglistIdAndOwner(long shoppinglistId, String owner);
}
