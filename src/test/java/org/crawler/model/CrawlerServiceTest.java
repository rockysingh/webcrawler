package org.crawler.model;


import org.crawler.CrawlerApplication;
import org.crawler.model.entity.Page;
import org.crawler.service.CrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes=CrawlerApplication.class)
public class CrawlerServiceTest {

    @InjectMocks
    private CrawlerService crawlerService;
    
    private static final String USERAGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B329 Safari/8536.25";

    private static Jsoup jsoup;

    private static Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(CrawlerServiceTest.class);

    @BeforeClass
    public static void setUp(){
        jsoup = mock(Jsoup.class);}


    @Test
    public void testCrawlerSuccess() throws IOException{
        
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body><a href='http://test.com'>home</a><a href='http://test.com/blog'>blog</a></html>";
        Document document = Jsoup.parse(html);
        List<Page> pages = crawlerService.crawl(document,"test.com");

        assertTrue(pages.size() == 2);
    }

    @Test
    public void testCrawlerFailure() throws IOException{

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document document = Jsoup.parse(html);
        List<Page> pages = crawlerService.crawl(document,"test.com");

        assertTrue(pages.size() == 0);
    }

    @Test
    public void testFilterStrippingHashes() throws IOException{

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body><a href='http://test.com'>home</a><a href='http://test.com/#blog'>blog</a></html>";
        Document document = Jsoup.parse(html);
        List<Page> pages = crawlerService.crawl(document,"test.com");

        assertTrue(pages.size() == 1);
    }

    @Test
    public void tesFilterShouldReturnNoResults() throws IOException{

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body><a href='http://test.com'>home</a><a href='http://test.com/blog'>blog</a></html>";
        Document document = Jsoup.parse(html);
        List<Page> pages = crawlerService.crawl(document,"test-filter.com");

        assertTrue(pages.size() == 0);
    }

    @Test
    public void testingFilterForNonUrl() throws IOException{

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body><a href='http://test.com'>home</a><a href='http://twitter.com/test'>twitter</a></html>";
        Document document = Jsoup.parse(html);
        List<Page> pages = crawlerService.crawl(document,"test.com");

        assertTrue(pages.size() == 1);
    }

}
