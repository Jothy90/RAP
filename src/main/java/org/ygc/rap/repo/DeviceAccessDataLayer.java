package org.ygc.rap.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.ygc.rap.object.DeviceAccess;

import java.util.List;

/**
 * Created by john on 10/31/14.
 */
public class DeviceAccessDataLayer {
    private static SessionFactory factory;
    static {
        try{
            factory = new AnnotationConfiguration().
                    configure().
                    //addPackage("com.xyz") //add package if used.
                            addAnnotatedClass(DeviceAccess.class).
                    buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static DeviceAccess getDeviceAccessByMask(String mask){
        DeviceAccess deviceAccess=null;
        Session session = factory.openSession();

        try{
            deviceAccess=(DeviceAccess)session.createQuery("FROM DeviceAccess as da where da.mask="+mask).uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return deviceAccess;
    }
    public static List<DeviceAccess> getDeviceAccessListByDeviceId(int id){
        List<DeviceAccess> deviceAccesses=null;
        Session session = factory.openSession();

        try{
            deviceAccesses=(List<DeviceAccess>)session.createQuery("FROM DeviceAccess as da where da.deviceId="+id).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return deviceAccesses;
    }
}
