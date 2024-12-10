package waveon.waveon.bl;

import waveon.waveon.core.IUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;


public class LoginFacade {
    protected OrdUserDAO userDAO;
    protected ArtistDAO artistDAO;

    private IUser currentUser;


    /**
     * Default constructor
     */
    public LoginFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();
    }


    /**
     * @param email
     */
    public void login(String email, boolean isArtist) {

        // Utilisez la valeur de isArtist selon vos besoins
        if (isArtist) {
            //System.out.println("L'utilisateur est un artiste.");
            this.currentUser = artistDAO.getArtistByEmail(email);
        } else {
            //System.out.println("L'utilisateur n'est pas un artiste.");
            this.currentUser = userDAO.getUserByEmail(email);
        }
    }

    /**
     * @param email
     * @param password
     * @return Boolean
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

    /**
     * @return IUser
     */
    public IUser getCurrentUser() {
        return currentUser;
    }

}