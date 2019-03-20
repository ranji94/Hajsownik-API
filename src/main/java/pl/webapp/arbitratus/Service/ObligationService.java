package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.Obligation;
import pl.webapp.arbitratus.Repository.ObligationRepository;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Repository.UserShoppinglistRepository;

import java.security.Principal;

@Service
public class ObligationService {
    @Autowired
    ObligationRepository obligationRepository;
    @Autowired
    UserShoppinglistRepository userShoppinglistRepository;
    @Autowired
    UserRepository userRepository;

    public ObligationService(ObligationRepository obligationRepository) {
        this.obligationRepository = obligationRepository;
    }

    public Obligation createNewObligation(long debtorId, Principal principal) {
        if (!obligationRepository.existsByWhoAndWhom(userRepository.findByUsername(principal.getName()).getUsername(), userRepository.findUserById(debtorId).getUsername())) { //CZY DŁUŻNIK ZAŁOŻYŁ NAM JUŻ ZOBOWIĄZANIE FINANSOWE
            if (!obligationRepository.existsByWhoAndWhom(principal.getName(), userRepository.findUserById(debtorId).getUsername())) {
                if (principal.getName().equals(userRepository.findUserById(debtorId).getUsername())) {
                    System.out.println("Nie można tworzyć zobowiązań finansowych wobec siebie samego!");
                    return null;
                } else {
                    Obligation obligation = new Obligation();
                    obligation.setWhom(principal.getName());
                    obligation.setWho(userRepository.findUserById(debtorId).getUsername());
                    return obligationRepository.save(obligation);
                }
            } else {
                System.out.println("Już istnieje zobowiązanie " + userRepository.findUserById(debtorId).getUsername() + " wobec " + principal.getName());
                return null;
            }
        } else {
            System.out.println("Już istnieje odwrotne zobowiązanie finansowe");
            return obligationRepository.findObligationByWhoAndWhom(principal.getName(), userRepository.findUserById(debtorId).getUsername());
        }
    }

    public Obligation updateAmount(long debtorId, float amount, Principal principal) {
        String whom = principal.getName();
        String who = userRepository.findUserById(debtorId).getUsername();

        if(obligationRepository.existsByWhoAndWhom(who, whom) || obligationRepository.existsByWhoAndWhom(whom, who)) {
            if(obligationRepository.existsByWhoAndWhom(who, whom)) {
                Obligation obligation = obligationRepository.findObligationByWhoAndWhom(who, whom);
                obligation.setAmount(obligation.getAmount() + amount);
                return obligationRepository.save(obligation);
            }
            if(obligationRepository.existsByWhoAndWhom(whom, who)) {
                Obligation obligation = obligationRepository.findObligationByWhoAndWhom(whom, who);
                obligation.setAmount(obligation.getAmount() - amount);
                return obligationRepository.save(obligation);
            }
            else
            {
                return null;
            }
        } else {
            System.out.println("Najpierw stwórz zobowiązanie finansowe!");
            return null;
        }
    }
}
