package waveon.waveon.bl;

import waveon.waveon.core.OrdUser;
import waveon.waveon.core.User;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.OrdUserDAO;
import waveon.waveon.persist.PGFactory;

/**
 * 
 */
public class LoginFacade {
    private AbstractFactory factory;
    private OrdUserDAO userDAO; // DAO pour accéder aux données utilisateur
    private OrdUser currentUser;     // Utilisateur actuellement connecté

    /**
     * Default constructor
     */
    public LoginFacade() {
       factory = AbstractFactory.getInstance();
       assert factory != null;
       userDAO = factory.createUserDAO();
    }

    /**
     * 
     */
    public User loginService;

    /**
     * @param   email
     * @param  password
     * @return
     */
    public boolean login(String  email, String password) {
        OrdUser user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        } else {
            System.out.println("Login échoué : identifiants incorrects.");
            return false;
        }
    }

    /**
     * @return
     */
    public boolean checkCredentials() {
        return false;
    }

}