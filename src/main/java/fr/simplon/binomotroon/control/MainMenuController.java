package fr.simplon.binomotroon.control;

import fr.simplon.binomotroon.dbobjects.Group;
import fr.simplon.binomotroon.dbobjects.Projet;
import fr.simplon.binomotroon.dbobjects.User;
import fr.simplon.binomotroon.dbservices.GroupServices;
import fr.simplon.binomotroon.dbservices.ProjectServices;
import fr.simplon.binomotroon.util.ConsoleUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur du menu principal.
 */
public class MainMenuController
{
    public static final int MENU_LISTER_PROJETS      = 0;
    public static final int MENU_VOIR_GROUPES_PROJET = 1;
    public static final int MENU_CREER_PROJET        = 2;

    private Questions       mQuestions;
    private ProjectServices mProjectServices;
    private GroupServices   mGroupServices;

    /**
     * Constructeur.
     *
     * @param projectServices Le service de récupération des projets.
     * @param pGroupServices  Le service de récupération des groupes.
     */
    public MainMenuController(ProjectServices projectServices, GroupServices pGroupServices)
    {
        mQuestions = new Questions();
        mProjectServices = projectServices;
        mGroupServices = pGroupServices;
    }

    /**
     * Le menu principal propose 4 fonctionnalités :
     * <ul>
     *     <li>Lister mes projets (Apprenant ou Formateur)</li>
     *     <li>Voir les groupes d'un projet (Apprenant ou Formateur)</li>
     *     <li>Créer un projet (Formateur)</li>
     *     <li>Former des groupes aléatoires sur un projet (Formateur)</li>
     * </ul>
     * @param pAuthenticatedUser L'utilisateur qui est connecté au binomotron.
     */
    public void mainMenu(User pAuthenticatedUser)
    {
        while (true)
        {
            int reponse = mQuestions.menuPrincipal();
            if (reponse == Questions.REPONSE_QUITTER)
            {
                break;
            }
            else if (reponse == MENU_LISTER_PROJETS)
            {
                listerProjets();
            }
            else if (reponse == MENU_VOIR_GROUPES_PROJET)
            {
                voirGroupesProjet();
            }
            else if (reponse == MENU_CREER_PROJET)
            {
                creerProjet();
            }
        }
    }

    /**
     * Affiche la liste des projets existants.
     */
    public void listerProjets()
    {
        ConsoleUtil.clearConsole();

        for (Projet projet : mProjectServices.getProjets())
        {
            System.out.printf("[%d] %s%n", projet.id(), projet.libelle());
        }
        ConsoleUtil.pressEnter();
    }

    /**
     * Affiche la liste des groupes d'un projet. Au préalable, cette méthode demande à l'utilisateur
     * de choisir le numéro de projet qu'il veut afficher.
     */
    public void voirGroupesProjet()
    {
        ConsoleUtil.clearConsole();

        int idProjet = selectionnerProjet();
        try
        {
            System.out.printf("Groupes du projet :%n---------------------------%n");
            Collection<Group> groupes = mGroupServices.getGroupesByProjet(idProjet);
            for (Group groupe : groupes)
            {
                List<String> listeApprenants = groupe.apprenants().stream().map(a -> a.firstName() + " " + a.lastName()).collect(Collectors.toList());
                System.out.println(String.format("[%d personnes] %s", groupe.apprenants().size(), listeApprenants));
            }
        }
        catch (SQLException pE)
        {
            pE.printStackTrace();
        }
        ConsoleUtil.pressEnter();
    }

    /**
     * Permet de sélectionner un ID de projet. La méthode affiche la liste des projets et attend le
     * choix de l'utilisateur.
     *
     * @return L'id du projet choisi.
     */
    public int selectionnerProjet()
    {
        for (Projet projet : mProjectServices.getProjets())
        {
            System.out.printf("[%d] %s%n", projet.id(), projet.libelle());
        }
        List<Integer> listProjectIds = mProjectServices.getProjets().stream().map(p -> p.id()).collect(Collectors.toList());
        Integer[] options = listProjectIds.toArray(e -> new Integer[listProjectIds.size()]);
        int idProjet = ConsoleUtil.question("Veuillez sélectionner un projet", options, options[0]);

        return idProjet;
    }

    /**
     * Crée un nouveau projet en demandant son nom.
     */
    public void creerProjet()
    {
        final long ONE_YEAR = 1000L * 60L * 60L * 24L * 365L; // en millisecondes

        // Demande la saisie du nom de projet
        String infosProjet = mQuestions.demanderInfosProjet();
        try
        {
            Projet projet = new Projet(-1, infosProjet, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + ONE_YEAR));
            projet = mProjectServices.create(projet);
            System.out.printf("%n[OK] Projet créé : id=%d, libelle=%s, date_debut=%s, date_fin=%s%n", projet.id(), projet.libelle(), projet.dateDebut(), projet.dateFin());
            System.out.println();
        }
        catch (SQLException pE)
        {
            System.err.println("Erreur de création du projet :");
            pE.printStackTrace();
        }
        ConsoleUtil.pressEnter();
    }
}
