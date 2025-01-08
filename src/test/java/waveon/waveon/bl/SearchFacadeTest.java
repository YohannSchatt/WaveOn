package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.MusicDAO;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchFacadeTest {

    private SearchFacade searchFacade;
    private MusicDAO mockMusicDAO;
    private ArtistDAO mockArtistDAO;

    @BeforeEach
    public void setUp() {
        mockMusicDAO = mock(MusicDAO.class);
        mockArtistDAO = mock(ArtistDAO.class);
        AbstractFactory mockFactory = mock(AbstractFactory.class);

        when(mockFactory.createMusicDAO()).thenReturn(mockMusicDAO);
        when(mockFactory.createArtistDAO()).thenReturn(mockArtistDAO);
        AbstractFactory.setInstance(mockFactory);

        searchFacade = new SearchFacade();
    }

    @Test
    public void testSearchMusic() {
        ArrayList<Music> mockMusicList = new ArrayList<>();
        mockMusicList.add(new Music(1, "title", null, null, 1, "artist", null, 0));
        when(mockMusicDAO.getMusicsByName("title")).thenReturn(mockMusicList);

        searchFacade.searchMusic("title");

        assertEquals(mockMusicList, searchFacade.getCurrentMusicSearch());
        verify(mockMusicDAO, times(1)).getMusicsByName("title");
    }

    @Test
    public void testSearchArtist() {
        ArrayList<Artist> mockArtistList = new ArrayList<>();
        mockArtistList.add(new Artist(1, "artist", "email", "password"));
        when(mockArtistDAO.getArtistsByName("artist")).thenReturn(mockArtistList);

        searchFacade.searchArtist("artist");

        assertEquals(mockArtistList, searchFacade.getCurrentArtistSearch());
        verify(mockArtistDAO, times(1)).getArtistsByName("artist");
    }

    @Test
    public void testSearchMusicEmptyResult() {
        when(mockMusicDAO.getMusicsByName("nonexistent")).thenReturn(new ArrayList<>());

        searchFacade.searchMusic("nonexistent");

        assertTrue(searchFacade.getCurrentMusicSearch().isEmpty());
        verify(mockMusicDAO, times(1)).getMusicsByName("nonexistent");
    }

    @Test
    public void testSearchArtistEmptyResult() {
        when(mockArtistDAO.getArtistsByName("nonexistent")).thenReturn(new ArrayList<>());

        searchFacade.searchArtist("nonexistent");

        assertTrue(searchFacade.getCurrentArtistSearch().isEmpty());
        verify(mockArtistDAO, times(1)).getArtistsByName("nonexistent");
    }

    @Test
    public void testSearchMusicNullResult() {
        ArrayList<Music> mockMusicList = new ArrayList<>();

      when(mockMusicDAO.getMusicsByName(null)).thenReturn(mockMusicList);

      searchFacade.searchMusic(null);

     assertTrue(searchFacade.getCurrentMusicSearch().isEmpty());
      verify(mockMusicDAO, times(1)).getMusicsByName(null);
    }

    @Test
    public void testSearchArtistNullResult() {
        ArrayList<Artist> mockArtistList = new ArrayList<>();
      when(mockArtistDAO.getArtistsByName(null)).thenReturn(mockArtistList);

       searchFacade.searchArtist(null);

       assertTrue(searchFacade.getCurrentArtistSearch().isEmpty());
       verify(mockArtistDAO, times(1)).getArtistsByName(null);
     }

     @Test
     public void testSearchMusicMultipleResults() {
        ArrayList<Music> mockMusicList = new ArrayList<>();
         mockMusicList.add(new Music(1, "title1", null, null, 1, "artist1", null, 0));
        mockMusicList.add(new Music(2, "title2", null, null, 2, "artist2", null, 0));
        when(mockMusicDAO.getMusicsByName("title")).thenReturn(mockMusicList);

    searchFacade.searchMusic("title");
   assertEquals(mockMusicList, searchFacade.getCurrentMusicSearch());
  verify(mockMusicDAO, times(1)).getMusicsByName("title");
 } //

    @Test
    public void testSearchArtistMultipleResults() {
        ArrayList<Artist> mockArtistList = new ArrayList<>();
        mockArtistList.add(new Artist(1, "artist1", "email1", "password1"));
        mockArtistList.add(new Artist(2, "artist2", "email2", "password2"));
        when(mockArtistDAO.getArtistsByName("artist")).thenReturn(mockArtistList);

        searchFacade.searchArtist("artist");

        assertEquals(mockArtistList, searchFacade.getCurrentArtistSearch());
        verify(mockArtistDAO, times(1)).getArtistsByName("artist");
    }

    @Test
    public void testSearchMusicExceptionHandling() {
        when(mockMusicDAO.getMusicsByName("title")).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> searchFacade.searchMusic("title"));
        verify(mockMusicDAO, times(1)).getMusicsByName("title");
    }

    @Test
    public void testSearchArtistExceptionHandling() {
        when(mockArtistDAO.getArtistsByName("artist")).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> searchFacade.searchArtist("artist"));
        verify(mockArtistDAO, times(1)).getArtistsByName("artist");
    }

    @Test
    public void testSearchMusicCaseInsensitive() {
        ArrayList<Music> mockMusicList = new ArrayList<>();
        mockMusicList.add(new Music(1, "Title", null, null, 1, "artist", null, 0));
        when(mockMusicDAO.getMusicsByName("title")).thenReturn(mockMusicList);

        searchFacade.searchMusic("title");

        assertEquals(mockMusicList, searchFacade.getCurrentMusicSearch());
        verify(mockMusicDAO, times(1)).getMusicsByName("title");
    }

    @Test
    public void testSearchArtistCaseInsensitive() {
        ArrayList<Artist> mockArtistList = new ArrayList<>();
        mockArtistList.add(new Artist(1, "Artist", "email", "password"));
        when(mockArtistDAO.getArtistsByName("artist")).thenReturn(mockArtistList);

        searchFacade.searchArtist("artist");

        assertEquals(mockArtistList, searchFacade.getCurrentArtistSearch());
        verify(mockArtistDAO, times(1)).getArtistsByName("artist");
    }
}