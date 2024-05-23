package com.example.NewsApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sessionId;

    String username;
    String token;

    @Column(unique = true)
    Long userId;
}

