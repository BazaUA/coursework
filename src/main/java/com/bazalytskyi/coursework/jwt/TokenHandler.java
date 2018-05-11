package com.bazalytskyi.coursework.jwt;

import com.bazalytskyi.coursework.dao.RefreshTokenDao;
import com.bazalytskyi.coursework.entities.SessionUser;
import com.bazalytskyi.coursework.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class TokenHandler {
    @Autowired
    private RefreshTokenDao refreshTokenDao;
    @Autowired
    private UserService userService;
    private String USER_ID = "user_id";
    private String AUTHORITIES = "authorities";


    public String createAccessToken(SessionUser userPrincipal) {
        return createAccessToken(userPrincipal, null);
    }

    public String createAccessToken(SessionUser userPrincipal, String refreshToken) {
        final Date now = new Date();
        if (refreshToken == null) {
            refreshToken = UUID.randomUUID().toString();
            refreshTokenDao.remove(userPrincipal.getUsername());
            refreshTokenDao.insert(userPrincipal.getUsername(), refreshToken, now.getTime() + TimeUnit.HOURS.toMillis(1L));
        }

        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put(USER_ID, String.valueOf(userPrincipal.getUser().getId()));
        claims.put(AUTHORITIES, userPrincipal.getAuthorities().get(0).getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setId(refreshToken)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TimeUnit.MINUTES.toMillis(10L)))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, "some-random-secret-key")
                .compact();
    }

    public SessionUser parseSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("some-random-secret-key")
                .parseClaimsJws(token)
                .getBody();
        String stringAuth = (String) claims.get(AUTHORITIES);
        SessionUser user = new SessionUser(Long.parseLong((String) claims.get(USER_ID)),
                claims.getSubject(),
               stringAuth
        );
        return user;
    }


    public SessionUser updateSessionUser(Claims claims) {
        String refreshToken = claims.getId();
        String username = claims.getSubject();
        //Отодвигаем время истечения срока действия на час вперед, если токен не просрочен
        int updated = refreshTokenDao.updateIfNotExpired(username, refreshToken, TimeUnit.HOURS.toMillis(1L));
        if (updated == 0) {
            throw new JwtException("Token is expired or missing");
        }
        return (SessionUser) userService.loadUserByUsername(username);
    }
}
