package org.ygc.rap.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.ygc.rap.object.User;





import org.hibernate.cfg.Configuration;

import java.io.Serializable;

/**
 * Created by john on 10/28/14.
 */
public class UserDB {
    private static SessionFactory factory;
    static {
        try{
            factory = new AnnotationConfiguration().
                    configure().
                    //addPackage("com.xyz") //add package if used.
                            addAnnotatedClass(User.class).
                    buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static int getUserIdByMask(String mask){
        int id=-99;
        Session session = factory.openSession();

        try{
            id=(Integer)session.createQuery("SELECT u.id FROM User as u where u.mask="+mask).uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return id;
    }

    public static User getUserByName(String name){
        User user=null;
        Session session = factory.openSession();

        try{
            user=(User)session.createQuery("FROM User as u where u.name='"+name+"'").uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }

    public static User getUserById(int id){
        User user=null;
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            user =(User)session.get(User.class, id);
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return  user;
    }

    public static boolean update(User user){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User oldUser =(User)session.get(User.class,user.getId());

            if(user.getName()!=null){
                oldUser.setName(user.getName());
            }
            if(user.getPassword()!=null){
                oldUser.setPassword(user.getPassword());
            }
            if(user.getLow()!=null){
                oldUser.setLow(user.getLow());
            }

            if(user.getHigh()!=null){
                oldUser.setHigh(user.getHigh());
            }

            session.update(oldUser);
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
