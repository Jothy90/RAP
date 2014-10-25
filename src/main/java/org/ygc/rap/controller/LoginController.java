package org.ygc.rap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ygc.rap.repo.object.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 10/25/14.
 */
@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class LoginController {
    @RequestMapping(value = "/registerMe", method = RequestMethod.POST)
    public void doLogin(ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user=new User();
        user.setName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        //DataLayer.addNewGarden(gn);


        request.setAttribute("userName",user.getName());
        request.setAttribute("password",user.getPassword());
        response.sendRedirect("/rap/j_spring_security_check");

    /*
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        try{
            dispatcher.forward(request, response);

        }catch(Exception e){

        }*/

    }

}
