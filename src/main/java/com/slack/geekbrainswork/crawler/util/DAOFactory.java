package com.slack.geekbrainswork.crawler.util;

import com.slack.geekbrainswork.crawler.dao.CrawlerDAO;
import com.slack.geekbrainswork.crawler.dao.impl.CrawlerDAOImpl;
import com.slack.geekbrainswork.crawler.model.GeekbrainsDBObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Andrey on 11.11.2016.
 */
public class DAOFactory {
    private static final ConcurrentHashMap<Class<? extends GeekbrainsDBObject>, CrawlerDAO<? extends GeekbrainsDBObject>> classToDao
            = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GeekbrainsDBObject> CrawlerDAO<T> getDAO (Class<T> daoClass) {
        CrawlerDAO<T> dao = (CrawlerDAO<T>) classToDao.putIfAbsent(daoClass, new CrawlerDAOImpl<T>(daoClass));
        if (dao == null) {
            dao = (CrawlerDAO<T>) classToDao.get(daoClass);
        }
        return dao;
    }
}
