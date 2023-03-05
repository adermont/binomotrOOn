package fr.simplon.binomotroon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe permettant de se connecter à une base de données.
 */
public abstract class Database
{
    protected String user;
    protected String password;
    protected String url;
    protected String driver;

    /**
     * Constructeur.
     *
     * @param pUser     Le login de l'utilisateur.
     * @param pPassword Le mot de passe utilisateur.
     * @param pUrl      L'URL de la base de données.
     * @param pDriver   Le driver à utiliser pour se connecter.
     */
    public Database(String pUser, String pPassword, String pUrl, String pDriver)
    {
        user = pUser;
        password = pPassword;
        url = pUrl;
        driver = pDriver;
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Impossible de trouver le Driver '" + driver + "'", e);
        }
    }

    /**
     * @return Une connexion à la base de données.
     *
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(this.url, user, password);
    }

    /**
     * @return Une connexion à la base de données.
     * @param user Le login à utiliser pour se connecter à la BDD.
     * @param password Le password correspondant au login.
     * @throws SQLException
     */
    public Connection getConnection(String user, String password) throws SQLException
    {
        return DriverManager.getConnection(this.url, user, password);
    }
}
