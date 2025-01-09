package waveon.waveon.bl;

import waveon.waveon.core.IUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;

import java.sql.SQLException;

//Singleton pour la gestion de la connexion
//afin de pouvoir l'utiliser plus facilement entre les pages
public class UserSessionFacade {

    static UserSessionFacade instance;

    public static UserSessionFacade getInstance() {
        if (instance == null) {
            instance = new UserSessionFacade();
        }
        return instance;
    }

    private AbstractFactory factory;
    private OrdUserDAO userDAO;
    private ArtistDAO artistDAO;
    private static IUser currentUser;

    private UserSessionFacade() {}

    public boolean login(String email, String password) {

        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();

        currentUser = userDAO.getUserByEmail(email);
        if (currentUser == null) {
            UserSessionFacade.setCurrentUser(artistDAO.getArtistByEmail(email));
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

    public boolean register(String email, String username,String password) {

        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();

        if (userDAO.getUserByEmail(email) != null || artistDAO.getArtistByEmail(email) != null) {
            return false;
        } else {
            try {
                userDAO.addUser(email,username,password);
                return true;
            }
            catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e);
                return false;
            }
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

    public static IUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(IUser user) {
        currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isArtist() {
        return currentUser.isArtist();
    }

    public boolean enregistrerModification(String username, String email, String password) {
        System.out.println("Enregistrement des modifications...");
        System.out.println("Username : " + username);
        System.out.println("Email : " + email);
        System.out.println("Password : " + password);

        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            return false;
        }

        if (currentUser != null  && (!username.equals(currentUser.getUsername()) || !email.equals(currentUser.getEmail()) || !password.isEmpty())) {
            System.out.println("COUCOU");
            factory = AbstractFactory.getInstance();
            assert factory != null;
            if(currentUser.isArtist()) {
                ArtistDAO artistDAO = factory.createArtistDAO();
                artistDAO.updateArtist(currentUser.getId(), username, email, password);
                updatePersonalInfo(username, email, password);
                return true;
            }
            else {
                OrdUserDAO userDAO = factory.createOrdUserDAO();
                userDAO.updateUser(currentUser.getId(), username, email, password);
                updatePersonalInfo(username, email, password);
                return true;
            }
        }
        return false;
    }

    private void updatePersonalInfo(String username, String email, String password) {
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        if (password != null && !password.trim().isEmpty()) {
            currentUser.setPassword(password);
        }
    }
}