package org.crawler.controller;

import org.crawler.model.entity.Page;
import org.crawler.service.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private SitemapService sitemapService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Page> pageList = sitemapService.getSitemapCreatorService();
        model.addAttribute("pageList",pageList);
        return "index";
    }
    
}
