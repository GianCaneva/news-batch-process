package com.uade.ainews.newsGeneration.utils;
import com.uade.ainews.newsGeneration.dto.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScrapper {

    public static News getInformationFromPage(News news) throws IOException {

        if (news.getUrl().substring(0, 22).contains("clarin")){
            return scrapperClarin(news);
        } else{
            return scrapperPerfil(news);
        }

    }


    private static News scrapperClarin(News news) throws IOException {
        Document document = Jsoup.connect(news.getUrl()).get();
        //Title
        Elements headline = document.select("h1");
        Elements subheaderGeneral = document.select("div.title.check-space");
        Elements subheaderTag = subheaderGeneral.select("h2");
        String title = headline.text();
        String subtitle = subheaderTag.text();

        //Article body
        Elements body = document.select("article.entry-body");
        StringBuilder article = new StringBuilder();
        article.append(subtitle);
        for (Element partOfBody : body) {
            article.append(partOfBody.text());
        }
        System.out.println("Article: " + article);
        news.setTitle(title);
        news.setArticle(article);
        return news;

    }

    private static News scrapperPerfil(News news) throws IOException {
        Document document = Jsoup.connect(news.getUrl()).get();
        //Title
        Elements headline = document.select("h1");
        String title = headline.text();

        //Article body
        Elements body = document.select("div.article__content");
        StringBuilder article = new StringBuilder();
        for (Element partOfBody : body) {
            article.append(partOfBody.text());
        }

        news.setTitle(title);
        news.setArticle(article);
        return news;
    }
}


