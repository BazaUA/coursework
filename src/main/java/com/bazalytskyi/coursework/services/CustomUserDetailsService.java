package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.dao.IRoleDAO;
import com.bazalytskyi.coursework.dao.IUserDAO;
import com.bazalytskyi.coursework.entities.PrivilegeEntity;
import com.bazalytskyi.coursework.entities.RoleEntity;
import com.bazalytskyi.coursework.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private IUserDAO userRepository;

    @Autowired
    private IUserService service;



    @Autowired
    private IRoleDAO roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(
                            roleRepository.findByName("ROLE_USER")));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
           RoleEntity role) {

        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(RoleEntity role) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();

            collection.addAll(role.getPrivileges());

        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
