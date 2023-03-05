package fr.simplon.binomotroon.control;

import fr.simplon.binomotroon.util.ConsoleUtil;

import java.io.Console;
import java.io.IOException;

public class Questions
{
    public static final int REPONSE_QUITTER = 9;

    /**
     * Demande son login à l'utilisateur.
     *
     * @param loginParDefaut Le login qui sera utilisé si l'utilisateur ne saisit rien.
     * @return Le login saisi.
     */
    public String demanderLogin(String loginParDefaut)
    {
        return ConsoleUtil.question("Login", loginParDefaut);
    }

    /**
     * Demande son mot de passe à l'utilisateur.
     *
     * @return Le mot de passe saisi.
     */
    public String demanderMotDePasse()
    {
        Console console = System.console();
        if (console != null)
        {
            char[] pwd = console.readPassword("Mot de passe : ");
            return new String(pwd);
        }
        else
        {
            return ConsoleUtil.question("Mot de passe : ");
        }
    }

    /**
     * Demande s'il faut insérer les groupes constitués en base de données.
     *
     * @return <code>true</code> si l'utilisateur a choisi "oui", <code>false</code> sinon.
     */
    public boolean demanderInsererGroupes()
    {
        return false;
    }

    /**
     * Le menu principal propose 4 fonctionnalités :
     * <ul>
     *     <li>Lister mes projets (Apprenant ou Formateur)</li>
     *     <li>Voir les groupes d'un projet (Apprenant ou Formateur)</li>
     *     <li>Créer un projet (Formateur)</li>
     *     <li>Former des groupes aléatoires sur un projet (Formateur)</li>
     * </ul>
     */
    public int menuPrincipal()
    {
        String question = null;
        try
        {
            question = new String(getClass().getResourceAsStream("menuPrincipal.txt").readAllBytes());
            int[] reponses = new int[]{0, 1, 2, 3, REPONSE_QUITTER};
            int optionParDefaut = 0;
            return ConsoleUtil.question(question, reponses, optionParDefaut);
        }
        catch (IOException pE)
        {
            throw new RuntimeException(pE);
        }
    }

    /**
     * Demande les informations d'un projet.
     * @return Les infos saisies.
     */
    public String demanderInfosProjet()
    {
        return ConsoleUtil.question("Nom du projet : ");
    }
}
