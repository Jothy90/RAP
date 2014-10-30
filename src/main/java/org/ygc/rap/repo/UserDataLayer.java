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
public class UserDataLayer {
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
    public static int add(User user){

        Session session = factory.openSession();
        Transaction tx = null;
        int userId=-99;
        try{
            tx = session.beginTransaction();
            userId =(Integer)session.save(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return userId;
    }

    public static boolean addUserId(User user){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User oldUser =(User)session.get(User.class,user.getName());

            if(user.getPassword()!=null){
                oldUser.setPassword(user.getPassword());
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
