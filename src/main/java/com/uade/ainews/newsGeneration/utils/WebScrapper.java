package com.uade.ainews.newsGeneration.utils;

import com.uade.ainews.newsGeneration.dto.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScrapper {

    public static News getInformationFromPage(News news) throws Exception {

        if (news.getUrl().substring(0, 22).contains("clarin")) {
            return scrapperClarin(news);
        } else {
            return scrapperPerfil(news);
        }

    }


    private static News scrapperClarin(News news) throws Exception {
        try {
            System.out.println("Clarin scrapper started");
            Document document = Jsoup.connect(news.getUrl()).get();
            // Title
            Elements headline = document.select("h1");
            Elements subheaderGeneral = document.select("div.title.check-space");
            Elements subheaderTag = subheaderGeneral.select("h2");
            String title = headline.text();
            String subtitle = subheaderTag.text();

            // Article body
            Elements body = document.select("article.entry-body");
            // Not all articles have the same structure
            if (body.size() == 0) {
                body = document.select("div.StoryTextContainer");
            }
            StringBuilder article = new StringBuilder();
            article.append(subtitle);
            for (Element partOfBody : body) {
                article.append(partOfBody.text());
            }
            news.setTitle(title);
            news.setArticle(article);
            System.out.println("Clarin scrapper completed");
            return news;
        } catch (Exception e) {
            throw new Exception("Error reading article from CLARIN: " + news.getUrl(), e);
        }

    }

    private static News scrapperPerfil(News news) throws Exception {
        try {
            System.out.println("Perfil scrapper started");
            Document document = Jsoup.connect(news.getUrl()).get();
            // Title
            Elements headline = document.select("h1");
            String title = headline.text();

            // Article body
            Elements body = document.select("div.article__content").not("p.destacadoNota");
            // Remove extra tags inside of the main div
            body.select("p.destacadoNota").remove();
            body.select("div.related-news").remove();
            body.select("div.d-lg-none").remove();
            StringBuilder article = new StringBuilder();
            for (Element partOfBody : body) {
                article.append(partOfBody.text());
            }

            news.setTitle(title);
            news.setArticle(article);
            System.out.println("Perfil scrapper completed");
            return news;
        } catch (Exception e) {
            throw new Exception("Error reading article from PERFIL: " + news.getUrl(), e);
        }
    }
}


