package com.slack.geekbrainswork.crawler.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Andrey on 11.11.2016.
 */
public class SFactory {
    private static SessionFactory sessionFactory = null;

    static {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static SessionFactory get() {
        return sessionFactory;
    }
}
