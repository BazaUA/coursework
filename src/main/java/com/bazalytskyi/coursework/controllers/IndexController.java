package com.bazalytskyi.coursework.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ModelAndView("index.html");
    }


}