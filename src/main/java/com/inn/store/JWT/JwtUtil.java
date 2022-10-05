package com.inn.store.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
// Claims : payload
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }


    // get Clamis who we want
    public <T> T extractClaims(String token , Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // extract All data of the payload  == payload
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
    }


    // check if the token is Expired  or not  (2023 before 2022 => false => isTokenExpired =false)
    // (2020 before 2022=> true => isTokenExpired =false)
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String genereteToken(String username , String role , String name , Integer id){
        Map<String,Object> claims= new HashMap<>();
        claims.put("role",role);
        claims.put("id",id);
        claims.put("name",name);
      return  createToken(claims,username);

    }

    public String createToken(Map<String,Object> claims , String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.TOKEN_SECRET).compact();
    }


    public Boolean validatedToken(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}
