package fr.simplon.binomotroon.control;

import fr.simplon.binomotroon.dbobjects.User;
import fr.simplon.binomotroon.dbservices.UserServices;

public class LoginController
{
    private UserServices mUserServices;

    public LoginController(UserServices userServices)
    {
        mUserServices = userServices;
    }

    /**
     * Cette méthode lance la procédure d'authentification de l'utilisateur. Ellse pose la question
     * du login et de mot de passe puis vérifie les infos en base de données.
     *
     * @return L'utilisateur authentifié ou <code>null</code> si l'utilisateur n'est pas
     *         authentifié.
     */
    public User authenticateUser(String defaultUser)
    {
        Questions q = new Questions();
        String login = q.demanderLogin(defaultUser);
        String password = q.demanderMotDePasse();
        User user = mUserServices.getUser(login, password);
        return user;
    }
}
