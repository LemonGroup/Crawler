package com.slack.geekbrainswork.crawler.dao;

import org.hibernate.criterion.Criterion;

/**
 * Created by Andrey on 11.11.2016.
 */
public interface HibernateSpecification {
    Criterion getCriteria();
}
