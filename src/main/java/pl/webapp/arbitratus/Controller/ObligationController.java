package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import pl.webapp.arbitratus.Entity.Obligation;
import pl.webapp.arbitratus.Service.ObligationService;

import java.security.Principal;

@Controller
@CrossOrigin
public class ObligationController {
    @Autowired
    ObligationService obligationService;

    public ObligationController(ObligationService obligationService) {
        this.obligationService=obligationService;
    }

    //UTWÓRZ NOWE OBCIĄŻENIE WOBEC UŻYTKOWNIKA O ID={debtorID}
    @PostMapping("/user/{debtorId}/obligation")
    public ResponseEntity<Obligation> createObligation(@PathVariable(name="debtorId") long debtorId, Principal principal) {
        try {
            return new ResponseEntity<Obligation>(obligationService.createNewObligation(debtorId, principal), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e)
        {
            System.out.println("Wystąpił wyjątek:"+ e.getMessage());
            return null;
        }
    }

    //OBCIĄŻ UŻYTKOWNIKA O ID={debtorID} KWOTĄ {amount}
    @PutMapping("/user/{debtorId}/obligation/{amount}")
    public ResponseEntity<Obligation> updateObligationAmount(@PathVariable(name="debtorId") long debtorId, @PathVariable(name="amount") float amount, Principal principal)
    {
        try {
            return new ResponseEntity<Obligation>(obligationService.updateAmount(debtorId, amount, principal), HttpStatus.CREATED);
        } catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Wyskoczył wyjątek: "+e.getMessage());
            return null;
        }
    }
}
