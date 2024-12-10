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
     * @param  email
     */
    public void login(String  email) {
        this.currentUser = userDAO.getUserByEmail(email);
    }

    /**
     * @return
     */
    public boolean checkCredentials(String email, String password) {
        if (currentUser != null) {
            if (this.currentUser.getPassword().equals(password) && this.currentUser.getEmail().equals(email)) {
                return true;
            } else {
                System.out.println("Login échoué : identifiants incorrects.");
                return false;
            }
        }
        return false;
    }

}