package waveon.waveon.core;

/**
 * 
 */
public class Artist implements IUser {

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
    public Artist(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Music[] music = new Music[0];

    public Event[] events = new Event[0];

    public Music[] getMusic() {
        return music;
    }

    public Event[] getEvents() {
        return events;
    }

    public void addMusic(Music music) {
        Music[] newMusic = new Music[this.music.length + 1];
        System.arraycopy(this.music, 0, newMusic, 0, this.music.length);
        newMusic[this.music.length] = music;
        this.music = newMusic;
    }

    public void addEvent(Event event) {
        Event[] newEvents = new Event[this.events.length + 1];
        System.arraycopy(this.events, 0, newEvents, 0, this.events.length);
        newEvents[this.events.length] = event;
        this.events = newEvents;
    }

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

    }

    /**
     *
     * @return String password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {

    }
}