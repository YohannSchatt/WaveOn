package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;

import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;

import java.util.*;


public class SearchFacade {

    private AbstractFactory factory;
    private MusicDAO MusicDAO;
    private static ArrayList<Music> currentMusicSearch = new ArrayList<>();
    private static ArrayList<Artist> currentArtistSearch = new ArrayList<>();


    public SearchFacade() {
        factory = AbstractFactory.getInstance();
        assert factory != null;
        MusicDAO = factory.createMusicDAO();
    }


    public void searchMusic(String search) {
        ArrayList<Music> allMusic = MusicDAO.getAllMusics();
        ArrayList<Music> result = new ArrayList<>();
        for (Music music : allMusic) {
            if (music.getName().contains(search)) {
                result.add(music);
            }
        }
        currentMusicSearch = result;
    }

    public void searchArtist(String search) {
        // TODO implement here
    }

    private void useFilterMusic(FilterOption filter) {

    }

    public ArrayList<Music> getCurrentMusicSearch() {
        return currentMusicSearch;
    }
}