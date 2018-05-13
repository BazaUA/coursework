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
    private String USER_ID = "user_id";
    private String AUTHORITIES = "authorities";


    public String createAccessToken(CustomUserDetails userPrincipal) {
        final Date now = new Date();

        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put(USER_ID, String.valueOf(userPrincipal.getUser().getId()));
        claims.put(AUTHORITIES, userPrincipal.getAuthorities().get(0).getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TimeUnit.MINUTES.toMillis(10L)))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, "some-random-secret-key")
                .compact();
    }

    public CustomUserDetails parseSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("some-random-secret-key")
                .parseClaimsJws(token)
                .getBody();
        String stringAuth = (String) claims.get(AUTHORITIES);
        CustomUserDetails user = new CustomUserDetails(Long.parseLong((String) claims.get(USER_ID)),
                claims.getSubject(),
                stringAuth
        );
        return user;
    }

}
