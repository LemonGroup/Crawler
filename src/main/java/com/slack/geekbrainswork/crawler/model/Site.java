package com.slack.geekbrainswork.crawler.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrey on 09.11.2016.
 */
@Entity
@Table(name = "Sites", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Site {
    private Integer   id;
    private String    name;
    private Set<Page> pages = new HashSet<Page>(0);

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

    @OneToMany(mappedBy = "site")
    public void setName(String name) {
        this.name = name;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (getId() != null ? !getId().equals(site.getId()) : site.getId() != null) return false;
        return getName() != null ? getName().equals(site.getName()) : site.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
