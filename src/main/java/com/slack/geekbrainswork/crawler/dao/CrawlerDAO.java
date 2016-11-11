package com.slack.geekbrainswork.crawler.dao;

import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 11.11.2016.
 */
public interface CrawlerDAO<T> {
    void add(T object)                                throws HibernateException;
    void add(List<T> objects)                         throws HibernateException;
    void update(T object)                             throws HibernateException;
    void update(List<T> objects)                      throws HibernateException;
    void delete(T object)                             throws HibernateException;
    void delete(List<T> objects)                      throws HibernateException;
    T getById(Serializable id)                        throws HibernateException;
    List<T> get(HibernateSpecification specification) throws HibernateException;
}
