package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFacadeTest {
    private UserSessionFacade loginFacade;

    @BeforeEach
    public void setUp() {
        loginFacade = UserSessionFacade.getInstance();
    }

    @Test
    public void testLoginSuccessful() {
        loginFacade.login("john.doe@example.com", "John123");
        assertTrue(loginFacade.checkCredentials("test@example.com", "password"));
    }

    @Test
    public void testLoginFailed() {
        loginFacade.login("wrong@example.com", "kjfjksnf");
        assertFalse(loginFacade.checkCredentials("wrong@example.com", "password"));
    }

    @Test
    public void testCheckCredentials() {
        loginFacade.login("jane.moderator@example.com", "JaneModo123!");
        assertTrue(loginFacade.checkCredentials("test@example.com", "password"));
    }
}