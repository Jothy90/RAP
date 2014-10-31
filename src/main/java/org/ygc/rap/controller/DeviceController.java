package org.ygc.rap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 10/30/14.
 */
@Controller
@RequestMapping("/")
//Maps the request after host:port/rap/
public class DeviceController {


    @RequestMapping(value = "/deviceHome", method = RequestMethod.GET)
    public void deviceHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int type=Integer.parseInt(request.getParameter("type"));

        switch(type){
            case 1:
                response.sendRedirect("/rap-web/temperatureHome?id="+request.getParameter("id"));
                break;
        }
    }
}
