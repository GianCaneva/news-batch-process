package com.uade.ainews.newsGeneration.repository;


import com.uade.ainews.newsGeneration.dto.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository <News, Long>{
    Optional<News> findOneByUrl(String url);
}


