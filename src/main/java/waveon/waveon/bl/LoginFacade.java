package waveon.waveon.bl;

import waveon.waveon.core.IUser;
import waveon.waveon.core.OrdUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;

public class LoginFacade {
    private AbstractFactory factory;
    private OrdUserDAO userDAO;
    private ArtistDAO artistDAO;


    private static OrdUser faux = new OrdUser(0, "coco", "coco@gmail.com","21032004");

    private static IUser currentUser = faux;
    public LoginFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();
    }

    public void login(String email, boolean isArtist) {

        // Utilisez la valeur de isArtist selon vos besoins
        if (isArtist) {
            System.out.println("L'utilisateur est un artiste.");
            currentUser = artistDAO.getArtistByEmail(email);
        } else {
            System.out.println("L'utilisateur n'est pas un artiste.");
            currentUser = userDAO.getUserByEmail(email);
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