package com.uade.ainews.newsGeneration.service;

import com.uade.ainews.newsGeneration.dto.News;
import com.uade.ainews.newsGeneration.dto.Rss;
import com.uade.ainews.newsGeneration.dto.SummarizedNews;
import com.uade.ainews.newsGeneration.repository.NewsRepository;
import com.uade.ainews.newsGeneration.repository.SummarizedNewsRepository;
import com.uade.ainews.newsGeneration.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsGetterService {

    // Set up every url source with their sections
    public static final String CLARIN_RSS_ULTIMO = "https://www.clarin.com/rss/lo-ultimo/";
    public static final String PERFIL_RSS_ULTIMO = "https://www.perfil.com/feed";
    public static final String CLARIN_RSS_POLITICA = "https://www.clarin.com/rss/politica/";
    public static final String PERFIL_RSS_POLITICA = "https://www.perfil.com/feed/politica";
    public static final String CLARIN_RSS_ECONOMIA = "https://www.clarin.com/rss/economia/";
    public static final String PERFIL_RSS_ECONOMIA = "https://www.perfil.com/feed/economia";
    public static final String CLARIN_RSS_DEPORTES = "https://www.clarin.com/rss/deportes/";
    public static final String PERFIL_RSS_DEPORTES = "https://www.perfil.com/feed/deportes";
    public static final String CLARIN_RSS_SOCIALES = "https://www.clarin.com/rss/sociedad/";
    public static final String PERFIL_RSS_SOCIALES = "https://www.perfil.com/feed/sociedad";
    public static final String CLARIN_RSS_INTERNACIONAL = "https://www.clarin.com/rss/mundo/";
    public static final String PERFIL_RSS_INTERNACIONAL = "https://www.perfil.com/feed/internacionales";
    public static final String CLARIN_RSS_POLICIALES = "https://www.clarin.com/rss/policiales/";
    public static final String PERFIL_RSS_POLICIALES = "https://www.perfil.com/feed/policia";
    public static final int TITLE_MAX_EXTENSION = 25;
    public static final int TITLE_MIN_EXTENSION = 5;

    @Autowired
    private SummarizedNewsRepository summarizedNewsRepository;
    @Autowired
    private NewsRepository newsRepository;

    public void getSameNews() {

        // Load RSS sources from which the information will be extracted
        System.out.println("Starting get source process.");
        List<Rss> allSourceLinks = new LinkedList<>();
        loadLinks(allSourceLinks);
        List<Rss> allRSSLinks = new LinkedList<>();
        for (int i = 0; i < allSourceLinks.size(); i++) {
            try {
                allRSSLinks.addAll(RssReader.getAllLinks(allSourceLinks.get(i)));
            } catch (Exception e) {
                System.out.println("===Error=== " + e.getClass()
                        + " Message: " + e.getMessage()
                        + " Cause: " + e.getCause()
                        + " Stacktrace:" + e.getStackTrace());
            }
        }

        // Access all URLs news and process article/content (get content, generate title and identify keywords)
        List<News> allNewsWithInfo = new LinkedList<>();
        for (int i = 0; i < allRSSLinks.size(); i++) {
            try {
                Rss currentRss = allRSSLinks.get(i);
                System.out.println("/////////////////////// Start process ///////////////////////////");
                System.out.println("Execute analysis for: " + currentRss.getUrl());
                Optional<News> oneByUrl = newsRepository.findOneByUrl(currentRss.getUrl());
                    /*
                        In case that URL does not exist in the database, it means that it was never part of a
                        SummarizedNews and the analysis proceeds.
                         Otherwise, that URL was previously processed and by business rules,
                         a URL cannot be in more than one summary.
                     */
                if (oneByUrl.isEmpty()) { //
                    // Get article and title from a URL -Scrapping process-.
                    News newsWithInformationFromPagAndKeyWords = WebScrapper.getInformationFromPage(News.builder().url(currentRss.getUrl()).section(currentRss.getSection()).releaseDate(LocalDateTime.now()).build());

                    // Get keywords from the article
                    List<String> keyWords = KeywordFinderSpacy.getKeyWords(newsWithInformationFromPagAndKeyWords.getArticle());
                    newsWithInformationFromPagAndKeyWords.setKeywords(keyWords);
                    allNewsWithInfo.add(newsWithInformationFromPagAndKeyWords);
                }
                System.out.println("----------------------- End process -----------------------");
            } catch (Exception e) {
                System.out.println("===Error=== " + e.getClass()
                        + " Message: " + e.getMessage()
                        + " Cause: " + e.getCause()
                        + " Stacktrace:" + e.getStackTrace());
            }
        }

        // Identify and match multiple news that refers to the same event
        System.out.println("Start comparison algorithm process.");
        List<List<News>> allSiblingNews = ComparisonAlgorithm.identifySameNews(allNewsWithInfo);

        // Merge similar articles onto a new one
        mergeSameNewsOntoNewArticle(allSiblingNews);
    }

    // Takes all news that have another similar news a creates a new one
    public void mergeSameNewsOntoNewArticle(List<List<News>> allSiblingNews) {
        System.out.println("Merging same news.");
        for (int i = 0; i < allSiblingNews.size(); i++) {
            StringBuilder mergeSiblingTitles = new StringBuilder();
            StringBuilder mergeSiblingArticles = new StringBuilder();
            List<News> siblings = allSiblingNews.get(i);
            String section = "section";
            for (int j = 0; j < siblings.size(); j++) {
                section = siblings.get(j).getSection();
                mergeSiblingTitles.append(siblings.get(j).getTitle()).append(" ");
                mergeSiblingArticles.append(siblings.get(j).getArticle()).append(" ");
                // Stores in the database the news that were used to generate an AI article.
                try {
                    newsRepository.save(siblings.get(j));
                } catch (Exception e) {
                    System.out.println("===Error=== " + e.getClass()
                            + " Message: " + e.getMessage()
                            + " Cause: " + e.getCause()
                            + " Stacktrace:" + e.getStackTrace());
                }
            }
            try {
                // Generate a summarized title using AI
                String titleSummarized = SummarizeTitle.sumUp(String.valueOf(mergeSiblingTitles), TITLE_MAX_EXTENSION, TITLE_MIN_EXTENSION);
                // Save merged all same news onto DB
                System.out.println("Saving element on DB");
                summarizedNewsRepository.save(SummarizedNews.builder()
                        .section(section)
                        .title(titleSummarized)
                        .rawArticle(String.valueOf(mergeSiblingArticles))
                        .releaseDate(LocalDateTime.now())
                        .build());
            } catch (Exception e) {
                System.out.println("===Error=== " + e.getClass()
                        + " Message: " + e.getMessage()
                        + " Cause: " + e.getCause()
                        + " Stacktrace:" + e.getStackTrace());
            }
        }
    }

    // Map each RSS URL with a specific section
    private static void loadLinks(List<Rss> allRSSLinks) {

        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ULTIMO).section("LAST").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ULTIMO).section("LAST").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLITICA).section("POLITICS").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLITICA).section("POLITICS").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ECONOMIA).section("ECONOMY").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ECONOMIA).section("ECONOMY").build());

        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_DEPORTES).section("SPORTS").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_DEPORTES).section("SPORTS").build());

        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_SOCIALES).section("SOCIAL").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_SOCIALES).section("SOCIAL").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_INTERNACIONAL).section("INTERNATIONAL").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_INTERNACIONAL).section("INTERNATIONAL").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLICIALES).section("POLICE").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLICIALES).section("POLICE").build());


    }

}

