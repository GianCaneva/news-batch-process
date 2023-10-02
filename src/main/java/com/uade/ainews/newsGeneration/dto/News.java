package com.uade.ainews.newsGeneration.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "news")
@Table(name = "NEWS")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String url;
    @Transient
    private List<String> keywords;
    @Column
    private String section;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String article;
    @Column
    private String merged;
    @Column
    private LocalDateTime releaseDate;

    public void setArticle(StringBuilder article) {
        this.article = String.valueOf(article);
    }
}
