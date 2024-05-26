package com.ia.chatbot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final String SECRET_KEY="2F423F4528482B4D6251655468576D597133743677397A24432646294J404E63";
    private static  final  int jwtExpirationMs=10800000;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // generate a JWT token with default claims and user details
    public String createToken(UserDetails userDetails, Long userId, String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        claims.put("userId", userId);
        claims.put("username", userName);

        //Set<String> permissionNames = permissions.stream()
            //    .map(Permission::getName)
                //.map(Enum::name)
          //      .collect(Collectors.toSet());
       // claims.put("permissions", permissionNames);

        return generateToken(claims, userDetails);
    }


    // generate a JWT token with additional custom claims and user details
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtExpirationMs);

        // build the JWT token
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer("chatbot")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    // extract a claim from the JWT token using a function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    // check if the JWT token is valid for the given user details
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // check if the JWT token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(Instant.now()));
    }


    // extract the expiration date from the JWT token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // get the signing key from the JWT secret
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
