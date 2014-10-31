package org.ygc.rap.sms;

import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;
import org.ygc.rap.object.Device;
import org.ygc.rap.object.DeviceAccess;
import org.ygc.rap.repo.DeviceAccessDataLayer;
import org.ygc.rap.repo.DeviceDataLayer;
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
        LOGGER.info("RMP Sms Received : " + moSmsReq);
        //TODO:init not working in one shot
        init();
        try {
            String message = RapSmsUtil.removeRMP(moSmsReq.getMessage());
            MtSmsReq deviceMtSms, userMtSms;
            MtSmsResp deviceMtResp = null, userMtResp = null;

            if (message.startsWith("dd")) {
                LOGGER.info("Identified message from device");

                //message received from device
                Device device = DeviceDataLayer.getDeviceByMask(moSmsReq.getSourceAddress());
                if (device.getId() > 0) {
                    LOGGER.info("Identified Device : " + device.getName());

                    String deviceName = device.getName();
                    List<DeviceAccess> deviceAccessList = DeviceAccessDataLayer.getDeviceAccessListByDeviceId(device.getId());
                    LOGGER.info("Found Device Users : " + deviceAccessList);

                    List<String> addressList = new ArrayList<String>();
                    for (DeviceAccess deviceAccess : deviceAccessList) {
                        addressList.add(deviceAccess.getUserMask());
                    }

                    userMtSms = RapSmsUtil.createUserReplyMtSms(moSmsReq);
                    userMtSms.setDestinationAddresses(addressList);

                    if (message.startsWith("dd on")) {      //TODO:reply for request sensor data
                        LOGGER.info("Processing device dd on reply");
                        message = message.substring(6);
                        device.setSensorData(message);
                        DeviceDataLayer.update(device);
                        //return; //TODO:undo comment

                        String userReply = deviceName + " Humidity " + device.getHumidity() + "%, Temperature " + device.getTemperature() + " C.";
                        LOGGER.info("Created user reply message: " + userReply);
                        userMtSms.setMessage(userReply);
                    } else if (message.startsWith("dd off")) {      //TODO:msg for over limit of sensor data
                        LOGGER.info("Processing device dd on reply");
                        message = message.substring(6);
                        device.setSensorData(message);
                        DeviceDataLayer.update(device);

                        String userReply = deviceName + " Humidity " + device.getHumidity() + "%, Temperature " + device.getTemperature() + " C.";
                        LOGGER.info("Created user reply message: " + userReply);
                        userMtSms.setMessage(userReply);
                    } else {
                        //return;//TODO:undo comment
                        userMtSms.setMessage("Some irrelevant data("+message +")received " + deviceName);
                    }
                    LOGGER.info("Sending User alert message: " + userMtSms);
                    RapSmsUtil.sendCommand(smsMtSender, userMtSms);
                }
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

