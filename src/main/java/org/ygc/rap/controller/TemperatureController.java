package org.ygc.rap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 10/25/14.
 */
@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class TemperatureController {

    @RequestMapping(value = "/temperatureHome", method = RequestMethod.GET)
    public String temperatureHome(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "temHumHome";
    }

    }
