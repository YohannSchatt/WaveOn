package waveon.waveon.persist;

public abstract class AbstractFactory {

    /**
     * Default constructor
     */
    public AbstractFactory() {
    }

    private static AbstractFactory Instance;

    public static AbstractFactory getInstance(){
        if (Instance == null) {
            Instance = new PGFactory();
        }
        return Instance;
    }

    public abstract OrdUserDAO createOrdUserDAO();

    public abstract ArtistDAO createArtistDAO();

}