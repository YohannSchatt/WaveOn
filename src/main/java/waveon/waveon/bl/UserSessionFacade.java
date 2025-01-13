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
    private final OrdUserDAO userDAO;
    private final ArtistDAO artistDAO;
    private static IUser currentUser;

    private UserSessionFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        userDAO = factory.createOrdUserDAO();
        artistDAO = factory.createArtistDAO();
    }

    // Function that logs in a user based on their email and whether they are an artist or not
    public boolean login(String email, String password, boolean isArtist) {
        currentUser = userDAO.getUserByEmail(email);
        if(isArtist) {
            currentUser = artistDAO.getArtistByEmail(email);
        } else{
            currentUser = userDAO.getUserByEmail(email);
        }
        return checkCredentials(email, password);
    }

    // Function that registers a user based on their email and whether they are an artist or not
    public boolean register(String email, String username,String password, boolean isArtist) {
        try {
            if(isArtist && artistDAO.getArtistByEmail(email) == null) {
                System.out.println("Ajout de l'artiste...");
                artistDAO.addArtist(email,username,password);
                return true;
            } else if (!isArtist && userDAO.getUserByEmail(email) == null) {
                System.out.println("Ajout de l'utilisateur...");
                userDAO.addUser(email,username,password);
                return true;
            } else {
                System.out.println("Ajout de l'utilisateur échoué : email déjà utilisé.");
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e);
            return false;
        }
    }

    // Function that checks if the email and password match the current user
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

    // Returns the current connected user
    public static IUser getCurrentUser() {
        return currentUser;
    }

    // Change the current connected user
    public static void setCurrentUser(IUser user) {
        currentUser = user;
    }

    // Logs out the current user
    public void logout() {
        currentUser = null;
    }

    // Returns true if the current user is an artist
    public boolean isArtist() {
        return currentUser.isArtist();
    }

    // Save changes to the user's profile
    public boolean enregistrerModification(String username, String email, String password) {
        System.out.println("Enregistrement des modifications...");
        System.out.println("Username : " + username);
        System.out.println("Email : " + email);
        System.out.println("Password : " + password);

        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            return false;
        }

        if (currentUser != null  && (!username.equals(currentUser.getUsername()) || !email.equals(currentUser.getEmail()) || !password.isEmpty())) {
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

    // Add a follow to the current user
    public void addFollow(int id) {
        if (currentUser != null) {
            userDAO.addFollow(id, currentUser.getId());
        }
    }

    // Remove a follow from the current user
    public void removeFollow(int id) {
        if (currentUser != null) {
            userDAO.removeFollow(id, currentUser.getId());
        }
    }
}