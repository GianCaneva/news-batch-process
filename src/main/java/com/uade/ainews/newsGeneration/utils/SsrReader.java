package com.uade.ainews.newsGeneration.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SsrReader {

    public static List<String> getAllLinks(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        //Article body
        Elements allLinksRaw = document.select("link");
        List<String> allLinks = new LinkedList<>();
        for (Element singleLink : allLinksRaw) {
            allLinks.add(singleLink.text());
        }
        return allLinks;
    }
}
