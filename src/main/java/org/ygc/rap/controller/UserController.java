package org.ygc.rap.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ygc.rap.object.Device;
import org.ygc.rap.object.User;
import org.ygc.rap.repo.DeviceDataLayer;
import org.ygc.rap.repo.UserDataLayer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by john on 10/25/14.
 */
@Controller
@RequestMapping("/")
//Maps the request after host:port/rap/
public class UserController {
    HttpSession session;

    @RequestMapping(value = "/registerMe", method = RequestMethod.POST)
    public void register(ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id= DeviceDataLayer.getDeviceIdByMask(request.getParameter("pin"));

        if(id>0){
            User user=new User();
            user.setName(request.getParameter("userName"));
            user.setPassword(request.getParameter("passwords"));
            DeviceDataLayer.addUserId(id,UserDataLayer.add(user));


            request.setAttribute("userName",user.getName());
            request.setAttribute("password",user.getPassword());
            response.sendRedirect("/rap-web/j_spring_security_check");
        }else{
            response.sendRedirect("/rap-web/j_spring_security_logout?mask");
        }

        //DataLayer.addNewGarden(gn);




    /*
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        try{
            dispatcher.forward(request, response);

        }catch(Exception e){

        }*/

    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public String toUserHome(ModelMap model, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        User user=UserDataLayer.getUserByName(name);
        session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());

        List<Device> devices = DeviceDataLayer.getDevicesByUserId(user.getId());
        model.addAttribute("devices", devices);
        return "userHome";
    }
}
