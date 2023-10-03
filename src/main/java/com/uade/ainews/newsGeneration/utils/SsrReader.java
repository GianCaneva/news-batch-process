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
                String section = aRssSource.getSection();
                if (section.equals("LAST")){
                    section = extracSectionFromLast(section);
                }
                allLinks.add(Rss.builder().url(singleLink.text()).section(section).build());
            }
            return allLinks;
        } catch (Exception e){
            throw new Exception("Error getting RSS sources from: " + aRssSource.getUrl() , e);
        }
    }

    public static String extracSectionFromLast(String url) {
        String newSection = "";
        // Divide la URL en partes usando "/" como delimitador
        String[] parts = url.split("/");

        // Obtiene la última parte (que es lo que necesitas)
        String section = parts[parts.length - 1];

        switch (section){
            case "politica":
                newSection = "POLITICS";
                break;
            case "economia":
                newSection = "ECONOMY";
                break;
            case "deportes":
                newSection = "SPORTS";
                break;
            case "sociedad":
                newSection = "SOCIAL";
                break;
            case "mundo":
            case "internacionales":
                newSection = "INTERNATIONAL";
                break;
            case "policiales" :
            case "policia":
                newSection = "POLICE";
                break;
            default:
                newSection = "LAST";
                break;
        }

        return newSection;
    }
}
