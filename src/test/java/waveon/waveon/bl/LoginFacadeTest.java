package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFacadeTest {
    private LoginFacade loginFacade;

    @BeforeEach
    public void setUp() {
        loginFacade = new LoginFacade();
    }

    @Test
    public void testLoginSuccessful() {
        loginFacade.login("test@example.com", true);
        assertTrue(loginFacade.checkCredentials("test@example.com", "password"));
    }

    @Test
    public void testLoginFailed() {
        loginFacade.login("wrong@example.com", false);
        assertFalse(loginFacade.checkCredentials("wrong@example.com", "password"));
    }

    @Test
    public void testCheckCredentials() {
        loginFacade.login("test@example.com", true);
        assertTrue(loginFacade.checkCredentials("test@example.com", "password"));
    }
}