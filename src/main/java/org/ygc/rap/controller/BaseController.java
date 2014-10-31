package org.ygc.rap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        return "login";

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String welcomeName(ModelMap model) {

        model.addAttribute("message", "RAP Web Project + Spring 3 MVC - you are now at page: test");
        return "index";

    }
}