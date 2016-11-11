package com.slack.geekbrainswork.crawler.model;

import javax.persistence.*;

/**
 * Created by Andrey on 09.11.2016.
 */
@Entity
@Table(name = "PersonPageRank")
public class PersonPageRank {
    private Integer rank;
    private Person  person;
    private Page    page;

    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

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

        PersonPageRank that = (PersonPageRank) o;

        if (getRank() != null ? !getRank().equals(that.getRank()) : that.getRank() != null) return false;
        if (getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null) return false;
        return getPage() != null ? getPage().equals(that.getPage()) : that.getPage() == null;

    }

    @Override
    public int hashCode() {
        int result = getRank() != null ? getRank().hashCode() : 0;
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonPageRank{" +
                "rank=" + rank +
                ", person=" + person +
                ", page=" + page +
                '}';
    }
}
