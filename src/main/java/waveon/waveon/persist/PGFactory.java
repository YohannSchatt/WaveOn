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
    public OrdUserDAOPG createOrdUserDAO() {
        return new OrdUserDAOPG();
    }

    /**
     * @return
     */
    public ArtistDAOPG createArtistDAO() {
        return new ArtistDAOPG();
    }
}