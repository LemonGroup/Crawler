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
}
