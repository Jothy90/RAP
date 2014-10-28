package org.ygc.rap.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.ygc.rap.object.User;

/**
 * Created by john on 10/28/14.
 */
public class UserDB {
    private static SessionFactory factory;

    public static int getUserIdByMask(String mask){
        int id=-99;

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

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            id =Integer.parseInt(session.createQuery("SELECT u.id FROM user as u where u.mask="+mask).getQueryString());
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return id;

    }
    public static User getUserById(int id){
        User user=null;

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
            User oldUser =
                    (User)session.get(User.class, user.getId());
            oldUser.setName(user.getName());
            oldUser.setPassword(user.getPassword());
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
