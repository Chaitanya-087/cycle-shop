package com.example.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  public static final String SECRET =
    "1239801283012830182390123809128390128301928301923801923";

  public void validateToken(String token) {
    Jwts
      .parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token);
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    String username = userDetails.getUsername();
    claims.put("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    return createToken(claims, username);
  }

  public String createToken(Map<String, Object> claims, String username) {
    return Jwts
      .builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
