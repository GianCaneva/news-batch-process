package com.uade.ainews.utils;

import com.uade.ainews.newsGeneration.utils.KeywordFinderSpacy;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class KeywordFinderSpacyTest extends TestCase {

    @Test
    public void givenMultiplesNewsThereAreNotSimilarOnes() {
        String messageToBeCast = " PrimerElementoá , SegundoéElemento, terceríelemento, CuÁrtOEóelementú, QuintoElEmentO";
        List<String> parametrizedResponse = KeywordFinderSpacy.standardizeResponse(messageToBeCast);
        assertThat(parametrizedResponse.get(0)).isEqualTo("PRIMERELEMENTOA");
        assertThat(parametrizedResponse.get(1)).isEqualTo("SEGUNDOEELEMENTO");
        assertThat(parametrizedResponse.get(2)).isEqualTo("TERCERIELEMENTO");
        assertThat(parametrizedResponse.get(3)).isEqualTo("CUARTOEOELEMENTU");
        assertThat(parametrizedResponse.get(4)).isEqualTo("QUINTOELEMENTO");
    }

    @Test
    public void removePreposicion() {
        String messageToBeCast = " dermatitis, cadencia, de, cadena, dedede";

        List<String> parametrizedResponse = KeywordFinderSpacy.standardizeResponse(messageToBeCast);

        assertThat(parametrizedResponse.get(0)).isEqualTo("DERMATITIS");
        assertThat(parametrizedResponse.get(1)).isEqualTo("CADENCIA");
        assertThat(parametrizedResponse.get(2)).isEqualTo("CADENA");
        assertThat(parametrizedResponse.get(3)).isEqualTo("DEDEDE");

    }

    @Test
    public void givenTextReturnKeywords() throws Exception {
        String text0 = "La oposición salió a cuestionar el fallo judicial que ordenó la liberación de \"Chocolate\" Rigau, al asegurar que tendría el objetivo de garantizar el silencio del acusado y, de esa forma, evitar un pronto esclarecimiento del supuesto entramado de corrupción que ventilaron las maniobras perpetradas en los cajeros automáticos de un banco de La Plata. Entre los referentes opositores también crece el temor de que el hecho provoque un crecimiento del discurso antipolítica.ra la diputada nacional Margarita Stolbizer, la resolución de la Cámara de Apelaciones platense basa \"en una detención mal hecha, pero la causa no desaparece\", por lo que se mostró confiada en una pronta apelación del fiscal de cámara. Para la líder del GEN, detrás de la rápida liberación de Rigau habría \"un encubrimiento de la política. Vemos cómo la justicia termina siendo cómplice para que no sepa la cuestión de fondo, qué hay detrás de ese accionar. Nadie puede pensar que esta persona se quedaba con todos esos millones de pesos\", en clara alusión a que habría una estructura política detrás del implicado. En este sentido, Stolbizer planteó a sus legisladores provinciales la necesidad de \"apurar\" una ley de financiamiento político en la Provincia. \"De lo contrario, nos " +
                "enchastran' a todos y después nos sorprendemos porque la gente vota a Milei, analizó en diálogo con este diario. Si bien reconoció que no es delito encontrar a una persona sacando plata de un cajero, observó que \"hay que insistir en el fondo de la cuestión, para quién operaba\" el acusado. \"Lo van a tener que custodiar porque hay mucho interés en que no hable. Su liberación es para que no se investigue a fondo\" el caso, conjeturó.";
        List<String> parametrizedResponse = KeywordFinderSpacy.getKeyWords(text0);
        assertThat(parametrizedResponse.get(0)).isEqualTo("LIBERACION");
        assertThat(parametrizedResponse.get(1)).isEqualTo("FONDO");
        assertThat(parametrizedResponse.get(2)).isEqualTo("RIGAU");
        assertThat(parametrizedResponse.get(3)).isEqualTo("ACUSADO");
        assertThat(parametrizedResponse.get(4)).isEqualTo("PLATA");
        //Expected [('liberación', 3), ('fondo', 3), ('rigau', 2), ('acusado', 2), ('plata', 2)]

    }

}