package com.slack.geekbrainswork.crawler.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Andrey on 11.11.2016.
 */
public class SFactory {
    private static final Logger logger = LoggerFactory.getLogger(SFactory.class);
    private static SessionFactory sessionFactory = null;

    static {
        try{
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch(HibernateException e) {
            logger.error("Ошибка инициализации Hibernate", e);
            throw e;
        }
    }

    public static SessionFactory get() {
        return sessionFactory;
    }
}
