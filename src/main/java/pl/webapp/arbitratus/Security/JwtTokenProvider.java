package pl.webapp.arbitratus.Security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")  //Wartość zapisana w Application.Properties
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))  //kogo dotyczy token
                .setIssuedAt(new Date()) // data utworzenia
                .setExpiration(expiryDate) // data utraty ważności
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //algorytm szyfrowania i tajny klucz
                .compact();
    }

    public Long getUserIdFromJWT(String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch(SignatureException e)
        {
            logger.error("Niewłaściwa sygnatura JWT");
        } catch(MalformedJwtException e)
        {
            logger.error("Niewłaściwy token JWT");
        } catch(ExpiredJwtException e)
        {
            logger.error("Token nieważny");
        } catch(UnsupportedJwtException e)
        {
            logger.error("Niewspierany token JWT");
        } catch(IllegalArgumentException e)
        {
            logger.error("JWT otrzymał pusty ciąg znaków");
        }
        return false;
    }
}
