package com.bazalytskyi.coursework.jwt;

import com.bazalytskyi.coursework.entities.SessionUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class UserAuthentication implements Authentication {
    private SessionUser user;
    public UserAuthentication(SessionUser user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
