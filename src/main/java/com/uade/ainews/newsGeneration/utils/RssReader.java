package com.uade.ainews.newsGeneration.utils;

import com.uade.ainews.newsGeneration.dto.Rss;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class RssReader {

    // Given a RSS URL, extract news url
    public static List<Rss> getAllLinks(Rss aRssSource) throws Exception {
        try {
            Document document = Jsoup.connect(aRssSource.getUrl()).get();
            // Article body
            Elements allLinksRaw = document.select("link");
            List<Rss> allLinks = new LinkedList<>();
            for (Element singleLink : allLinksRaw) {
                String section = aRssSource.getSection();
                if (section.equals("LAST")) {
                    section = extractSectionFromLast(singleLink.text());
                }
                allLinks.add(Rss.builder().url(singleLink.text()).section(section).build());
            }
            return allLinks;
        } catch (Exception e) {
            throw new Exception("Error getting RSS sources from: " + aRssSource.getUrl(), e);
        }
    }

    // If the URL comes from LAST section, this method will be executed to assign it a specific.
    public static String extractSectionFromLast(String url) {
        String newSection = "";
        // Splits the URL into parts using "/" as delimiter
        String[] parts = url.split("/");

        // Gets the last part (which is what it needs)
        String section = parts[parts.length - 1];

        switch (section) {
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
            case "policiales":
            case "policia":
                newSection = "POLICE";
                break;
            default:
                newSection = "OTHERS";
                break;
        }

        return newSection;
    }
}
