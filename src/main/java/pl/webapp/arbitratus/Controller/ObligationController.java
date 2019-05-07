package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Obligation;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Service.ObligationService;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
public class ObligationController {
    @Autowired
    ObligationService obligationService;
    @Autowired
    UserRepository userRepository;

    public ObligationController(ObligationService obligationService, UserRepository userRepository) {
        this.obligationService=obligationService;
        this.userRepository = userRepository;
    }

    /*//UTWÓRZ NOWE OBCIĄŻENIE WOBEC UŻYTKOWNIKA O ID={debtorID}
    @PostMapping("/user/{debtorId}/obligation")
    public ResponseEntity<Obligation> createObligation(@PathVariable(name="debtorId") long debtorId, Principal principal) {
        try {
            return new ResponseEntity<Obligation>(obligationService.createNewObligation(debtorId, principal), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e)
        {
            System.out.println("Wystąpił wyjątek:"+ e.getMessage());
            return null;
        }
    }*/

    //OBCIĄŻ UŻYTKOWNIKA O ID={debtorID} KWOTĄ {amount}
    /*@PutMapping("/user/{debtorId}/obligation/{amount}")
    public ResponseEntity<Obligation> updateObligationAmount(@PathVariable(name="debtorId") long debtorId, @PathVariable(name="amount") float amount, Principal principal)
    {
        try {
            if(debtorId!=userRepository.findByUsername(principal.getName()).getId())
            return null;//new ResponseEntity<Obligation>(obligationService.setAmount(debtorId, amount, principal), HttpStatus.CREATED);
            else
                return new ResponseEntity<Obligation>(HttpStatus.BAD_REQUEST);
        } catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Wyskoczył wyjątek: "+e.getMessage());
            return null;
        }
    }*/

    //POKAŻ DŁUGI I UZNANIA OSOBY ZALOGOWANEJ:
    /*@GetMapping("/obligations")
    public ResponseEntity<List<Obligation>> showAllObligations(Principal principal)
    {
        try{
            return new ResponseEntity<List<Obligation>>(obligationService.getAllObligations(principal), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Wyskoczył wyjątek: "+e.getMessage());
            return null;
        }
    }*/

    @GetMapping("/liabilities")
    public ResponseEntity<List<Obligation>> showAllLiabilities(Principal principal)
    {
        try{
            return new ResponseEntity<List<Obligation>>(obligationService.getLiabilites(principal), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Wyskoczył wyjątek: "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/credits")
    public ResponseEntity<List<Obligation>> showAllCredits(Principal principal)
    {
        try{
            return new ResponseEntity<List<Obligation>>(obligationService.getCredits(principal), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Wyskoczył wyjątek: "+e.getMessage());
            return null;
        }
    }
}
