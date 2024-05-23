package com.example.NewsApplication.entity;

import com.example.NewsApplication.constants.StringConstants;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = StringConstants.TEXT)
    private String description;
    private String pubDate;
    @Column(columnDefinition = StringConstants.TEXT)
    private String guid;
}
