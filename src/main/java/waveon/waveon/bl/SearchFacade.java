package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Music;

import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.MusicDAO;

import java.util.*;


public class SearchFacade {

    private AbstractFactory factory;
    private MusicDAO MusicDAO;
    private ArtistDAO ArtistDAO;
    private static ArrayList<Music> currentMusicSearch = new ArrayList<>();
    private static ArrayList<Artist> currentArtistSearch = new ArrayList<>();

    public static Artist profilePageInfo;


    public SearchFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        MusicDAO = factory.createMusicDAO();
        ArtistDAO = factory.createArtistDAO();
    }


    public void searchMusic(String search) {
        currentMusicSearch = MusicDAO.getMusicsByName(search);
    }

    public void searchArtist(String search) {
        currentArtistSearch = ArtistDAO.getArtistsByName(search);
    }

    private void useFilterMusic(FilterOption filter) {

    }

    public ArrayList<Music> getCurrentMusicSearch() {
        return currentMusicSearch;
    }

    public ArrayList<Artist> getCurrentArtistSearch() { return currentArtistSearch; }

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