package com.slack.geekbrainswork.crawler.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Andrey on 11.11.2016.
 */
public class PersonPageRankPK implements Serializable {
    private static final long serialVersionUID = 7951247080199704462L;
    private Person  person;
    private Page    page;

    @Id
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "personid", nullable = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Id
    @ManyToOne(targetEntity = Page.class)
    @JoinColumn(name = "pageid", nullable = false)
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonPageRankPK that = (PersonPageRankPK) o;

        if (getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null) return false;
        return getPage() != null ? getPage().equals(that.getPage()) : that.getPage() == null;

    }

    @Override
    public int hashCode() {
        int result = getPerson() != null ? getPerson().hashCode() : 0;
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        return result;
    }
}
