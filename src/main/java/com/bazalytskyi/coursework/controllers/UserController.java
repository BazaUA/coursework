package com.bazalytskyi.coursework.controllers;

import com.bazalytskyi.coursework.entities.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;
import com.bazalytskyi.coursework.services.CustomUserDetailsService;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import com.bazalytskyi.coursework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    UserService userService;


//    @GetMapping(value = "/login", produces = "application/json")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void login(HttpServletResponse response) {
//        CustomUserPrincipal user = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        tokenAuthenticationService.addAuthentication(response, user);
//    }

    @PutMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<Void> registerNewUserAccount(@RequestBody UserDto accountDto){
        UserEntity flag= userService.registerNewUserAccount(accountDto);
        if(flag==null){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
