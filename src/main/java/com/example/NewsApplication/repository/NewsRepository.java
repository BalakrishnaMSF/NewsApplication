package com.example.NewsApplication.repository;

import com.example.NewsApplication.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface NewsRepository extends JpaRepository<News , Long> {
    News findByGuid(String guid);
    List<News> findByPubDateContaining(String date);
    @Query("SELECT n FROM News n WHERE n.title LIKE %:query% OR n.description LIKE %:query%")
    List<News> customSearch(@Param("query") String query);
}


