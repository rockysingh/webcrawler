package org.crawler.service;

import org.crawler.model.entity.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class SitemapCreatorService {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    private List<Page> visited = new LinkedList<Page>();
    private Set<Page> pages = new CopyOnWriteArraySet<Page>();
    
    @Autowired
    private CrawlerService crawlerService;
    
    public Set<Page> create(String url, String filter) {

        if (visited.isEmpty()) {
            Page page = new Page(url,"Home",url);
            visited.add(page);
            pages.add(page);
            pages.addAll(crawlerService.getCrawledPages(url, filter));
        }
        
        if (pages.size() > 0 ) {

            for (Page p : pages) {
                logger.error("considering crawling ... {}",p.getUrl());
                if (!visited.contains(p)) {
                    logger.error("crawling... {}",p.getUrl());
                    visited.add(p);
                    pages.addAll(crawlerService.getCrawledPages(p.getUrl(), filter));
                }
            }
            
            logger.error("Pages crawled size = {}",pages.size());
            
            if (visited.size() < pages.size()) {
                create(url, filter);
            }
        }
        
        logger.error("urls found " + pages.size());
        
        return pages;

    }

}
