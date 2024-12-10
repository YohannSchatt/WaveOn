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
    public static AbstractFactory getInstance(){
        if (Instance == null) {
            Instance = new PGFactory();
        }
        return Instance;
    }

    /**
     * @return
     */
    public abstract OrdUserDAO createUserDAO(String email);

    /**
     * @return
     */
    public abstract ArtistDAO createArtistDAO(String email);


}