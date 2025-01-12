package waveon.waveon.bl;

import waveon.waveon.core.IUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;

public class LoginFacade {
    private AbstractFactory factory;
    OrdUserDAO userDAO;
    ArtistDAO artistDAO;
    private static IUser currentUser;

    public LoginFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();
    }

    // Function that logs in a user based on their email and whether they are an artist or not
    // CurrrentUser is set to the user that is logged in
    public void login(String email, boolean isArtist) {

        if (isArtist) {
            System.out.println("L'utilisateur est un artiste.");
            currentUser = artistDAO.getArtistByEmail(email);
        } else {
            System.out.println("L'utilisateur n'est pas un artiste.");
            currentUser = userDAO.getUserByEmail(email);
        }
    }

    // Function that checks if the email and password match the current user
    // returns true if they match, false otherwise
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

    // Function that returns the current connected user
    public static IUser getCurrentUser() {
        return currentUser;
    }

    // Function that logs out the current user
    public void logout() {
        currentUser = null;
    }
}