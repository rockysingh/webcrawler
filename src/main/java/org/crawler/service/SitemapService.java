package org.crawler.service;

import org.crawler.model.entity.Page;
import org.crawler.model.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SitemapService {
    
    @Value("${crawler.site.url}")
    private String url;

    @Autowired
    private PageRepository pageRepository;
    
    @Autowired
    private SitemapCreatorService sitemapCreatorService;
    
    public List<Page> getSitemapCreatorService(){
        return pageRepository.findByWebsite(url);
    }
    
}
