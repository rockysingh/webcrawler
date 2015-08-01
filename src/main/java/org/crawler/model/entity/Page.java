package org.crawler.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Page {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String url;
    private String title;
    private String website;

    protected Page() {}

    public Page(String url, String title, String website) {
        this.url = url;
        this.title = title;
        this.website = website;
    }
    
    public String getUrl(){
        return url;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getWebsite(){
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
        
    }

    @Override
    public String toString() {
        return String.format(
                "Page[id=%d, url='%s', title='%s', website='%s']",
                id, url, title, website);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Page))
            return false;
        Page p = (Page) o;
        return p.url.equals(url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

}