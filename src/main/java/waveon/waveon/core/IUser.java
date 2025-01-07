package waveon.waveon.core;

/**
 * 
 */
public interface IUser {

    /**
     * 
     */
    public int id = 0;

    /**
     * 
     */
    public String username = "";

    /**
     * 
     */
    public String email = "";

    /**
     *
     */
    public String password = "";


    public int getId();
    public void setId(int id);
    public String getUsername();
    public void setUsername(String username);
    public String getEmail();
    public void setEmail(String email);
    public String getPassword();
    public void setPassword(String password);
    public boolean isArtist();
}