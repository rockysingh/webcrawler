package org.crawler.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.crawler.model.entity.Page;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService
{

    private static final String USERAGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B329 Safari/8536.25";
    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    public List<Page> getCrawledPages(String url, String filter) {
       List<Page> pages = new LinkedList<Page>();
        try {
            Connection connection = Jsoup.connect(url).userAgent(USERAGENT);
            Document document = connection.get();
            if (connection.response().statusCode() != 200 || !connection.response().contentType().contains("text/html")) {
                return pages;
            }
            return this.crawl(document,filter);
        } catch (IOException e) {
            return pages;
        }
    }
    
    public List<Page> crawl(Document document, String filter) {

        List<Page> linkList = new LinkedList<Page>();
        Elements links = document.select("a[href]");

        for (Element link : links) {
            String href = link.absUrl("href");
            if (isFilter(href,filter)) {
                Page page = new Page(link.absUrl("href"),getPageTitle(document),"");
                linkList.add(page);
            }
        }
        
        return linkList;
    }


    private boolean isFilter(String url, String filter){
        Boolean bool = url.contains(filter);
        return (!bool) ? bool : !url.contains("#");
    }
    
    private String getPageTitle(Document document) {
        return document.title();
    }
    
}