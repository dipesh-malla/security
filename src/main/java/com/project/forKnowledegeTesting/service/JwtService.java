package com.project.forKnowledegeTesting.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 1000 * 60 * 60 * 24 * 7;
    @Value("${jwt.secret}")
    private String SECRET;
    // creating new key every time a server restart will fail to validate the previous key
    // so store the key in the yml file and use it
    // and in constructor place the decoder method to decode the key

    public JwtService() {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//            key = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }
    public String generateAccessToken(String username){
        return buildToken(username, ACCESS_TOKEN_EXPIRATION_MS);
    }

    public String generateRefreshToken(String username){
        return buildToken(username, REFRESH_TOKEN_EXPIRATION_MS);
    }

    public String buildToken( String username, long expiration) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
//        byte[] encodedKey = Decoders.BASE64.decode(key);
//        return Keys.hmacShaKeyFor(encodedKey);
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

   public boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
   }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private<T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claim = extraAllClaims(token);
        return claimResolver.apply(claim);
    }

    private Claims extraAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
