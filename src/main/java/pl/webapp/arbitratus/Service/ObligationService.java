package pl.webapp.arbitratus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.*;
import pl.webapp.arbitratus.Repository.*;

import java.security.Principal;
import java.util.List;

@Service
public class ObligationService {
    @Autowired
    ObligationRepository obligationRepository;
    @Autowired
    ObligationShoppinglistRepository obligationShoppinglistRepository;
    @Autowired
    UserShoppinglistRepository userShoppinglistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShoppinglistRepository shoppinglistRepository;

    public ObligationService(ObligationRepository obligationRepository, UserShoppinglistRepository userShoppinglistRepository, UserRepository userRepository, ShoppinglistRepository shoppinglistRepository) {
        this.obligationRepository = obligationRepository;
        this.userShoppinglistRepository = userShoppinglistRepository;
        this.userRepository = userRepository;
        this.shoppinglistRepository = shoppinglistRepository;
    }

    //POKAZ WSZYSTKIE ZADLUZENIA
    public List<Obligation> getLiabilites(Principal principal)
    {
        return obligationRepository.findObligationsByWho(principal.getName());
    }

    //POKAZ WSZYSTKIE UZNANIA
    public List<Obligation> getCredits(Principal principal)
    {
        return obligationRepository.findObligationsByWhom(principal.getName());
    }

    public Obligation createNewObligation(long debtorId, Principal principal, long shoppinglistId) {
        if(userShoppinglistRepository.existsUserShoppinglistByShoppinglistIdAndUserId(shoppinglistId, debtorId)) {
            if (principal.getName().equals(userRepository.findUserById(debtorId).getUsername())) {
                System.out.println("Nie można tworzyć zobowiązań finansowych wobec siebie samego!");
                return null;
            } else {
                Obligation obligation = new Obligation();
                ObligationShoppinglist obligationShoppinglist = new ObligationShoppinglist();
                obligationShoppinglist.setObligation(obligation);
                obligationShoppinglist.setShoppinglist(shoppinglistRepository.findShoppinglistById(shoppinglistId));
                obligation.setWhom(principal.getName());
                obligation.setWho(userRepository.findUserById(debtorId).getUsername());
                obligationShoppinglistRepository.save(obligationShoppinglist);
                return obligationRepository.save(obligation);
            }
        }
        else {
            System.out.println("Już dodałeś użytkownika do tej listy");
            return null;
        }
    }

    public void odwrocDlugi(Obligation obligation)
    {
        if(obligation.getAmount()<0)
        {
            String who = obligation.getWho();
            String whom = obligation.getWhom();
            obligation.setAmount(obligation.getAmount()*(-1));
            obligation.setWhom(who);
            obligation.setWho(whom);
            obligationRepository.save(obligation);
        }
    }

    public void calculateListObligations(long shoppinglistId) //Wykonywać po zatwierdzeniu listy
    {
        float total = shoppinglistRepository.findShoppinglistById(shoppinglistId).getListtotal();
        int usersInList = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(shoppinglistId).size() + 1;
        float kwota = (float) (Math.round((total/usersInList) * 100.0) / 100.0);
        System.out.println("Obliczono kwote do podzialu pomiedzy "+usersInList+" uzytkownikow: "+kwota);
        List<ObligationShoppinglist> zadluzeniaListy = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(shoppinglistId);

        for(ObligationShoppinglist obligacja : zadluzeniaListy)
        {
            obligacja.getObligation().setAmount(kwota);
            obligationRepository.save(obligacja.getObligation());
        }
    }

    public List<Obligation> getAllObligations(Principal principal)
    {
        List<Obligation> dlugiUznania = obligationRepository.findObligationsByWho(principal.getName());
        dlugiUznania.addAll(obligationRepository.findObligationsByWhom(principal.getName()));
        return dlugiUznania;
    }

    public Obligation getObligationByDebtor(long debtorId, Principal principal)
    {
        return obligationRepository.findObligationByWhoAndWhom(userRepository.findUserById(debtorId).getUsername(),principal.getName());
    }

    public void deleteListObligations(long shoppinglistId)
    {
        List<ObligationShoppinglist> zadluzeniaListy = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(shoppinglistId);
        for(ObligationShoppinglist obligacja : zadluzeniaListy)
        {
            obligationRepository.delete(obligacja.getObligation());
        }
        obligationShoppinglistRepository.deleteAll(zadluzeniaListy);
    }

    public void deleteUserObligations(UserShoppinglist userShoppinglist)
    {
        List<ObligationShoppinglist> obligacje = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(userShoppinglist.getShoppinglist().getId());
        List<Obligation> obligacja = obligationRepository.findObligationsByWho(userShoppinglist.getUser().getUsername());
        for(ObligationShoppinglist obiekt : obligacje) {
            for (Obligation zadluzenie : obligacja)
            {
                if(obiekt.getObligation().getId()==zadluzenie.getId())
                {
                    obligationShoppinglistRepository.delete(obiekt);
                    obligationRepository.delete(zadluzenie);
                }
            }
        }
    }
}
