package com.example.NewsApplication.service;

import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.entity.News;
import com.example.NewsApplication.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional
@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;
    public void displayNews(News news){
        News existingNews = newsRepository.findByGuid(news.getGuid());
        if(existingNews==null){
            newsRepository.save(news);
        }
    }

    public List<News> ListAllNews(){
        return newsRepository.findAll();
    }

    public List<News> listNewsForCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConstants.PATTERN);
        String formattedDate = currentDate.format(formatter);
        return newsRepository.findByPubDateContaining(formattedDate);
    }

    public List<News> searchNews(String query) {
        return newsRepository.customSearch(query);
    }
}
