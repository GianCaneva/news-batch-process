package com.uade.ainews.utils;

import com.uade.ainews.newsGeneration.utils.SsrReader;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
public class SsrReaderTest extends TestCase {

    @Test
    public void testGetAllLinksFromClarin() throws IOException {
        String CLARIN_RSS = "https://www.clarin.com/rss/lo-ultimo/";
        //List<String> allLinks = SsrReader.getAllLinks(CLARIN_RSS);
        //assertThat(allLinks.size()).isGreaterThan(5);
    }

    @Test
    public void testGetAllLinksFromPerfil() throws IOException {
        String PERFIL_RSS = "https://www.perfil.com/feed";
        //List<String> allLinks = SsrReader.getAllLinks(PERFIL_RSS);
        //assertThat(allLinks.size()).isGreaterThan(5);
    }
}