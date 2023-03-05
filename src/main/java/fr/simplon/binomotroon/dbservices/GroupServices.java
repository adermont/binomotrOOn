package fr.simplon.binomotroon.dbservices;

import fr.simplon.binomotroon.db.Database;
import fr.simplon.binomotroon.dbobjects.Group;
import fr.simplon.binomotroon.dbobjects.Role;
import fr.simplon.binomotroon.dbobjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Service de gestion des groupes.
 */
public class GroupServices
{
    public static final String T_GROUPES               = "groupe"; // Table des groupes
    public static final String T_USERS                 = "user"; // Table des utilisateurs
    public static final String T_LINK_APPRENANT_GROUPE = "apprenant_integre_groupe"; // Table des liens entre groupes et apprenants

    /**
     * Sélectionne tous les apprenants qui appartiennent déjà à un groupe d'un projet.
     */
    private static final String SQL_SELECT_GROUPE_BY_PROJET = "SELECT " + T_GROUPES + ".id AS idGroupe, "
            + T_GROUPES + ".libelle AS libelleGroupe, "
            + T_USERS + ".id AS idApprenant, "
            + T_USERS + ".login AS loginApprenant, "
            + T_USERS + ".nom AS nomApprenant, "
            + T_USERS + ".prenom AS prenomApprenant"
            + " FROM " + T_GROUPES
            + " INNER JOIN " + T_LINK_APPRENANT_GROUPE + " ON " + T_LINK_APPRENANT_GROUPE + ".id_groupe=" + T_GROUPES + ".id"
            + " INNER JOIN " + T_USERS + " ON " + T_USERS + ".id=" + T_LINK_APPRENANT_GROUPE + ".id_apprenant"
            + " WHERE id_projet=? "
            + " GROUP BY " + T_GROUPES + ".id, " + T_USERS + ".id, "+ T_USERS + ".login, " + T_USERS + ".nom, " + T_USERS + ".prenom";

    private Database mDatabase;

    public GroupServices(Database pDatabase)
    {
        mDatabase = pDatabase;
    }

    /**
     * Exécute une requête SQL qui retourne tous les groupes d'un projet.
     *
     * @param con      La connexion à la base de données.
     * @param idProjet L'identifiant du projet.
     * @return Une map contenant les groupes sous forme de List&lt;String&gt; rangés par clé
     *         String.
     *
     * @throws SQLException En cas de problème d'accès à la base de données.
     */
    public Collection<Group> getGroupesByProjet(int idProjet) throws SQLException
    {
        Map<String, Group> map = new HashMap<>();

        try (Connection con = mDatabase.getConnection())
        {
            try (PreparedStatement st = con.prepareStatement(SQL_SELECT_GROUPE_BY_PROJET))
            {
                st.setInt(1, idProjet);
                try (ResultSet rs = st.executeQuery())
                {
                    while (rs.next())
                    {
                        int i = 1;
                        int idGroupe = rs.getInt(i++);
                        String nomGroupe = rs.getString(i++);
                        int idApprenant = rs.getInt(i++);
                        String loginApp = rs.getString(i++);
                        String nomApprenant = rs.getString(i++);
                        String prenomApprenant = rs.getString(i++);
                        User user = new User(idApprenant, loginApp, prenomApprenant, nomApprenant, Role.Apprenant);

                        // Si le groupe n'existe pas encore dans la map on le crée et on l'ajoute à la map
                        Group groupe = map.get(nomGroupe);
                        if (groupe == null)
                        {
                            groupe = new Group(idGroupe, nomGroupe, new ArrayList<>());
                            map.put(nomGroupe, groupe);
                        }
                        // On ajoute l'apprenant dans la liste des apprenants du groupe courant
                        groupe.apprenants().add(user);
                    }
                }
            }
        }
        return map.values();
    }
}
