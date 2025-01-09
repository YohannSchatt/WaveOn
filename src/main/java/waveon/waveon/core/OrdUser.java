package waveon.waveon.core;

/**
 * 
 */
public class OrdUser implements IUser {

    /**
     *
     */
    public int id;

    /**
     *
     */public String username = "";

    /**
     *
     */
    public String email = "";

    /**
     *
     */
    public String password = "";

    /**
     * Default constructor
     */
    public OrdUser(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public OrdUser(int id ) {
        this.id = id;
    }
    /**
     * 
     */
    public Boolean IsModerator = false;

    /**
     *
     * @return int id
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     *
     * @param username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return String email
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return String password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return Boolean IsModerator
     */
    public Boolean getIsModerator() {
        return this.IsModerator;
    }

    /**
     *
     * @param IsModerator
     */
    public void setIsModerator(Boolean IsModerator) {
        this.IsModerator = IsModerator;
    }

    @Override
    public boolean isArtist() {
        return false;  // Ce n'est pas un artiste
    }

    public boolean isSubscribe(Artist artist) {
        for (OrdUser subscriber : artist.getSubscribers()) {
            if (subscriber.getId() == this.id) {
                return true;
            }
        }
        return false;
    }
}