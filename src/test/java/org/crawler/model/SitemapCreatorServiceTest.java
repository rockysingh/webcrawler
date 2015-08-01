package org.crawler.model;

import org.crawler.CrawlerApplication;
import org.crawler.model.entity.Page;
import org.crawler.service.CrawlerService;
import org.crawler.service.SitemapCreatorService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes=CrawlerApplication.class)
public class SitemapCreatorServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);
    
    @InjectMocks
    private SitemapCreatorService sitemapCreatorService;

    private static CrawlerService crawler;

    @BeforeClass
    public static void setUp(){
        crawler = mock(CrawlerService.class);
    }
    
    @Test
    public void testCreateWithPagesReturnedByCrawler() {
        Page page = new Page("http://test.com","test","");
        Page page2 = new Page("http://test.com/blog-test","test blog","");
        List<Page> mockPages = new ArrayList<>();
        mockPages.add(page);
        mockPages.add(page2);
        when(crawler.getCrawledPages("http://test.com", "test.com")).thenReturn(mockPages);
        Set<Page> pages = sitemapCreatorService.create("http://test.com","test.com");
        assertTrue(pages.size() == 2);

    }
    
    public void testCreateWithNoPagesReturnedFromCrawler() {
        
        List<Page> mockPages = new ArrayList<>();
        Mockito.when(crawler.getCrawledPages("http://test.com", "test.com")).thenReturn(mockPages);
        Set<Page> pages = sitemapCreatorService.create("http://test.com","test.com");
        assertTrue(pages.size() == 1);
        
    }
    
    
}
