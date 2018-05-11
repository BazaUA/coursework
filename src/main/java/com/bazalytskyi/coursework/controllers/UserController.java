package com.bazalytskyi.coursework.controllers;

import com.bazalytskyi.coursework.entities.SessionUser;
import com.bazalytskyi.coursework.entities.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;
import com.bazalytskyi.coursework.services.TokenAuthenticationService;
import com.bazalytskyi.coursework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping(value = "/login",produces = "application/json")
    public ModelAndView getLogin() {
        return new ModelAndView("login_form.html");
    }

    @PostMapping(value = "/signin",produces = "application/json")
    public RedirectView login(ViewControllerRegistry registry, HttpServletResponse response, @RequestParam String username, @RequestParam String password){
        UserEntity userEntity = userService.getUser(username,password);
        SessionUser user = new SessionUser(userEntity);
        tokenAuthenticationService.addAuthentication(response,user);
        return new RedirectView("/");
    }
}
