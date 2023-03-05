package fr.simplon.binomotroon.db;

public class MysqlDatabase extends Database
{
    private static MysqlDatabase singleton;

    /**
     * Constructeur.
     *
     * @param user     Le login de l'utilisateur.
     * @param password Le mot de passe utilisateur.
     * @param host     Nom de la machine où se trouve la base de données.
     * @param port     Le numéro de port de la base MySQL.
     * @param db       Nom de la base de données.
     */
    private MysqlDatabase(String user, String password, String host, int port, String db)
    {
        super(user, password, String.format("jdbc:mysql://%s:%d/%s", host, port, db), "com.mysql.cj.jdbc.Driver");
    }

    /**
     * Retourne le singleton MysqlDatabase permettant d'accéder à une base MySQL.
     *
     * @param user     Le login de l'utilisateur.
     * @param password Le mot de passe utilisateur.
     * @param host     Nom de la machine où se trouve la base de données.
     * @param port     Le numéro de port de la base MySQL.
     * @param db       Nom de la base de données.
     * @return le singleton MysqlDatabase permettant d'accéder à une base MySQL.
     */
    public static MysqlDatabase getInstance(String user, String password, String host, int port, String db)
    {
        if (singleton == null)
        {
            singleton = new MysqlDatabase(user, password, host, port, db);
        }
        return singleton;
    }
}
