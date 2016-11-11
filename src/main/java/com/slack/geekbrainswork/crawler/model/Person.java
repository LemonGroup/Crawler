package com.slack.geekbrainswork.crawler.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrey on 09.11.2016.
 */
@Entity
@Table(name = "Persons", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Person implements GeekbrainsDBObject {
    private Integer             id;
    private String              name;
    private Set<Keyword>        keywords  = new HashSet<Keyword>(0);
    private Set<PersonPageRank> pageRanks = new HashSet<PersonPageRank>(0);

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "person")
    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    @OneToMany(mappedBy = "person")
    public Set<PersonPageRank> getPageRanks() {
        return pageRanks;
    }

    public void setPageRanks(Set<PersonPageRank> pageRanks) {
        this.pageRanks = pageRanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) return false;
        return getName() != null ? getName().equals(person.getName()) : person.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
