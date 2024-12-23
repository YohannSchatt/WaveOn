package waveon.waveon.bl;

import waveon.waveon.core.IUser;
import waveon.waveon.core.OrdUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;

//Singleton pour la gestion de la connexion
//afin de pouvoir l'utiliser plus facilement entre les pages
public class LoginFacade {

    static LoginFacade instance;

    public static LoginFacade getInstance() {
        if (instance == null) {
            instance = new LoginFacade();
        }
        return instance;
    }

    private AbstractFactory factory;
    private OrdUserDAO userDAO;
    private ArtistDAO artistDAO;
    private static IUser currentUser;

    private LoginFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();
    }

    public boolean login(String email, String password) {

        currentUser = userDAO.getUserByEmail(email);
        if (currentUser == null) {
            currentUser = artistDAO.getArtistByEmail(email);
            if(currentUser == null) {
                System.out.println("L'utilisateur n'existe pas.");
                return false;
            }
            else {
                System.out.println("L'utilisateur est un artiste.");
                return checkCredentials(email, password);
            }
        } else {
            System.out.println("L'utilisateur n'est pas un artiste.");
            return checkCredentials(email, password);
        }
    }

    public boolean checkCredentials(String email, String password) {
        if (currentUser != null) {
            if (currentUser.getPassword().equals(password) && currentUser.getEmail().equals(email)) {
                return true;
            } else {
                System.out.println("Login échoué : identifiants incorrects.");
                return false;
            }
        }
        return false;
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}