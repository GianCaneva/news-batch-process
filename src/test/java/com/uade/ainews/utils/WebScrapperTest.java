package com.uade.ainews.utils;

import com.uade.ainews.TestUtils;
import com.uade.ainews.newsGeneration.dto.News;
import com.uade.ainews.newsGeneration.utils.WebScrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapperTest {

    @Test
    void testGetInformationFromPagina() throws Exception {
        News news = TestUtils.createNewsWithLongContentFromPerfil();
        News informationFromPage = WebScrapper.getInformationFromPage(news);
        assertEquals(informationFromPage.getUrl(), "https://www.perfil.com/noticias/bloomberg/bc-radicalismo-de-milei-no-es-lo-que-argentina-necesita.phtml");
        assertEquals(informationFromPage.getTitle(), "El radicalismo de Milei no es lo que la Argentina necesita");
        assertEquals(informationFromPage.getArticle().substring(0, 50), "El inesperado éxito de Javier Milei en las eleccio");
    }

    @Test
    void testGetInformationFromClarin() throws Exception {
        News news = TestUtils.createNewsWithLongContentFromClarin();
        News informationFromPage = WebScrapper.getInformationFromPage(news);
        assertEquals(informationFromPage.getUrl(), "https://www.clarin.com/economia/siguen-largas-filas-estaciones-servicio-cargar-nafta-gobierno-atribuye-psicosis_0_M9NfwNZoFo.html");
        assertEquals(informationFromPage.getTitle(), "Siguen las largas filas en las estaciones de servicio para cargar nafta y el Gobierno lo atribuye a una \"psicosis\"");
        assertEquals(informationFromPage.getArticle().substring(0, 50), "Por trabas para importar combustibles, falta nafta");
    }
}