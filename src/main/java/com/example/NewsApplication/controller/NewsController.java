package com.example.NewsApplication.controller;


import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.entity.News;
import com.example.NewsApplication.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StringConstants.NEWS)
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping(StringConstants.LIST)
    public ResponseEntity<List<News>> listAllNews() {
        List<News> allNews = newsService.ListAllNews();
        return ResponseEntity.ok(allNews);
    }

    @GetMapping(StringConstants.CURRENT_DATE)
    public ResponseEntity<List<News>> listNewsForCurrentDay() {
        List<News> newsForCurrentDay = newsService.listNewsForCurrentDay();
        return ResponseEntity.ok(newsForCurrentDay);
    }

    @GetMapping(StringConstants.SEARCH)
    public ResponseEntity<List<News>> searchNews(@RequestParam("query") String query) {
        List<News> searchResults = newsService.searchNews(query);
        return ResponseEntity.ok(searchResults);
    }

}
