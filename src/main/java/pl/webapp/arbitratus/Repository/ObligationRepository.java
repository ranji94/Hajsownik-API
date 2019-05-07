package pl.webapp.arbitratus.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.webapp.arbitratus.Entity.Obligation;

import java.util.List;

@Repository
public interface ObligationRepository extends CrudRepository<Obligation, Long> {
    Obligation findObligationByWhoAndWhom(String who, String whom);
    List<Obligation> findObligationsByWhoAndWhom(String who, String whom);
    List<Obligation> findObligationsByWhom(String whom);
    List<Obligation> findObligationsByWho(String who);
    boolean existsByWhoAndWhom(String who, String whom);
}
