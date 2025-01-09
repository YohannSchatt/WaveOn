package waveon.waveon.persist;

public abstract class AbstractFactory {

    public AbstractFactory() {
    }

    private static AbstractFactory Instance;

    public static AbstractFactory getInstance(){
        if (Instance == null) {
            Instance = new PGFactory();
        }
        return Instance;
    }

    public static void setInstance(AbstractFactory factory){
        Instance = factory;
    }

    public abstract OrdUserDAO createOrdUserDAO();

    public abstract ArtistDAO createArtistDAO();

    public abstract MusicDAO createMusicDAO();

    public abstract NotificationDAO createNotificationDAO();
}