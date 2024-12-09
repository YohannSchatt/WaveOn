package waveon.waveon;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class AbstractFactory {

    /**
     * Default constructor
     */
    public AbstractFactory() {
    }

    /**
     * 
     */
    private AbstractFactory Instance;

    /**
     * 
     */
    public void getInstance() {
        // TODO implement here
    }

    /**
     * @return
     */
    public abstract OrdUserDAOPG createUserDAO();

    /**
     * @return
     */
    public abstract ArtistDAO createArtistDAO();

    /**
     * @param String username 
     * @param int subscriptionLevel 
     * @return
     */
    public OrdUser createOrdinaryUser(String username, int subscriptionLevel) {
        // TODO implement here
        return null;
    }

}