package waveon.waveon.persist;



public class PGFactory extends AbstractFactory {

    /**
     * Default constructor
     */
    public PGFactory() {}

    public OrdUserDAOPG createOrdUserDAO() {
        return new OrdUserDAOPG();
    }

    public ArtistDAOPG createArtistDAO() {
        return new ArtistDAOPG();
    }
}