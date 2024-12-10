package waveon.waveon.persist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.OrdUser;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


public class OrdUserDAOPGTest {

    private OrdUserDAOPG ordUserDAOPG;

    @BeforeEach
    public void setUp() throws SQLException {
        ordUserDAOPG = new OrdUserDAOPG() ;
    }

    @Test
    public void testGetUserByEmailSuccess() throws SQLException {
        OrdUser user = ordUserDAOPG.getUserByEmail("alice@example.com");

        assertNotNull(user);

        // Actual data from the database
        assertEquals(3, user.getId());
        assertEquals("Alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("123", user.getPassword());
    }

    @Test
    public void testGetUserByEmailNotFound() throws SQLException {
        OrdUser user = ordUserDAOPG.getUserByEmail("nonexistent@example.com");

        assertNull(user);
    }

}
