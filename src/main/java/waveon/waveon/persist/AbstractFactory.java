package waveon.waveon.persist;

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
     *
     * @return OrdUserDAO
     */
    public abstract OrdUserDAO createOrdUserDAO();

    /**
     *
     * @return ArtistDAO
     */
    public abstract ArtistDAO createArtistDAO();

    public abstract ConversationDAO createConversationDAO();

}