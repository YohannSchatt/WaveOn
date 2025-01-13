package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;

import waveon.waveon.core.OrdUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.OrdUserDAO;

import java.util.*;


public class SearchFacade {

    private final MusicDAO MusicDAO;
    private final ArtistDAO ArtistDAO;
    private final OrdUserDAO OrdUserDAO;
    private static ArrayList<Music> currentMusicSearch = new ArrayList<>();
    private static ArrayList<Artist> currentArtistSearch = new ArrayList<>();
    private static ArrayList<OrdUser> currentOrdUserSearch = new ArrayList<>();

    public static Artist profilePageInfo;


    public SearchFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        MusicDAO = factory.createMusicDAO();
        ArtistDAO = factory.createArtistDAO();
        OrdUserDAO = factory.createOrdUserDAO();
    }

    // Fetches the matching musics with the search string
    public void searchMusic(String search) {
        currentMusicSearch = MusicDAO.getMusicsByName(search);
    }

    // Fetches the matching artists with the search string
    public void searchArtist(String search) {
        currentArtistSearch = ArtistDAO.getArtistsByName(search);
    }

    // Returns the current music search results
    public ArrayList<Music> getCurrentMusicSearch() { return currentMusicSearch; }

    // Returns the current artist search results
    public ArrayList<Artist> getCurrentArtistSearch() { return currentArtistSearch; }

    public ArrayList<OrdUser> getCurrentOrdUserSearch() { return currentOrdUserSearch; }

    public static void setCurrentOrdUserSearch(ArrayList<OrdUser> currentOrdUserSearch) {SearchFacade.currentOrdUserSearch = currentOrdUserSearch; }

    public void searchOrdUser(String search) {setCurrentOrdUserSearch(OrdUserDAO.getUserByName(search));}

    public static void setProfilePageInfo(Artist profilePageInfo) {
        SearchFacade.profilePageInfo = profilePageInfo;
    }

    public static Artist getProfilePageInfo() {
        return profilePageInfo;
    }

    public void getArtistById(int ArtistId){
        profilePageInfo = ArtistDAO.getArtistById(ArtistId);
    }

    public boolean getAllInfoArtistById(int ArtistId){
        profilePageInfo = ArtistDAO.getArtistById(ArtistId);
        profilePageInfo.setSubscribers(ArtistDAO.getSubscribers(ArtistId));
        profilePageInfo.setMusics(MusicDAO.getListMusicsByidArtist(ArtistId));
        return profilePageInfo != null;
    }

}