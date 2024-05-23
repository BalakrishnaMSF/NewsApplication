package com.example.NewsApplication.testing;

import com.example.NewsApplication.entity.News;
import com.example.NewsApplication.repository.NewsRepository;
import com.example.NewsApplication.service.NewsService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;


    @Test
    public void testListAllNews() {

        News news1 = new News();
        news1.setId(1L);
        news1.setTitle("News Title 1");
        news1.setDescription("News Description 1");

        News news2 = new News();
        news2.setId(2L);
        news2.setTitle("News Title 2");
        news2.setDescription("News Description 2");

        List<News> newsList = Arrays.asList(news1, news2);

        when(newsRepository.findAll()).thenReturn(newsList);

        List<News> result = newsService.ListAllNews();


        assertEquals(2, result.size()); // Ensure that two news articles are returned
        assertEquals("News Title 1", result.get(0).getTitle()); // Check the title of the first news article
        assertEquals("News Description 2", result.get(1).getDescription()); // Check the description of the second news article
        log.info("List all news mock test successful");
    }
    @Test
    public void testListNewsForCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d");
        String formattedDate = currentDate.format(formatter);

        News news1 = new News();
        news1.setPubDate(String.valueOf(LocalDateTime.now()));
        news1.setTitle("News 1");
        newsRepository.save(news1);

        News news2 = new News();
        news2.setPubDate(String.valueOf(LocalDateTime.now()));
        news2.setTitle("News 2");
        newsRepository.save(news2);

        List<News> mockNewsList = new ArrayList<>();
        mockNewsList.add(news1);
        mockNewsList.add(news2);
        when(newsRepository.findByPubDateContaining(formattedDate)).thenReturn(mockNewsList);

        List<News> newsList = newsService.listNewsForCurrentDay();

        assertEquals(2, newsList.size());
        assertEquals("News 1", newsList.get(0).getTitle());
        assertEquals("News 2", newsList.get(1).getTitle());
        log.info("Current date news mock test successful");
    }


    @Test
    public void testSearchNews() {
        String query = "example";

        News news1 = new News();
        news1.setId(1L);
        news1.setTitle("News Title 1");
        news1.setDescription("News Description 1");

        News news2 = new News();
        news2.setId(2L);
        news2.setTitle("News Title 2");
        news2.setDescription("News Description 2");

        List<News> newsList = Arrays.asList(news1, news2);

        when(newsRepository.customSearch(query)).thenReturn(newsList);

        List<News> result = newsService.searchNews(query);

        assertEquals(2, result.size()); // Ensure that two news articles are returned
        assertEquals("News Title 1", result.get(0).getTitle()); // Check the title of the first news article
        assertEquals("News Description 2", result.get(1).getDescription()); // Check the description of the second news article
        log.info("Search news mock test successful");
    }

    @Test
    public void testDisplayNewNews() {

        News newNews = new News();
        newNews.setGuid("123456");

        when(newsRepository.findByGuid("123456")).thenReturn(null); // News does not exist

        newsService.displayNews(newNews);

        verify(newsRepository, times(1)).save(newNews);
        log.info("Display news mock test successful");
    }

}
