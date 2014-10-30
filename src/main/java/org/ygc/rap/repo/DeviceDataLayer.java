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

            if(device.getLow()!=null){
                oldDevice.setLow(device.getLow());
            }

            if(device.getHigh()!=null){
                oldDevice.setHigh(device.getHigh());
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
