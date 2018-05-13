package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.auth.TokenHandler;
import com.bazalytskyi.coursework.auth.UserAuthentication;
import com.bazalytskyi.coursework.entities.CustomUserDetails;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService implements ITokenAuthenticationService {
    @Autowired
    private TokenHandler tokenHandler;

    public String addAuthentication(HttpServletResponse response, CustomUserDetails userPrincipal) {
        String token = tokenHandler.createAccessToken(userPrincipal);
        return token;
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response, String authToken) {
        String accessToken = authToken;
        if (!accessToken.isEmpty()) {
            try {
                CustomUserDetails user = tokenHandler.parseSessionUser(accessToken);//Виняток ExpiredJwtException якщо термін токену вийшов
                return new UserAuthentication(user);

            } catch (JwtException ex) {
                return null;
            }
        }
        return null;
    }

}
