package com.uade.ainews.utils;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
public class RssReaderTest extends TestCase {

    @Test
    public void testGetAllLinksFromClarin() throws IOException {
        String CLARIN_RSS = "https://www.clarin.com/rss/lo-ultimo/";
        //List<String> allLinks = RssReader.getAllLinks(CLARIN_RSS);
        //assertThat(allLinks.size()).isGreaterThan(5);
    }

    @Test
    public void testGetAllLinksFromPerfil() throws IOException {
        String PERFIL_RSS = "https://www.perfil.com/feed";
        //List<String> allLinks = RssReader.getAllLinks(PERFIL_RSS);
        //assertThat(allLinks.size()).isGreaterThan(5);
    }
}