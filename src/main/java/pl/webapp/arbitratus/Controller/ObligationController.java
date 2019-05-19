package pl.webapp.arbitratus.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Obligation;
import pl.webapp.arbitratus.Model.ObligationStack;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Security.JwtTokenProvider;
import pl.webapp.arbitratus.Service.ObligationService;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
public class ObligationController {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    ObligationService obligationService;
    @Autowired
    UserRepository userRepository;

    public ObligationController(ObligationService obligationService, UserRepository userRepository) {
        this.obligationService=obligationService;
        this.userRepository = userRepository;
    }

    /*@GetMapping("/liabilities")
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
    }*/

    @GetMapping("/credits/sum")
    public ResponseEntity<Float> getCreditsSum(Principal principal){
        try{
            return new ResponseEntity<Float>(obligationService.getCreditsSum(principal), HttpStatus.OK);
        } catch(IllegalArgumentException | NullPointerException e){
            logger.error("Wystąpił wyjątek "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/liabilities/sum")
    public ResponseEntity<Float> getLiabilitiesSum(Principal principal){
        try{
            return new ResponseEntity<Float>(obligationService.getLiabilitiesSum(principal), HttpStatus.OK);
        } catch(IllegalArgumentException | NullPointerException e){
            logger.error("Wystąpił wyjątek "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/liabilitiesstack")
    public ResponseEntity<List<ObligationStack>> showAllLiabilitiesStack(Principal principal)
    {
        try{
            return new ResponseEntity<List<ObligationStack>>(obligationService.getLiabilitiesStack(principal), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            logger.error("Wystąpił wyjątek "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/creditsstack")
    public ResponseEntity<List<ObligationStack>> showAllCreditsStack(Principal principal)
    {
        try{
            return new ResponseEntity<List<ObligationStack>>(obligationService.getCreditsStack(principal), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            logger.error("Wystąpił wyjątek" + e.getMessage());
            return null;
        }
    }
}
