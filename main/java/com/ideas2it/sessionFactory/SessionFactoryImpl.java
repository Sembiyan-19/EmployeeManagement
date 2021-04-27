//                                                                             ;
package com.ideas2it.sessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;


public class SessionFactoryImpl {  

    private static SessionFactory sessionFactory = null;
    private static SessionFactoryImpl instance = null;

    private SessionFactoryImpl() {  }
    
    public static SessionFactoryImpl getInstance() {
        if (null == instance) {
            instance = new SessionFactoryImpl();
        }
        return instance;
    }
    
    public static SessionFactory getSessionFactory() {
        try {
            if (null == sessionFactory) {
                try {
					sessionFactory = new Configuration().configure().buildSessionFactory();
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