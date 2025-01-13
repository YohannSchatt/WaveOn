package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.PlaylistDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicFacadePlaylistTest {

    private MusicFacade musicFacade;
    private PlaylistDAO mockPlaylistDAO;

    @BeforeEach
    public void setUp() {
        mockPlaylistDAO = mock(PlaylistDAO.class);
        MusicDAO mockMusicDAO = mock(MusicDAO.class);
        AbstractFactory factory = mock(AbstractFactory.class);
        when(factory.createPlaylistDAO()).thenReturn(mockPlaylistDAO);
        when(factory.createMusicDAO()).thenReturn(mockMusicDAO);
        AbstractFactory.setInstance(factory);

        musicFacade = new MusicFacade();
    }

    @Test
    public void testCreatePlaylist() {
        when(mockPlaylistDAO.createPlaylist("My Playlist", 1)).thenReturn(true);
        boolean result = musicFacade.createPlaylist("My Playlist", 1);
        assertTrue(result);
        verify(mockPlaylistDAO, times(1)).createPlaylist("My Playlist", 1);
    }

    @Test
    public void testCreatePlaylistWithOutCorrectId() {
        when(mockPlaylistDAO.createPlaylist("My Playlist", -1)).thenReturn(false);
        boolean result = musicFacade.createPlaylist("My Playlist", -1);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).createPlaylist("My Playlist", -1);
    }

    @Test
    public  void testCreatePlaylistWithOutName() {
        when(mockPlaylistDAO.createPlaylist("", 1)).thenReturn(false);
        boolean result = musicFacade.createPlaylist("", 1);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).createPlaylist("", 1);
    }

    @Test
    public void testGetPlaylistsByUserId() {
        ArrayList<Playlist> mockPlaylists = new ArrayList<>();
        mockPlaylists.add(new Playlist(1, "Playlist 1", 1));
        mockPlaylists.add(new Playlist(2, "Playlist 2", 1));

        when(mockPlaylistDAO.getPlaylistsByUserId(1)).thenReturn(mockPlaylists);

        ArrayList<Playlist> playlists = musicFacade.getPlaylistsByUserId(1);
        assertEquals(2, playlists.size());
        assertEquals("Playlist 1", playlists.get(0).getName());
        assertEquals("Playlist 2", playlists.get(1).getName());
    }

    @Test
    public void testGetPlaylistsByUseridWithOutCorrectId() {
        ArrayList<Playlist> mockPlaylists = new ArrayList<>();

        when(mockPlaylistDAO.getPlaylistsByUserId(-1)).thenReturn(mockPlaylists);

        ArrayList<Playlist> playlists = musicFacade.getPlaylistsByUserId(-1);
        assertEquals(0, playlists.size());
    }

    @Test
    public void testAddMusicToPlaylist() {
        Music music = new Music(1, "Song", null, null, 1, "Artist", null, 0);
        Playlist playlist = new Playlist(1, "Playlist", 1);

        when(mockPlaylistDAO.addMusicToPlaylist(music.getId(), playlist.getId())).thenReturn(true);

        boolean result = musicFacade.addMusicToPlaylist(music, playlist);
        assertTrue(result);
        verify(mockPlaylistDAO, times(1)).addMusicToPlaylist(music.getId(), playlist.getId());
    }

    @Test
    public void testAddMusicToPlaylistWithoutCorrectIdMusic(){
        Music music = new Music(-1, "Song", null, null, 1, "Artist", null, 0);
        Playlist playlist = new Playlist(1, "Playlist", 1);

        when(mockPlaylistDAO.addMusicToPlaylist(music.getId(), playlist.getId())).thenReturn(false);

        boolean result = musicFacade.addMusicToPlaylist(music, playlist);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).addMusicToPlaylist(music.getId(), playlist.getId());
    }

    @Test
    public void testAddMusicToPlaylistWithoutCorrectIdPlaylist(){
        Music music = new Music(1, "Song", null, null, 1, "Artist", null, 0);
        Playlist playlist = new Playlist(-1, "Playlist", 1);

        when(mockPlaylistDAO.addMusicToPlaylist(music.getId(), playlist.getId())).thenReturn(false);

        boolean result = musicFacade.addMusicToPlaylist(music, playlist);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).addMusicToPlaylist(music.getId(), playlist.getId());
    }

    @Test
    public void testGetMusicByPlaylistId() throws Exception {
        ArrayList<Music> mockMusicList = new ArrayList<>();
        mockMusicList.add(new Music(1, "Song 1", null, null, 1, "Artist 1", null, 0));
        mockMusicList.add(new Music(2, "Song 2", null, null, 2, "Artist 2", null, 0));

        when(mockPlaylistDAO.getMusicByPlaylistId(1)).thenReturn(mockMusicList);

        ArrayList<Music> musicList = musicFacade.getMusicsByPlaylistId(1);
        assertEquals(2, musicList.size());
        assertEquals("Song 1", musicList.get(0).getTitle());
        assertEquals("Song 2", musicList.get(1).getTitle());
    }

    @Test
    public void testGetMusicByPlaylistIdWithoutCorrectId() throws Exception {
        ArrayList<Music> mockMusicList = new ArrayList<>();

        when(mockPlaylistDAO.getMusicByPlaylistId(-1)).thenReturn(mockMusicList);

        ArrayList<Music> musicList = musicFacade.getMusicsByPlaylistId(-1);
        assertEquals(0, musicList.size());
    }

    @Test
    public void testDeletePlaylist() {
        when(mockPlaylistDAO.deletePlaylist(1)).thenReturn(true);

        boolean result = musicFacade.deletePlaylist(1);
        assertTrue(result);
        verify(mockPlaylistDAO, times(1)).deletePlaylist(1);
    }

    @Test
    public void testDeletePlaylistWithoutCorrectId() {
        when(mockPlaylistDAO.deletePlaylist(-1)).thenReturn(false);

        boolean result = musicFacade.deletePlaylist(-1);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).deletePlaylist(-1);
    }

    @Test
    public void testDeleteMusicFromPlaylist() {
        when(mockPlaylistDAO.deleteMusicFromPlaylist(1, 1)).thenReturn(true);

        boolean result = musicFacade.deleteMusicFromPlaylist(1, 1);
        assertTrue(result);
        verify(mockPlaylistDAO, times(1)).deleteMusicFromPlaylist(1, 1);
    }

    @Test
    public void testDeleteMusicFromPlaylistWithoutCorrectIdMusic() {
        when(mockPlaylistDAO.deleteMusicFromPlaylist(-1, 1)).thenReturn(false);

        boolean result = musicFacade.deleteMusicFromPlaylist(-1, 1);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).deleteMusicFromPlaylist(-1, 1);
    }

    @Test
    public void testDeleteMusicFromPlaylistWithoutCorrectIdPlaylist() {
        when(mockPlaylistDAO.deleteMusicFromPlaylist(1, -1)).thenReturn(false);

        boolean result = musicFacade.deleteMusicFromPlaylist(1, -1);
        assertFalse(result);
        verify(mockPlaylistDAO, times(1)).deleteMusicFromPlaylist(1, -1);
    }
}