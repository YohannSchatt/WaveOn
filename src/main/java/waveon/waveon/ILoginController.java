package waveon.waveon;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * 
 */
public interface ILoginController {

    /**
     * 
     */
    public TextField usernameField = null;

    /**
     * 
     */
    public TextField passwordField = null;

    /**
     * @return
     */
    public abstract void Login();

}