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
                //TODO:Device msg handle codes



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
                            DataLayer.updateDevice(device);
                            DataLayer.updateTempHum(device);
                            String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                            String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));
                            String userReply = deviceName + " switched on, Moisture level " + moisture + "%, temperature " + temperature + " C.";
                            LOGGER.info("Created user reply message: " + userReply);
                            //TODO: send sensor data to DIA intellegent and get message
                            userMtSms.setMessage(userReply);
                        } else if (message.startsWith("dd off")) {
                            message = message.substring(7);
                            device.setCurrentStatus(0);
                            device.setSensorData(message);
                            DataLayer.updateDevice(device);
                            String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                            String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));
                            //TODO: send sensor data to DIA intellegent and get message
                            String userReply1 =deviceName + " switched off, Moisture level " + moisture + "%, temperature " + temperature + " C.";
                            if(Integer.parseInt(moisture)<30){
                                userReply1=userReply1+" Please check your water supply!";
                            }
                            userMtSms.setMessage(userReply1);
                        } else if (message.startsWith("dd shd on")) {
                            message = message.substring(10);
                            device.setCurrentStatus(1);
                            device.setSensorData(message);
                            DataLayer.updateDevice(device);
                            String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                            String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));
                            //TODO: send sensor data to DIA intellegent and get message
                            userMtSms.setMessage(deviceName + " switched on by your schedule, Moisture level " + moisture + "%, temperature " + temperature + " C.");
                        } else if (message.startsWith("dd shd off")) {
                            message = message.substring(11);
                            device.setCurrentStatus(0);
                            device.setSensorData(message);
                            DataLayer.updateDevice(device);
                            String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                            String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));
                            //TODO: send sensor data to DIA intellegent and get message
                            String userReply2= deviceName + " switched off by your schedule, Moisture level " + moisture + "%, temperature " + temperature + " C.";
                            if(Integer.parseInt(moisture)<30){
                                userReply2=userReply2+" Please check your water supply!";
                            }
                            userMtSms.setMessage(userReply2);

                        } else if (message.startsWith("dd shd t")) {
                            userMtSms.setMessage("Schedule successfully loaded to " + deviceName);
                        } else if (message.startsWith("dd rst")) {
                            deviceMtSms = DiaSmsUtil.createDeviceReplyCommandMtSms(moSmsReq);
                            if (device.getOperationType() == OperationType.MANUAL) {
                                deviceMtSms.setMessage("shd stop");
                                DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                                userMtSms.setMessage("There was a power down and up now, restarting "+deviceName+" with manual mode");
                                DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                                return;
                            } else {
                                String schedule = device.getSchedule();
                                schedule = "shd " + String.valueOf(DiaCommonUtil.getCurrentDay()) + ";" + schedule;
                                deviceMtSms.setMessage(schedule);
                                DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                                userMtSms.setMessage("There was a power down and up now, restarting "+deviceName+" with scheduled mode. Calling schedule back");
                                DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                                return;
                            }
                        }
                        LOGGER.info("Sending User alert message: " + userMtSms);
                        DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
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

