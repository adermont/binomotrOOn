package fr.simplon.binomotroon.dbservices;

import fr.simplon.binomotroon.db.Database;
import fr.simplon.binomotroon.dbobjects.Projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des projets.
 */
public class ProjectServices
{
    public static final String SQL_LISTE_PROJETS = "SELECT id, libelle, date_debut, date_fin FROM projet";
    public static final String SQL_CREATE_PROJET = "INSERT INTO projet(libelle, date_debut, date_fin) VALUES(?,?,?)";

    private Database mDatabase;

    /**
     * Constructeur.
     *
     * @param pDatabase La base de données à utiliser.
     */
    public ProjectServices(Database pDatabase)
    {
        mDatabase = pDatabase;
    }

    /**
     * Retourne la clé primaire de type "auto_increment" qui a été générée par la requête
     * précédente.
     *
     * @param pstmt Le statement sur lequel a été réalisée la requête SQL d'insertion.
     * @return La clé primaire générée par la requête.
     *
     * @throws SQLException Si aucune requête n'a été exécutée dans 'pstmt' ou bien si le statement
     *                      n'était pas configuré avec la constante
     *                      {@link PreparedStatement#RETURN_GENERATED_KEYS}.
     */
    private int getPrimaryKey(PreparedStatement pstmt) throws SQLException
    {
        int result = -1;
        try (ResultSet rs = pstmt.getGeneratedKeys())
        {
            rs.first();
            result = rs.getInt(1);
        }
        return result;
    }

    /**
     * Crée un nouveau projet.
     *
     * @param pProjet Le projet à créer.
     * @return Le projet créé en BDD, avec son nouvel identifiant.
     *
     * @throws SQLException En cas de problème de création.
     */
    public Projet create(Projet pProjet) throws SQLException
    {
        Projet newProjet = null;
        try (Connection connection = mDatabase.getConnection())
        {
            PreparedStatement pstmt = connection.prepareStatement(SQL_CREATE_PROJET, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pProjet.libelle());
            pstmt.setDate(2, pProjet.dateDebut());
            pstmt.setDate(3, pProjet.dateFin());
            pstmt.executeUpdate();
            int idProjet = getPrimaryKey(pstmt);
            newProjet = new Projet(idProjet, pProjet.libelle(), pProjet.dateDebut(), pProjet.dateFin());
            pstmt.close();
        }
        return newProjet;
    }

    /**
     * Récupère la liste de tous les projets.
     */
    public List<Projet> getProjets()
    {
        List<Projet> result = new ArrayList<>();

        try (Connection connection = mDatabase.getConnection())
        {
            PreparedStatement pstmt = connection.prepareStatement(SQL_LISTE_PROJETS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                result.add(new Projet(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4)));
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
