package com.uade.ainews.newsGeneration.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sumnews")
@Table(name = "SUMNEWS")
public class SummarizedNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String section;
    @Column
    private String title;
    @Column
    private String rawArticle;
    @Column
    private String summary;
    @Column
    private LocalDateTime releaseDate;
}
