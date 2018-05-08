package com.bazalytskyi.coursework.controllers;

import com.bazalytskyi.coursework.entities.SessionUser;
import com.bazalytskyi.coursework.entities.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;
import com.bazalytskyi.coursework.services.CustomUserDetailsService;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import com.bazalytskyi.coursework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void login(HttpServletResponse response) {
        SessionUser user = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUser().toString());
        tokenAuthenticationService.addAuthentication(response, user);
    }

    @PutMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<Void> registerNewUserAccount(@RequestBody UserDto accountDto){
        UserEntity flag= userService.registerNewUserAccount(accountDto);
        if(flag==null){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
