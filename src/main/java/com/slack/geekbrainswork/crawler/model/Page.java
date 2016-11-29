package com.slack.geekbrainswork.crawler.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrey on 09.11.2016.
 */
@Entity
@Table(name = "pages", uniqueConstraints = {@UniqueConstraint(columnNames = {"url", "siteid"})})
public class Page implements GeekbrainsDBObject {
    private Integer             id;
    private String              url;
    private Site                site;
    private Calendar            foundDateTime;
    private Calendar            lastScanDate;
    private Set<PersonPageRank> pageRanks = new HashSet<PersonPageRank>(0);

    public Page() {
    }

    public Page(String url, Site site, Calendar foundDateTime, Calendar lastScanDate) {
        this.url = url;
        this.site = site;
        this.foundDateTime = foundDateTime;
        this.lastScanDate = lastScanDate;
    }

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

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(targetEntity = Site.class)
    @JoinColumn(name = "siteid", nullable = false)
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Column(name = "founddatetime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getFoundDateTime() {
        return foundDateTime;
    }

    public void setFoundDateTime(Calendar foundDateTime) {
        this.foundDateTime = foundDateTime;
    }

    @Column(name = "lastscandate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Calendar lastScanDate) {
        this.lastScanDate = lastScanDate;
    }

    @OneToMany(mappedBy = "page")
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

        Page page = (Page) o;

        if (getUrl() != null ? !getUrl().equals(page.getUrl()) : page.getUrl() != null) return false;
        return getSite() != null ? getSite().equals(page.getSite()) : page.getSite() == null;

    }

    @Override
    public int hashCode() {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + (getSite() != null ? getSite().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", site=" + site +
                ", foundDateTime=" + foundDateTime +
                ", lastScanDate=" + lastScanDate +
                '}';
    }
}
