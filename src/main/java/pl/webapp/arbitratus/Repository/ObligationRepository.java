package pl.webapp.arbitratus.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Obligation;

@Repository
public interface ObligationRepository extends CrudRepository<Obligation, Long> {
    Obligation findObligationByWhoAndWhom(String who, String whom);
    boolean existsByWhoAndWhom(String who, String whom);
}
