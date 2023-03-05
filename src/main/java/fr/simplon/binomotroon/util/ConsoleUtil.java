package fr.simplon.binomotroon.util;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe utilitaire proposant des méthodes pour interagir dans la console avec l'utilisateur.
 */
public class ConsoleUtil
{
    private ConsoleUtil(){}

    public static String question(String question)
    {
        Scanner mScanner = new Scanner(System.in);

        System.out.print(question);
        String nextLine = mScanner.nextLine();
        return nextLine;
    }

    public static String question(String question, String optionParDefaut)
    {
        Scanner mScanner = new Scanner(System.in);

        System.out.print(String.format("%s (défaut=%s) : ", question, optionParDefaut));
        String nextLine = mScanner.nextLine();
        if ( nextLine == null || nextLine.isEmpty()){
            return optionParDefaut;
        }
        return nextLine;
    }

    public static int question(String question, Integer[] reponses, int optionParDefaut)
    {
        int[] iReponses = new int[reponses.length];
        for (int i = 0; i < iReponses.length; i++)
        {
            iReponses[i] = reponses[i].intValue();
        }
        return question(question, iReponses, optionParDefaut);
    }

    /**
     * Pose une question à l'utilisateur et attend sa réponse.
     *
     * @param question        La question à poser.
     * @param reponses        Les réponses acceptées par le programme.
     * @param optionParDefaut La réponse par défaut (elle doit figurer parmi le tableau
     *                        <code>reponses</code>).

     * @return La réponse saisie par l'utilisateur.
     */
    public static int question(String question, int[] reponses, int optionParDefaut)
    {
        Scanner mScanner = new Scanner(System.in);

        // Valeur saisie par l'utilisateur (au début on initialise à Integer.MIN_VALUE)
        int iValue = Integer.MIN_VALUE;

        // Tant que l'utilisateur saisit une valeur invalide, on boucle
        while (iValue == Integer.MIN_VALUE)
        {
            // Affichage de la question
            System.out.print(String.format("%s %s : ", question, toStringWithDefaultValue(reponses, optionParDefaut)));

            String nextLine = mScanner.nextLine();
            if (nextLine == null || nextLine.isEmpty())
            {
                // Si l'utilisateur n'a rien saisi, c'est comme s'il avait
                // validé l'option par défaut
                iValue = optionParDefaut;
            }
            else
            {
                try
                {
                    iValue = Integer.parseInt(nextLine);
                }
                catch (NumberFormatException pE)
                {
                }
            }
            final int iComparison = iValue;
            if (!Arrays.stream(reponses).anyMatch(i -> i == iComparison))
            {
                iValue = Integer.MIN_VALUE;
            }
        }
        return iValue;
    }

    /**
     * Transforme le tableau d'options en chaine de caractères. L'option par défaut est affichée
     * avec un astérisque.
     *
     * @param values        Le tableau des options possibles.
     * @param defaultOption L'option par défaut parmi les options possibles.
     * @return Le tableau sous forme d'une chaine de caractères.
     */
    private static String toStringWithDefaultValue(
            int[] values,
            int defaultOption)
    {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < values.length - 1; i++)
        {
            sb.append(values[i]);
            if (values[i] == defaultOption)
            {
                sb.append("*");
            }
            sb.append("|");
        }
        sb.append(values[values.length - 1]);
        if (values[values.length - 1] == defaultOption)
        {
            sb.append("*");
        }
        sb.append(")");
        return sb.toString();
    }

    public static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressEnter()
    {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Appuyez sur Entree pour continuer...");
        mScanner.nextLine();
    }
}
