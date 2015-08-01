package org.crawler.job;

import org.crawler.model.entity.Page;
import org.crawler.model.repository.PageRepository;
import org.crawler.service.SitemapCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CrawlerJob
{

    @Value("${crawler.site.url}")
    private String url;

    @Value("${crawler.site.domain.filter}")
    private String filter;

    private static final Logger logger = LoggerFactory.getLogger(CrawlerJob.class);

    @Autowired
    private SitemapCreatorService sitemapCreatorService;
    
    @Autowired
    private PageRepository pageRepository;
    
    //run every hour
    @Scheduled(fixedDelay = 3600000)
    public void getCrawledPages() {
         Set<Page> pages = sitemapCreatorService.create(url,filter);
         
         //clean up
         pageRepository.deleteAll();
         
         for(Page page : pages) {
             page.setWebsite(url);
             pageRepository.save(page);
         }
        
     }
    
    
}
