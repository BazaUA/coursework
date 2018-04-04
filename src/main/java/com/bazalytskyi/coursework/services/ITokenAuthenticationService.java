package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.CustomUserPrincipal;

import javax.servlet.http.HttpServletResponse;

public interface ITokenAuthenticationService {
    void addAuthentication(HttpServletResponse response, CustomUserPrincipal userPrincipal);
}
