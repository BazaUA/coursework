package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.CustomUserDetails;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITokenAuthenticationService {
    String addAuthentication(HttpServletResponse response, CustomUserDetails userPrincipal);

    Authentication getAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String authToken);
}
