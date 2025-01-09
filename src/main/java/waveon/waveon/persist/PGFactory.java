package waveon.waveon.persist;

public class PGFactory extends AbstractFactory {

    public PGFactory() {}

    public OrdUserDAOPG createOrdUserDAO() {
        return new OrdUserDAOPG();
    }

    public ArtistDAOPG createArtistDAO() {
        return new ArtistDAOPG();
    }

    public MusicDAOPG createMusicDAO() { return new MusicDAOPG();}

    public NotificationDAOPG createNotificationDAO() { return new NotificationDAOPG(); }
}