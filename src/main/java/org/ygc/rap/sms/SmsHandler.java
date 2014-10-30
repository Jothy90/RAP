package org.ygc.rap.sms;

import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;
import org.ygc.rap.util.Property;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by john on 10/30/14.
 */
public class SmsHandler implements MoSmsListener {
    private final static Logger LOGGER = Logger.getLogger(SmsHandler.class.getName());
    SmsRequestSender smsMtSender;


    @Override
    public void init() {
        LOGGER.info("Initiating SMS Handler");
        try {
            smsMtSender = new SmsRequestSender(new URL(Property.getValue("sdp.sms.url")));
        } catch (MalformedURLException e) {
            LOGGER.info("MalformedURLException on initializing SmsHandler");
        }
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        LOGGER.info("DIA Sms Received : " + moSmsReq);
        //TODO:init not working in one shot
        init();
        try {
            String message = RapSmsUtil.removeRMP(moSmsReq.getMessage());
            MtSmsReq deviceMtSms, userMtSms;
            MtSmsResp deviceMtResp = null, userMtResp = null;

            if (message.startsWith("dd")) {
                LOGGER.info("Identified message from device");

                //message received from device
                //TODO:Device msg handle codes

            } else {
                LOGGER.info("Identified message from User");

                //message received from User
                //TODO:User msg handle codes

            }

        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred", e);
        }
        smsMtSender = null;
//        smsRequestProcessor = null;
    }

}

