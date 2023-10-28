package com.uade.ainews.newsGeneration.repository;


import com.uade.ainews.newsGeneration.dto.SummarizedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummarizedNewsRepository extends JpaRepository<SummarizedNews, Long> {
}


