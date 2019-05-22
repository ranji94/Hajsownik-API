package pl.webapp.arbitratus.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.*;
import pl.webapp.arbitratus.Model.ObligationStack;
import pl.webapp.arbitratus.Repository.*;
import pl.webapp.arbitratus.Security.JwtTokenProvider;

import java.security.Principal;
import java.util.*;

@Service
public class ObligationService {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

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

    public List<ObligationStack> getLiabilitiesStack(Principal principal)
    {
        long id = 1;
        List<Obligation> liabilities = obligationRepository.findObligationsByWho(principal.getName());
        List<ObligationStack> stosObligacji = new ArrayList<>();
        Set<String> exists = new HashSet<>();

        for(Obligation liability : liabilities){
            exists.add(liability.getWhom());
        }

        for(Obligation liability : liabilities){
            if(exists.contains(liability.getWhom()))
            {
                float suma = 0;
                exists.remove(liability.getWhom());
                List<Obligation> obligationOneWhom = obligationRepository.findObligationsByWhoAndWhom(principal.getName(), liability.getWhom());
                ObligationStack oneStack = new ObligationStack();
                oneStack.setName(liability.getWhom());
                oneStack.setId(id);
                id++;

                for(Obligation oneWhomAmount : obligationOneWhom){
                    suma += oneWhomAmount.getAmount();
                }
                oneStack.setAmount(suma);
                stosObligacji.add(oneStack);
            }
        }
        return stosObligacji;
    }

    public List<ObligationStack> getReducedCredits(Principal principal) {
        List<ObligationStack> credits = this.getCreditsStack(principal);
        List<ObligationStack> liabilities = this.getLiabilitiesStack(principal);
        try{
        for(int i=0;i<credits.size();i++){
            for(ObligationStack liability : liabilities){
                if(credits.get(i).getName().equals(liability.getName())){
                    float redukcja = credits.get(i).getAmount() - liability.getAmount();

                    if(redukcja<0){
                        credits.remove(credits.get(i));
                    }
                    else {
                        credits.get(i).setAmount(redukcja);
                    }
                }
            }
        }
        return credits;
        }
        catch(IndexOutOfBoundsException e){
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<ObligationStack> getReducedLiabilities(Principal principal) {
        List<ObligationStack> credits = this.getCreditsStack(principal);
        List<ObligationStack> liabilities = this.getLiabilitiesStack(principal);
        try {
            for (int i = 0; i < liabilities.size(); i++) {
                for (ObligationStack credit : credits) {
                    if (liabilities.get(i).getName().equals(credit.getName())) {
                        float redukcja = liabilities.get(i).getAmount() - credit.getAmount();
                        if (redukcja < 0) {
                            liabilities.remove(liabilities.get(i));
                        } else {
                            liabilities.get(i).setAmount(redukcja);
                        }
                    }
                }
            }
            return liabilities;
        } catch(IndexOutOfBoundsException e){
            logger.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    public float getCreditsSum(Principal principal)
    {
        try {
            List<ObligationStack> obligations = this.getReducedCredits(principal);
            float sum = 0.0F;
            for (ObligationStack ob : obligations) {
                sum += ob.getAmount();
            }
            return sum;
        } catch(IndexOutOfBoundsException e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    public float getLiabilitiesSum(Principal principal)
    {
        try {
            List<ObligationStack> obligations = this.getReducedLiabilities(principal);
            float sum = 0.0F;
            for (ObligationStack ob : obligations) {
                sum += ob.getAmount();
            }
            return sum;
        } catch(IndexOutOfBoundsException e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    public List<ObligationStack> getCreditsStack(Principal principal)
    {
        long id = 1;
        List<Obligation> credits = obligationRepository.findObligationsByWhom(principal.getName());
        List<ObligationStack> stosObligacji = new ArrayList<>();
        Set<String> exists = new HashSet<>();

        for(Obligation credit : credits){
            exists.add(credit.getWho());
        }

        for(Obligation credit : credits){
            if(exists.contains(credit.getWho()))
            {
                float suma = 0;
                exists.remove(credit.getWho());
                List<Obligation> obligationOneWho = obligationRepository.findObligationsByWhoAndWhom(credit.getWho(),principal.getName());
                ObligationStack oneStack = new ObligationStack();
                oneStack.setName(credit.getWho());
                oneStack.setId(id);
                id++;

                for(Obligation oneWhoAmount : obligationOneWho){
                    suma += oneWhoAmount.getAmount();
                }
                oneStack.setAmount(suma);
                stosObligacji.add(oneStack);
            }
        }
        return stosObligacji;
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
        try {
            float total = shoppinglistRepository.findShoppinglistById(shoppinglistId).getListtotal();
            int usersInList = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(shoppinglistId).size() + 1;
            float kwota = (float) (Math.round((total / usersInList) * 100.0) / 100.0);
            System.out.println("Obliczono kwote do podzialu pomiedzy " + usersInList + " uzytkownikow: " + kwota);
            List<ObligationShoppinglist> zadluzeniaListy = obligationShoppinglistRepository.findObligationShoppinglistsByShoppinglistId(shoppinglistId);

            for (ObligationShoppinglist obligacja : zadluzeniaListy) {
                obligacja.getObligation().setAmount(kwota);
                obligationRepository.save(obligacja.getObligation());
            }
        } catch (NullPointerException e)
        {
            logger.error("Użytkownik już istnieje na liście");
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
