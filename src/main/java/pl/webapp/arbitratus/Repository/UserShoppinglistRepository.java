package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.UserShoppinglist;

import java.util.List;

@Repository
public interface UserShoppinglistRepository extends JpaRepository<UserShoppinglist, Long> {
    List<UserShoppinglist> findUserShoppinglistsByShoppinglistId(long shoppinglistId);
    List<UserShoppinglist> findUserShoppinglistsByUserId(long userId);
    UserShoppinglist findUserShoppinglistByShoppinglist(long shoppinglistId);
    UserShoppinglist findUserShoppinglistByShoppinglistIdAndUserId(long shoppinglistId, long userId);
    List<UserShoppinglist> findUserShoppinglistsByOwnerAndUserId(String owner, long userId);
    boolean existsUserShoppinglistByShoppinglistIdAndUserId(long shoppinglistId, long userId);
    boolean existsUserShoppinglistByShoppinglistIdAndOwner(long shoppinglistId, String owner);
}

