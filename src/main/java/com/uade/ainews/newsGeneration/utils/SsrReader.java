package com.uade.ainews.newsGeneration.utils;

import com.uade.ainews.newsGeneration.dto.Rss;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SsrReader {

    public static List<Rss> getAllLinks(Rss aRssSource) throws Exception {
        try {
            Document document = Jsoup.connect(aRssSource.getUrl()).get();
            //Article body
            Elements allLinksRaw = document.select("link");
            List<Rss> allLinks = new LinkedList<>();
            for (Element singleLink : allLinksRaw) {
                allLinks.add(Rss.builder().url(singleLink.text()).section(aRssSource.getSection()).build());
            }
            return allLinks;
        } catch (Exception e){
            throw new Exception("Error getting RSS sources from: " + aRssSource.getUrl() , e);
        }
    }
}
