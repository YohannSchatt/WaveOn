package waveon.waveon.persist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Artist;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


public class ArtistDAOPGTest {

    private ArtistDAOPG artistDAOPG;

    @BeforeEach
    public void setUp() throws SQLException {
        artistDAOPG = new ArtistDAOPG();
    }

    @Test
    public void testGetArtistByEmailSuccess() throws SQLException {
        Artist artist = artistDAOPG.getArtistByEmail("taylor.swift@example.com");

        assertNotNull(artist);

        // Actual data from the database
        assertEquals(2, artist.getId());
        assertEquals("Taylor Swift", artist.getUsername());
        assertEquals("taylor.swift@example.com", artist.getEmail());
        assertEquals("swift", artist.getPassword());
    }

    @Test
    public void testGetArtistByEmailNotFound() throws SQLException {
        Artist artist = artistDAOPG.getArtistByEmail("nonexistent@example.com");

        assertNull(artist);
    }

}