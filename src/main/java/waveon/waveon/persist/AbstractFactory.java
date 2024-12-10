package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;

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
    private static AbstractFactory Instance;

    /**
     * 
     */
    public static AbstractFactory getInstance() {
        // TODO implement here
        return null;
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