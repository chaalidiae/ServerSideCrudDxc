package ma.dxc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * Cette classe contient l'ensemble des outils et les fonctions qu'on va utiliser dans le traitement des requettes.
 * @author dchaa
 *
 */
@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";
 
    /**
     * Extraction du nom d'utilisateur à partir du token.
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extraction de la date d'expiration du token.
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extraire un Claim.
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extraire l'ensemble des Claims.
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    
    /**
     * Vérifier si le token est expiré.
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Génaration du Token.
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
        		.builder()
        		.setClaims(claims)
        		.setSubject(userDetails.getUsername())
        		.setIssuedAt(new Date(System.currentTimeMillis()))
        		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        		.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        		.claim("roles", userDetails.getAuthorities())
        		.compact();
    }
    
    /**
     * Vérifier si le Token est valide.
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
