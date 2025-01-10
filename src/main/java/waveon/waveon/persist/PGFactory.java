package waveon.waveon.persist;

public class PGFactory extends AbstractFactory {

    public PGFactory() {}

    @Override
    public OrdUserDAOPG createOrdUserDAO() {
        return new OrdUserDAOPG();
    }

    @Override
    public ArtistDAOPG createArtistDAO() {
        return new ArtistDAOPG();
    }

    @Override
    public MusicDAOPG createMusicDAO() {
        return new MusicDAOPG();
    }

    @Override
    public CommentsDAOPG createCommentsDAO() {
        return new CommentsDAOPG();
    }
}