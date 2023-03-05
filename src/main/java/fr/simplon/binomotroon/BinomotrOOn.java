package fr.simplon.binomotroon;

import fr.simplon.binomotroon.control.LoginController;
import fr.simplon.binomotroon.control.MainMenuController;
import fr.simplon.binomotroon.dbobjects.User;
import fr.simplon.binomotroon.db.Database;
import fr.simplon.binomotroon.dbservices.GroupServices;
import fr.simplon.binomotroon.dbservices.ProjectServices;
import fr.simplon.binomotroon.dbservices.UserServices;

public class BinomotrOOn
{
    private User               mAuthenticatedUser;
    private LoginController    mLoginController;
    private MainMenuController mMainController;

    public BinomotrOOn(Database db)
    {
        UserServices us = new UserServices(db);
        ProjectServices ps = new ProjectServices(db);
        GroupServices gs = new GroupServices(db);
        mLoginController = new LoginController(us);
        mMainController = new MainMenuController(ps, gs);
    }

    public void authenticateUser(String defaultUser)
    {
        System.out.println("***************** Authentification *******************");
        mAuthenticatedUser = mLoginController.authenticateUser(defaultUser);
        System.out.println("Utilisateur authentifié : " + mAuthenticatedUser);
        System.out.println("******************************************************");
    }

    public void mainMenu(){
        System.out.println();
        mMainController.mainMenu(mAuthenticatedUser);
        System.out.println("Sortie...");
    }

    public User getAuthenticatedUser()
    {
        return mAuthenticatedUser;
    }
}
