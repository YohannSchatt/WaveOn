package waveon.waveon.persist;


/**
 * 
 */
public class PGFactory extends AbstractFactory {

    /**
     * Default constructor
     */
    public PGFactory() {}


    /**
     * @return
     */
    public OrdUserDAOPG createUserDAO(String email) {
        return new OrdUserDAOPG();
    }

    /**
     * @return
     */
    public ArtistDAOPG createArtistDAO(String email) {
        return new ArtistDAOPG();
    }
}