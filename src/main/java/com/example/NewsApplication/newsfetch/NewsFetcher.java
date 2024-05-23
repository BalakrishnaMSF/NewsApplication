package com.example.NewsApplication.newsfetch;


import com.example.NewsApplication.entity.News;
import com.example.NewsApplication.service.NewsService;
import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@EnableScheduling
@Slf4j
public class NewsFetcher {
    @Autowired
    NewsService newsService;
    @Value("${news.fetch.url}") // Read the URL from properties
    private String newsFetchUrl;
    @Scheduled(fixedRate = 60000) // Runs the annotated method every 1 min.
    public void fetchAndSaveNews() {

        try {
            URL url = new URL(newsFetchUrl);
            FeedFetcher feedFetcher = new HttpURLFeedFetcher();
            // Retrieve the news feed
            SyndFeed feed = feedFetcher.retrieveFeed(url);

            for (SyndEntry entry : feed.getEntries()) {
                // Create a News object and populate it with feed data
                News news = new News();
                news.setTitle(entry.getTitle());
                news.setDescription(entry.getDescription().getValue());
                news.setPubDate(entry.getPublishedDate().toString());
                news.setGuid(entry.getUri());
                newsService.displayNews(news);
            }
        } catch (Exception e) {
            log.error("Error while fetching and saving news", e);
        }
    }
}

