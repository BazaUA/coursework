package com.bazalytskyi.coursework.jwt;

import com.bazalytskyi.coursework.dao.IRefreshTokenDao;
import com.bazalytskyi.coursework.entities.CustomUserPrincipal;
import com.bazalytskyi.coursework.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class TokenHandler {
    @Autowired
    private IRefreshTokenDao refreshTokenDao;

    public String createAccessToken(CustomUserPrincipal userPrincipal) {
        return createAccessToken(userPrincipal, null);
    }

    public String createAccessToken(CustomUserPrincipal userPrincipal, String refreshToken) {
        final Date now = new Date();
        if (refreshToken == null) {
            refreshToken = UUID.randomUUID().toString();
            refreshTokenDao.insert(userPrincipal.getUsername(), refreshToken, now.getTime() + TimeUnit.HOURS.toMillis(1L));
        }

        UserEntity entity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put("user_id", String.valueOf(userPrincipal.getUser().getId()));
        claims.put("authorities", userPrincipal.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setId(refreshToken)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TimeUnit.MINUTES.toMillis(10L)))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, "some-random-secret-key")
                .compact();
    }
}
