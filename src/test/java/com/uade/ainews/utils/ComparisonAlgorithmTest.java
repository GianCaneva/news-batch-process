package com.uade.ainews.utils;

import com.uade.ainews.newsGeneration.dto.News;
import com.uade.ainews.newsGeneration.utils.ComparisonAlgorithm;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ComparisonAlgorithmTest extends TestCase {
    @Test
    public void givenMultiplesNewsThereAreSimilarOnes() {
        List<String> keywords1 = new LinkedList<>(Arrays.asList("Javier Milei", "Sergio Massa", "ministro", "PASO", "Patricia Bullrich"));
        List<String> keywords2 = new LinkedList<>(Arrays.asList("Javier Milei", "Sergio Massa", "C", "D", "E"));
        List<String> keywords5 = new LinkedList<>(Arrays.asList("X", "Y", "Z", "J", "Q"));
        List<String> keywords3 = new LinkedList<>(Arrays.asList("A", "Sergio Massa", "C", "PASO", "Patricia Bullrich"));
        List<String> keywords4 = new LinkedList<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<String> keywords6 = new LinkedList<>(Arrays.asList("E", "A", "F", "D", "B"));
        News news1 = News.builder().keywords(keywords1).build();
        News news2 = News.builder().keywords(keywords2).build();
        News news3 = News.builder().keywords(keywords3).build();
        News news4 = News.builder().keywords(keywords4).build();
        News news5 = News.builder().keywords(keywords5).build();
        News news6 = News.builder().keywords(keywords6).build();

        List<News> allNews = new LinkedList<>(Arrays.asList(news1, news2, news3, news4, news5, news6));

        List<List<News>> sameNews = ComparisonAlgorithm.identifySameNews(allNews);

        assertThat(sameNews.size()).isEqualTo(2);
        assertThat(sameNews.get(0).size()).isEqualTo(2);
        assertThat(sameNews.get(1).size()).isEqualTo(3);
    }

    @Test
    public void givenMultiplesNewsThereAreNotSimilarOnes() {
        List<String> keywords1 = new LinkedList<>(Arrays.asList("Javier Milei", "gabinete Económico", "ministro", "PASO", "medidas"));
        List<String> keywords2 = new LinkedList<>(Arrays.asList("X", "Y", "Z", "J", "Q"));
        List<String> keywords3 = new LinkedList<>(Arrays.asList("A", "B", "C", "D", "E"));
        News news1 = News.builder().keywords(keywords1).build();
        News news2 = News.builder().keywords(keywords2).build();
        News news3 = News.builder().keywords(keywords3).build();

        List<News> allNews = new LinkedList<>(Arrays.asList(news1, news2, news3));

        List<List<News>> sameNews = ComparisonAlgorithm.identifySameNews(allNews);

        assertThat(sameNews.size()).isEqualTo(0);

    }

    @Test
    public void testWithProdInfo() {

        List<String> keywords1 = new LinkedList<>(Arrays.asList("AMERICA", "LATINA", "FALTA", "OPORTUNIDADES", "SATISFACCION"));
        List<String> keywords2 = new LinkedList<>(Arrays.asList("PROYECTOS", "TURISMO", "SUSPENSION", "REORGANIZACION", "SECRETARIAS"));
        List<String> keywords3 = new LinkedList<>(Arrays.asList("POPULAR", "LIBERALISMO", "PERONISMO"));
        List<String> keywords4 = new LinkedList<>(Arrays.asList("ARGENTINAS", "FMI", "DEVALUACION", "INCERTIDUMBRE", "CAMBIO"));
        List<String> keywords5 = new LinkedList<>(Arrays.asList("BLUE", "EURO", "OFICIAL", "DOLAR", "OFICIAL"));
        List<String> keywords6 = new LinkedList<>(Arrays.asList("VIOLENCIA", "EXTREMA", "DERECHA"));
        List<String> keywords7 = new LinkedList<>(Arrays.asList("NUEVA", "CEPA", "SUBVARIANTE", "EG5", "VARIANTE"));
        List<String> keywords8 = new LinkedList<>(Arrays.asList("CAMBIO", "DE", "CICLO", "CICLO", "INVERSORES"));
        List<String> keywords9 = new LinkedList<>(Arrays.asList("PLANES", "DE", "FINANCIACION"));
        List<String> keywords10 = new LinkedList<>(Arrays.asList("DE", "AZAR", "NUMEROS", "DE", "LOTERIA"));
        List<String> keywords11 = new LinkedList<>(Arrays.asList("ACCIONES", "MERCADOS", "GALICIA", "YPF", "DESEMBOLSO"));
        List<String> keywords12 = new LinkedList<>(Arrays.asList("MERCADO", "DE", "TRABAJO", "INCERTIDUMBRE", "HABILIDADES"));
        List<String> keywords13 = new LinkedList<>(Arrays.asList("EDUCACION", "PRIVADA", "OBLIGATORIEDAD", "GRATUIDAD", "ESTADO"));
        List<String> keywords14 = new LinkedList<>(Arrays.asList("MEDIOS", "INTERNACIONALES"));
        List<String> keywords15 = new LinkedList<>(Arrays.asList("GOBERNADOR", "PERONISMO", "LIBERTAD", "AVANZA", "JUNTOS"));
        List<String> keywords16 = new LinkedList<>(Arrays.asList("NUEVA", "YORK", "MERCADO", "LABORAL", "DESEMPLEO"));
        List<String> keywords17 = new LinkedList<>(Arrays.asList("SEGURIDAD", "ANIBAL", "FERNANDEZ"));
        List<String> keywords18 = new LinkedList<>(Arrays.asList("DOLAR", "MEP", "FMI", "DOLAR", "BLUE"));
        List<String> keywords19 = new LinkedList<>(Arrays.asList("SEGURIDAD", "ANIBAL", "FERNANDEZ"));
        List<String> keywords20 = new LinkedList<>(Arrays.asList("DOLAR", "MEP", "FMI", "DOLAR", "BLUE"));
        List<String> keywords21 = new LinkedList<>(Arrays.asList("AEROPARQUE", "VUELOS", "DEMORAS"));
        List<String> keywords22 = new LinkedList<>(Arrays.asList("AMERICA", "LATINA", "FALTA", "OPORTUNIDADES", "SATISFACCION"));

        News news1 = News.builder().keywords(keywords1).build();
        News news2 = News.builder().keywords(keywords2).build();
        News news3 = News.builder().keywords(keywords3).build();
        News news4 = News.builder().keywords(keywords4).build();
        News news5 = News.builder().keywords(keywords5).build();
        News news6 = News.builder().keywords(keywords6).build();
        News news7 = News.builder().keywords(keywords7).build();
        News news8 = News.builder().keywords(keywords8).build();
        News news9 = News.builder().keywords(keywords9).build();
        News news10 = News.builder().keywords(keywords10).build();
        News news11 = News.builder().keywords(keywords11).build();
        News news12 = News.builder().keywords(keywords12).build();
        News news13 = News.builder().keywords(keywords13).build();
        News news14 = News.builder().keywords(keywords14).build();
        News news15 = News.builder().keywords(keywords15).build();
        News news16 = News.builder().keywords(keywords16).build();
        News news17 = News.builder().keywords(keywords17).build();
        News news18 = News.builder().keywords(keywords18).build();
        News news19 = News.builder().keywords(keywords19).build();
        News news20 = News.builder().keywords(keywords20).build();
        News news21 = News.builder().keywords(keywords21).build();
        News news22 = News.builder().keywords(keywords22).build();

        List<News> allNews = new LinkedList<>(Arrays.asList(news1, news2, news3, news4, news5, news6, news7, news8, news9, news10, news11, news12, news13, news14, news15, news16, news17, news18, news19, news20, news21, news22));

        List<List<News>> sameNews = ComparisonAlgorithm.identifySameNews(allNews);

        assertThat(sameNews.size()).isEqualTo(4);

    }

}