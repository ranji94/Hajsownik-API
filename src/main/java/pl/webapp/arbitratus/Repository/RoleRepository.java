package pl.webapp.arbitratus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName
}
