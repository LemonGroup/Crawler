package com.slack.geekbrainswork.crawler.dao.impl;

import com.slack.geekbrainswork.crawler.dao.CrawlerDAO;
import com.slack.geekbrainswork.crawler.dao.HibernateSpecification;
import com.slack.geekbrainswork.crawler.util.SFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrey on 11.11.2016.
 */
public class CrawlerDAOImpl<T> implements CrawlerDAO<T> {
    private final Class<T> clazz;

    public CrawlerDAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void add(T object) throws HibernateException {
        add(Arrays.asList(object));
    }

    public void add(List<T> objects) throws HibernateException {
        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int i = 0;
        int batchSize = SFactory.get().getSessionFactoryOptions().getJdbcBatchSize();
        for (T object : objects) {
            i++;
            session.save(object);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
    }

    public void update(T object) throws HibernateException {
        update(Arrays.asList(object));
    }

    public void update(List<T> objects) throws HibernateException {
        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int i = 0;
        int batchSize = SFactory.get().getSessionFactoryOptions().getJdbcBatchSize();
        for (T object : objects) {
            i++;
            session.update(object);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
    }

    @Override
    public void addOrUpdate(T object) throws HibernateException {
        addOrUpdate(Arrays.asList(object));
    }

    @Override
    public void addOrUpdate(List<T> objects) throws HibernateException {
        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int i = 0;
        int batchSize = SFactory.get().getSessionFactoryOptions().getJdbcBatchSize();
        for (T object : objects) {
            i++;
            session.saveOrUpdate(object);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
    }

    public void delete(T object) throws HibernateException {
        delete(Arrays.asList(object));
    }

    public void delete(List<T> objects) throws HibernateException {
        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int i = 0;
        int batchSize = SFactory.get().getSessionFactoryOptions().getJdbcBatchSize();
        for (T object : objects) {
            i++;
            session.delete(object);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
    }

    public T getById(Serializable id) throws HibernateException {
        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T object = session.get(clazz, id);
        transaction.commit();
        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> get(HibernateSpecification<T> specification) throws HibernateException {
        List<T> objects = new ArrayList<T>();

        Session session = SFactory.get().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criterion selectCriteria = specification.getCriteria();
        if (selectCriteria != null) {
            objects.addAll(session.createCriteria(clazz).add(specification.getCriteria()).list());
        }
        else {
            objects.addAll(session.createCriteria(clazz).list());
        }
        transaction.commit();
        return objects;
    }
}
