package waveon.waveon.core;


import java.util.ArrayList;

public class Artist implements IUser {

    public int id;
    public String username;
    public String email;
    public String password;
    public ArrayList<OrdUser> subscribers;
    public ArrayList<Music> musics;

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

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) { this.username = username;}

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isArtist() { return true; }

    public ArrayList<OrdUser> getSubscribers() {
        return this.subscribers;
    }

    public void setSubscribers(ArrayList<OrdUser> subscribers) {
        this.subscribers = subscribers;
    }

    public ArrayList<Music> getMusics() {
        return this.musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    public int getNbSubscribers() {
        return this.subscribers.size();
    }

    public boolean isSubscribe(Artist artist) {
        return false;
    }

}