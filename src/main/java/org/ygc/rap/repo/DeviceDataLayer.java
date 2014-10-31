package org.ygc.rap.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.ygc.rap.object.Device;
import org.ygc.rap.object.User;


import java.util.List;

/**
 * Created by john on 10/30/14.
 */
public class DeviceDataLayer {

    private static SessionFactory factory;
    static {
        try{
            factory = new AnnotationConfiguration().
                    configure().
                    //addPackage("com.xyz") //add package if used.
                            addAnnotatedClass(Device.class).
                    buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static List<Device> getDevicesByUserId(int userId) {

        Session session = factory.openSession();
        List<Device> deviceList=null;

        try{
            deviceList=(List<Device>)session.createQuery("FROM Device as d where d.userId="+userId).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return deviceList;
    }

    public static boolean update(Device device){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Device oldDevice =(Device)session.get(Device.class,device.getId());

            if(device.getName()!=null){
                oldDevice.setName(device.getName());
            }

            if(device.getLowTemp()!=null){
                oldDevice.setLowTemp(device.getLowTemp());
            }

            if(device.getHighTemp()!=null){
                oldDevice.setHighTemp(device.getHighTemp());
            }

            if(device.getTemperature()!=null){
                oldDevice.setTemperature(device.getTemperature());
            }

            if(device.getLowHum()!=null){
                oldDevice.setLowHum(device.getLowHum());
            }

            if(device.getHighHum()!=null){
                oldDevice.setHighHum(device.getHighHum());
            }
            if(device.getHumidity()!=null){
                oldDevice.setHumidity(device.getHumidity());
            }

            session.update(oldDevice);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return true;
    }

    public static int getDeviceIdByMask(String mask){
        int id=-99;
        Session session = factory.openSession();

        try{
            id=(Integer)session.createQuery("SELECT d.id FROM Device as d where d.mask="+mask).uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return id;
    }

    public static Device getDeviceById(int id){
        Device device=null;
        Session session = factory.openSession();

        try{
            device=(Device)session.get(Device.class,id);
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return device;
    }
    public static Device getDeviceByMask(String mask){
        Device device=null;
        Session session = factory.openSession();

        try{
            device=(Device)session.createQuery("FROM Device as d where d.mask="+mask).uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return device;
    }
    public static boolean addUserId(int deviceId,int userId ){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Device device =(Device)session.get(Device.class,deviceId);
            device.setUserId(userId);
            session.update(device);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return true;
    }


}
