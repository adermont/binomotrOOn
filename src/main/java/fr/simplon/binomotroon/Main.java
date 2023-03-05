package fr.simplon.binomotroon;

import fr.simplon.binomotroon.db.Database;
import fr.simplon.binomotroon.db.MysqlDatabase;

import java.sql.*;

public class Main
{
    public static void main(String[] args)
    {
        Database db = MysqlDatabase.getInstance("root", "root", "localhost", 3306, "accenture");
        BinomotrOOn binomotron = new BinomotrOOn(db);

        // Authentification de l'utilisateur
        binomotron.authenticateUser("adermont");

        // Affichage du menu principal
        binomotron.mainMenu();
    }
}
