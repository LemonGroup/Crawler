package com.slack.geekbrainswork.crawler;

import com.slack.geekbrainswork.crawler.model.*;
import com.slack.geekbrainswork.crawler.util.DAOFactory;
import com.slack.geekbrainswork.crawler.util.SFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Andrey on 09.11.2016.
 */
public class MainApp {
    public static void main(String[] args) {
        try{
            
        }
        finally {
            // закрываем сессию с БД
            SFactory.get().getCurrentSession().close();
            SFactory.get().close();
        }
    }
}