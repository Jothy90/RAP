package org.ygc.rap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ygc.rap.object.Device;
import org.ygc.rap.repo.DeviceDataLayer;
import org.ygc.rap.sms.SmsRequestProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 10/25/14.
 */
@Controller
@RequestMapping("/")
//Maps the request after host:port/rap/
public class TemperatureController {

    @RequestMapping(value = "/temperatureHome", method = RequestMethod.GET)
    public String goToTemperatureHome(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        Device device= DeviceDataLayer.getDeviceById(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("device",device);
        return "temperaturePage";

    }

    @RequestMapping(value = "/requestForData", method = RequestMethod.GET)
    @ResponseBody
    public String changeStatus(HttpServletRequest request) {
        String s1 = request.getParameter("id");
        Device device = DeviceDataLayer.getDeviceById(Integer.parseInt(s1));
        /*SmsRequestProcessor.sendWebDeviceCommand(device.getMask(), "on");*/
        Integer humidity=device.getHumidity();
        Integer temperature=device.getTemperature();
        device.setHumidity(0);
        device.setTemperature(0);
        DeviceDataLayer.update(device);

        Device deviceNew;
        int count=0;
        do{
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            deviceNew=DeviceDataLayer.getDeviceById(device.getId());
            count++;
        }while(deviceNew.getTemperature()<=0&&count<10);


        /*try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }*/
        if(count==10){
            deviceNew.setHumidity(humidity);
            deviceNew.setTemperature(temperature);
            DeviceDataLayer.update(deviceNew);
            return "ERROR 00 00";
        }else{
            return "WORK "+deviceNew.getTemperature()+" "+deviceNew.getHumidity();
        }
    }
}
