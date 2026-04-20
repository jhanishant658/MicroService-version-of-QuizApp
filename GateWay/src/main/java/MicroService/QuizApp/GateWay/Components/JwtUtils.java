package MicroService.QuizApp.GateWay.Components;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
     private final String SECRET = "mysecretkeymysecretkeymysecretkey123"; // min 32 chars

       private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
     private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String getUserRole(String token) {
    Object role = extractAllClaims(token).get("role");
    return role != null ? role.toString() : null;
}
    public boolean validateToken(String token, String username) {
        try{
        if(isTokenExpired(token)) {
            return false;
        }
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username);}
        catch(Exception e){
            return false;
        }
    }
}