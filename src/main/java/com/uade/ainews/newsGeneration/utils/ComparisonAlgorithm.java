package com.uade.ainews.newsGeneration.utils;


import com.uade.ainews.newsGeneration.dto.News;

import java.util.LinkedList;
import java.util.List;

public class ComparisonAlgorithm {

    public static final int AMOUNT_OF_SAME_WORDS_TO_DETECT_SIMILARITY = 2;

    // Algorithm that takes all news and checks if there are different articles that talk about the same news.
    public static List<List<News>> identifySameNews(List<News> allNews) {
        List<List<News>> matchNews = new LinkedList<>();

        int matches = 0;

        for (int i = 0; i < allNews.size(); i++) {
            List<News> siblingNews = new LinkedList<>();
            News currentNews = allNews.get(i);
            List<String> keywordsFirst1 = currentNews.getKeywords();
            for (int j = 0; j < allNews.size(); j++) {
                if (allNews.get(i).equals(allNews.get(j))) {
                    continue;
                }
                News comparisonNews = allNews.get(j);
                List<String> keywordsSecond2 = comparisonNews.getKeywords();

                for (int x = 0; x < keywordsFirst1.size(); x++) {
                    String keywords1 = keywordsFirst1.get(x);
                    if (keywordsSecond2.contains(keywords1)) {
                        matches++;
                        if (matches == AMOUNT_OF_SAME_WORDS_TO_DETECT_SIMILARITY) {
                            comparisonNews.setMerged("Y");
                            siblingNews.add(comparisonNews);
                            allNews.remove(comparisonNews);
                        }
                        continue;
                    }
                }
                matches = 0;
            }
            if (!siblingNews.isEmpty()) {
                currentNews.setMerged("Y");
                siblingNews.add(currentNews);
                allNews.remove(currentNews);
                matchNews.add(siblingNews);
            }
        }
        return matchNews;
    }
}
