package waveon.waveon.persist;



public class PGFactory extends AbstractFactory {

    /**
     * Default constructor
     */
    public PGFactory() {}

    /**
     *
     * @return OrdUserDAO
     */
    public OrdUserDAOPG createOrdUserDAO() {
        return new OrdUserDAOPG();
    }

    /**
     *
     * @return ArtistDAO
     */
    public ArtistDAOPG createArtistDAO() {
        return new ArtistDAOPG();
    }
}