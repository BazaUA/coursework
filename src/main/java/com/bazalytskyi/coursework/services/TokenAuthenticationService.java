package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.CustomUserPrincipal;
import com.bazalytskyi.coursework.jwt.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService implements ITokenAuthenticationService {
    @Autowired
    TokenHandler tokenHandler;
//    @Autowired
//    Request request;

    public void addAuthentication(HttpServletResponse response, CustomUserPrincipal userPrincipal) {
        Cookie cookie = new Cookie("access_token", tokenHandler.createAccessToken(userPrincipal));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
    }
}
