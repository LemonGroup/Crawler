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
@Table(name = "Pages", uniqueConstraints = {@UniqueConstraint(columnNames = {"url", "site"})})
public class Page {
    private Integer             id;
    private String              url;
    private Site                site;
    private Calendar            foundDateTime;
    private Calendar            lastScanDate;
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

    @Column(name = "founddatetime")
    @Temporal(TemporalType.DATE)
    public Calendar getFoundDateTime() {
        return foundDateTime;
    }

    public void setFoundDateTime(Calendar foundDateTime) {
        this.foundDateTime = foundDateTime;
    }

    @Column(name = "lastscandate")
    @Temporal(TemporalType.DATE)
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

        if (getId() != null ? !getId().equals(page.getId()) : page.getId() != null) return false;
        if (getUrl() != null ? !getUrl().equals(page.getUrl()) : page.getUrl() != null) return false;
        if (getSite() != null ? !getSite().equals(page.getSite()) : page.getSite() != null) return false;
        if (getFoundDateTime() != null ? !getFoundDateTime().equals(page.getFoundDateTime()) : page.getFoundDateTime() != null)
            return false;
        return getLastScanDate() != null ? getLastScanDate().equals(page.getLastScanDate()) : page.getLastScanDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getSite() != null ? getSite().hashCode() : 0);
        result = 31 * result + (getFoundDateTime() != null ? getFoundDateTime().hashCode() : 0);
        result = 31 * result + (getLastScanDate() != null ? getLastScanDate().hashCode() : 0);
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
