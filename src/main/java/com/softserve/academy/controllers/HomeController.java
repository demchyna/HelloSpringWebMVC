package com.softserve.academy.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {

    private int counter = 0;

    @RequestMapping(path = { "/", "/home" }, method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole()")
    public String homePage() {
        return "home";
    }

    @ModelAttribute("counter")
    public int getCounter() {
        return ++counter;
    }
}
