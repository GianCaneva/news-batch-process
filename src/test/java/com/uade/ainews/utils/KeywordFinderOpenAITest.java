package com.uade.ainews.utils;

import com.uade.ainews.newsGeneration.utils.KeywordFinderOpenAI;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
public class KeywordFinderOpenAITest extends TestCase {

    @Test
    public void givenMultiplesNewsThereAreNotSimilarOnes() {
        String messageToBeCast = " PrimerElementoá , SegundoéElemento, terceríelemento, CuÁrtOEóelementú, QuintoElEmentO";
        List<String> parametrizedResponse = KeywordFinderOpenAI.standardizeResponse(messageToBeCast);
        assertThat(parametrizedResponse.get(0)).isEqualTo("PRIMERELEMENTOA");
        assertThat(parametrizedResponse.get(1)).isEqualTo("SEGUNDOEELEMENTO");
        assertThat(parametrizedResponse.get(2)).isEqualTo("TERCERIELEMENTO");
        assertThat(parametrizedResponse.get(3)).isEqualTo("CUARTOEOELEMENTU");
        assertThat(parametrizedResponse.get(4)).isEqualTo("QUINTOELEMENTO");
    }

    @Test
    public void removePreposicion() {
        String messageToBeCast = " dermatitis, cadencia, de, cadena, dedede";

        List<String> parametrizedResponse = KeywordFinderOpenAI.standardizeResponse(messageToBeCast);

        assertThat(parametrizedResponse.get(0)).isEqualTo("DERMATITIS");
        assertThat(parametrizedResponse.get(1)).isEqualTo("CADENCIA");
        assertThat(parametrizedResponse.get(2)).isEqualTo("CADENA");
        assertThat(parametrizedResponse.get(3)).isEqualTo("DEDEDE");


    }

}