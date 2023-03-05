package fr.simplon.binomotroon;

import fr.simplon.binomotroon.util.ConsoleUtil;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsoleUtilTest
{
    InputStream in;

    @BeforeEach
    public void before(){
        in = System.in;
    }

    @AfterEach
    public void after(){
        System.setIn(in);
    }

    @Test
    @Order(1)
    public void testReponseParDefaut()
    {
        // On simule le fait de taper Entrée (caractère "\n" = retour à la ligne)
        System.setIn(new ByteArrayInputStream("\n".getBytes()));

        // Options sélectionnables : 0, 1 ou 2
        int[] options = new int[]{
                0, 1, 2
        };
        int result = ConsoleUtil.question("Indiquez votre choix", options, 0, 9);
        Assertions.assertEquals(0, result);
    }

    @Test
    @Order(2)
    public void testToutesReponsesValides()
    {
        // Options sélectionnables : 0, 1 ou 2
        int[] options = new int[]{
                0, 1, 2
        };
        // On simule le fait de taper sur 0 + Entrée
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        int result = ConsoleUtil.question("Indiquez votre choix", options, 0, 9);
        Assertions.assertEquals(0, result);

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        result = ConsoleUtil.question("Indiquez votre choix", options, 0, 9);
        Assertions.assertEquals(1, result);

        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        result = ConsoleUtil.question("Indiquez votre choix", options, 0, 9);
        Assertions.assertEquals(2, result);
    }

    @Test
    @Order(3)
    public void testReponseInvalide()
    {
        // Options sélectionnables : 0, 1 ou 2
        int[] options = new int[]{
                0, 1, 2
        };
        // On simule le fait de taper sur 3 + Entrée
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        Assertions.assertThrows(NoSuchElementException.class, () -> ConsoleUtil.question("Indiquez votre choix", options, 0, 9));
    }
}
