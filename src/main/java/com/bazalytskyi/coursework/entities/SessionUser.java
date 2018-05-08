package com.bazalytskyi.coursework.entities;

import com.bazalytskyi.coursework.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SessionUser implements UserDetails {
    private UserEntity user;
    @Autowired
    private IUserService userService;

    public SessionUser(UserEntity user){
        this.user = user;
    }

    public SessionUser(long id, String subject, List<GrantedAuthority> grantedAuthorities) {
        this.user=userService.getUserById(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isTokenExpired();
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isTokenExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
