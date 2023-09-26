package com.uade.ainews;

import com.uade.ainews.newsGeneration.dto.News;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestUtils {

    public static News createNews(){
        List<String> keywords1 = new LinkedList<String>(Arrays.asList("A", "B", "C", "D", "E"));
        LocalDateTime date= LocalDateTime.now();
        return News.builder().url("aURL").keywords(keywords1).section("aSection").title("aTitle").article("anArticle").releaseDate(date).build();
    }
}
