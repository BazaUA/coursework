package com.bazalytskyi.coursework.auth;


import com.bazalytskyi.coursework.entities.CustomUserDetails;
import com.bazalytskyi.coursework.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class TokenHandler {
    @Autowired
    private UserService userService;
    private String USER = "user_id";
    private String ROLES = "roles";
    private String key = "random-key";


    public String createAccessToken(CustomUserDetails userPrincipal) {
        final Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put(USER, String.valueOf(userPrincipal.getUser().getId()));
        claims.put(ROLES, userPrincipal.getAuthorities().get(0).getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TimeUnit.MINUTES.toMillis(10L)))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, key)
                .compact();
    }

    public CustomUserDetails parseSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        String authorities = (String) claims.get(ROLES);
        CustomUserDetails user = new CustomUserDetails(Long.parseLong((String) claims.get(USER)),
                claims.getSubject(),
                authorities
        );
        return user;
    }
}
