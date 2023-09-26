package com.uade.ainews.newsGeneration.service;

import com.uade.ainews.newsGeneration.dto.News;
import com.uade.ainews.newsGeneration.dto.Rss;
import com.uade.ainews.newsGeneration.dto.SummarizedNews;
import com.uade.ainews.newsGeneration.repository.NewsGenerationRepository;
import com.uade.ainews.newsGeneration.repository.SummarizedNewsRepository;
import com.uade.ainews.newsGeneration.utils.ComparisonAlgorithm;
import com.uade.ainews.newsGeneration.utils.KeywordFinder;
import com.uade.ainews.newsGeneration.utils.SummarizeArticle;
import com.uade.ainews.newsGeneration.utils.WebScrapper;
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
    private NewsGenerationRepository newsGenerationRepository;

    public void getSameNews() {

        try {
            List<Rss> allRSSLinks = new LinkedList<>();
            loadLinks(allRSSLinks);
            List<News> allNewsWithInfo = new LinkedList<>();
            //todo modificar para que use todos los elementos de la lista y no solo 30
            for (int i = 30; i < allRSSLinks.size(); i++) {
                Rss currentRss = allRSSLinks.get(i);
                Optional<News> oneByUrl = newsGenerationRepository.findOneByUrl(currentRss.getUrl());
                //En caso de que no haya ninguna URL igual en la base de datos, se procede al analisis
                if (oneByUrl.isEmpty()) {
                    //Scrapping. Get article and title from a URL
                    News newsWithInformationFromPagAndKeyWords = WebScrapper.getInformationFromPage(News.builder().url(currentRss.getUrl()).section(currentRss.getSection()).build());
                    //Get keywords
                    List<String> keyWords = KeywordFinder.getKeyWords(newsWithInformationFromPagAndKeyWords.getArticle());
                    newsWithInformationFromPagAndKeyWords.setKeywords(keyWords);
                    allNewsWithInfo.add(newsWithInformationFromPagAndKeyWords);
                }
            }
            //Identify and match same news
            List<List<News>> allSiblingNews = ComparisonAlgorithm.identifySameNews(allNewsWithInfo);

            //Merge same articles onto a new one
            for(int i = 0; i<allSiblingNews.size(); i++){
                StringBuilder mergeSiblingTitles = new StringBuilder();
                StringBuilder mergeSiblingArticles = new StringBuilder();
                List<News> siblings = allSiblingNews.get(i);
                for (int j = 0; j<siblings.size(); j++){
                    mergeSiblingTitles.append(siblings.get(j).getTitle()).append(" ");
                    mergeSiblingArticles.append(siblings.get(j).getArticle()).append(" ");
                    //Almacena en la base de datos las noticias que se utilizaron para genear un AI articulo
                    newsGenerationRepository.save(siblings.get(j));
                }
                String titleSummarized = SummarizeArticle.sumUp(String.valueOf(mergeSiblingTitles), TITLE_MAX_EXTENSION, TITLE_MIN_EXTENSION);
                //Save on the db
                summarizedNewsRepository.save(SummarizedNews.builder().title(titleSummarized).rawArticle(String.valueOf(mergeSiblingArticles)).releaseDate(LocalDateTime.now()).build());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loadLinks(List<Rss> allRSSLinks) {
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ULTIMO).section("Last").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ULTIMO).section("Last").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLITICA).section("Politics").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLITICA).section("Politics").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_ECONOMIA).section("Economy").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_ECONOMIA).section("Economy").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_DEPORTES).section("Sports").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_DEPORTES).section("Sports").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_SOCIALES).section("Social").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_SOCIALES).section("Social").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_INTERNACIONAL).section("International").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_INTERNACIONAL).section("International").build());
        allRSSLinks.add(Rss.builder().url(CLARIN_RSS_POLICIALES).section("Policy").build());
        allRSSLinks.add(Rss.builder().url(PERFIL_RSS_POLICIALES).section("Policy").build());
    }

}

