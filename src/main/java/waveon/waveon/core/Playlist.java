package waveon.waveon.core;

import java.util.ArrayList;

import java.util.*;

/**
 * 
 */
public class Playlist {

    private String name;
    private List<Music> musics;
    private int id;
    private int userId;

    public Playlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.musics = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void addMusic(Music music) {
        if (!musics.contains(music)) {
            musics.add(music);
        }
    }

    public void removeMusic(Music music) {
        musics.remove(music);
    }

    public void modifyPlaylist(String name) {
        this.name = name;
    }
}