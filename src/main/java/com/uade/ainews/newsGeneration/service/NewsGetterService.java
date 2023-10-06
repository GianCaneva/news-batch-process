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
    // CRON

    //Set up every url source with their sections
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

        // Load RSS sources
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

        //Access all URLs news and process article/content (get content, generate title and identify keywords)
        List<News> allNewsWithInfo = new LinkedList<>();
        for (int i = 0; i < allRSSLinks.size(); i++) {
            try {
                Rss currentRss = allRSSLinks.get(i);
                Optional<News> oneByUrl = newsRepository.findOneByUrl(currentRss.getUrl());
                    /*
                        En caso de que no existe esa URL en la base de datos, significa que nunca fue parte de una SummarizedNews
                        y se procede al analisis.
                        De otra forma, esa URL fue procesada previamente y por reglas de negocio, una URL no puede estar
                        en mas de un resumen
                     */
                if (oneByUrl.isEmpty()) { //
                    //Scrapping. Get article and title from a URL
                    News newsWithInformationFromPagAndKeyWords = WebScrapper.getInformationFromPage(News.builder().url(currentRss.getUrl()).section(currentRss.getSection()).releaseDate(LocalDateTime.now()).build());
                    //Get keywords from the article
                    List<String> keyWords = KeywordFinderSpacy.getKeyWords(newsWithInformationFromPagAndKeyWords.getArticle());
                    newsWithInformationFromPagAndKeyWords.setKeywords(keyWords);
                    allNewsWithInfo.add(newsWithInformationFromPagAndKeyWords);
                }
            } catch (Exception e) {
                System.out.println("===Error=== " + e.getClass()
                        + " Message: " + e.getMessage()
                        + " Cause: " + e.getCause()
                        + " Stacktrace:" + e.getStackTrace());
            }
        }

        //Identify and match same news
        List<List<News>> allSiblingNews = ComparisonAlgorithm.identifySameNews(allNewsWithInfo);

        //Merge same articles onto a new one
        mergeSameNewsOntoNewArticle(allSiblingNews);
    }

    public void mergeSameNewsOntoNewArticle(List<List<News>> allSiblingNews) {
        for (int i = 0; i < allSiblingNews.size(); i++) {
            StringBuilder mergeSiblingTitles = new StringBuilder();
            StringBuilder mergeSiblingArticles = new StringBuilder();
            List<News> siblings = allSiblingNews.get(i);
            String section = "section";
            for (int j = 0; j < siblings.size(); j++) {
                section = siblings.get(j).getSection();
                mergeSiblingTitles.append(siblings.get(j).getTitle()).append(" ");
                mergeSiblingArticles.append(siblings.get(j).getArticle()).append(" ");
                //Almacena en la base de datos las noticias que se utilizaron para genear un AI articulo
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
                String titleSummarized = SummarizeTitle.sumUp(String.valueOf(mergeSiblingTitles), TITLE_MAX_EXTENSION, TITLE_MIN_EXTENSION);
                //Save merged all same news onto DB
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

    private static void loadLinks(List<Rss> allRSSLinks) {
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ULTIMO).section("LAST").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ULTIMO).section("LAST").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLITICA).section("POLITICS").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLITICA).section("POLITICS").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ECONOMIA).section("ECONOMY").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ECONOMIA).section("ECONOMY").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_DEPORTES).section("SPORTS").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_DEPORTES).section("SPORTS").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_SOCIALES).section("SOCIAL").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_SOCIALES).section("SOCIAL").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_INTERNACIONAL).section("INTERNATIONAL").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_INTERNACIONAL).section("INTERNATIONAL").build());
//        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLICIALES).section("POLICE").build());
//        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLICIALES).section("POLICE").build());
    }

}

