package SpringBootStarterProject.ManagingPackage.Security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String secret_Key="ohQN9NiajJ7A9RSXegN1uaiTUeaHj1klaXC2QJ+Tr5ZCtyxEPKYrmifS7PHW4Rxv";

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails)
    {
     return generateToken(new HashMap<>(),userDetails);
    }

    public String extractUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }
    public String generateToken(Map<String, Object> extraClaims,
                               UserDetails userDetails)
    {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*24*60*10000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);

    }
    public  Object  extractUserType(String token)
{

        // Parse the JWT token
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Ensure you have a method to generate the signing key
                .build()
                .parseClaimsJws(token);

        // Extract the claims
        Claims claims = jwsClaims.getBody();
        System.out.println("Claims: " + claims.get("UserType"));

return claims.get("UserType");

}


    private Key getSignInKey() {
       byte[] keyBytes= Decoders.BASE64.decode(secret_Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
