package com.bazalytskyi.coursework.controllers;

import com.bazalytskyi.coursework.dto.UserDto;
import com.bazalytskyi.coursework.entities.CustomUserDetails;
import com.bazalytskyi.coursework.entities.UserEntity;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import com.bazalytskyi.coursework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    TokenAuthenticationService tokenAuthenticationService;


    @Autowired
    UserService userService;


    @PutMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<Void> registerNewUserAccount(@RequestBody UserDto accountDto) {
        UserEntity flag = userService.registerNewUserAccount(accountDto);
        if (flag == null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


    @PostMapping(value = "/signin", produces = "application/json")
    public HttpEntity<Map> login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        String encodePass = new String(Base64.getDecoder().decode(password));
        UserEntity userEntity = userService.getUserByLoginAndPassword(  username, encodePass);
        Map<String, Object> result = new HashMap<String, Object>();
        if (userEntity == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } else {
            CustomUserDetails user = new CustomUserDetails(userEntity);
            String token = tokenAuthenticationService.addAuthentication(response, user);
            result.put("token", token);
            return new HttpEntity(result);
        }
    }

    @GetMapping(value = "/home", produces = "application/json")
    public HttpEntity<Map> home(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Map<String, Boolean> authorizations = new HashMap();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                authorizations.put(grantedAuthority.getAuthority(), Boolean.TRUE);
            }
            result.put("permissions", authorizations);
        }
        try {
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            result.put("username", user.getUsername());
            return new HttpEntity(result);
        } catch (ClassCastException ex) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}
