//                                                                             ;
package com.ideas2it.sessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;


public class HibernateSessionFactory {  

    private static SessionFactory sessionFactory = null;
    private static HibernateSessionFactory instance = null;

    private HibernateSessionFactory() {  }
    
    public static HibernateSessionFactory getInstance() {
        if (null == instance) {
            instance = new HibernateSessionFactory();
        }
        return instance;
    }
    
    public static SessionFactory getSessionFactory() {
        try {
            if (null == sessionFactory) {
                try {
					sessionFactory = new Configuration().configure("hibernate/properties/hibernate.cfg.xml").buildSessionFactory();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        } catch(Exception e) {
            System.out.println(e);
        } 
        return sessionFactory;
    }
}