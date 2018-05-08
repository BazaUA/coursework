package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.SessionUser;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITokenAuthenticationService {
    void addAuthentication(HttpServletResponse response, SessionUser userPrincipal);

    Authentication getAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse);
}
