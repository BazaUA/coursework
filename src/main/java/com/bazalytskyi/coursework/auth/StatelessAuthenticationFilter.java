package com.bazalytskyi.coursework.auth;

import com.bazalytskyi.coursework.services.ITokenAuthenticationService;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final ITokenAuthenticationService tokenAuthenticationService;
    static final String AUTHORIZATION = "Authorization";
    static final int BEGIN_INDEX = 7;
    public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        String authToken = httpServletRequest.getHeader(AUTHORIZATION);
        if(authToken != null) {
            authToken = new String(authToken.substring(BEGIN_INDEX).getBytes(), "UTF-8");
            Authentication authentication = tokenAuthenticationService.getAuthentication(httpServletRequest, httpServletResponse,authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
