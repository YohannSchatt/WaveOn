package waveon.waveon;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Playlist {

    /**
     * Default constructor
     */
    public Playlist() {
    }

    /**
     * 
     */
    public int id;

    /**
     * 
     */
    public String name;

    /**
     * 
     */
    public User Creator;

    /**
     * 
     */
    public Music[] Musics;

    /**
     * 
     */
    public PlaylistFollower[] UsersList;

    /**
     * 
     */
    public int Attribute6;

    /**
     * 
     */
    public Boolean IsRandom;

    /**
     * 
     */
    public void Getters() {
        // TODO implement here
    }

    /**
     * @param musicid  
     * @return
     */
    public void AddMusic(int musicid ) {
        // TODO implement here
    }

    /**
     * @param musicid 
     * @return
     */
    public void RemoveMusic(int musicid) {
        // TODO implement here
    }

    /**
     * @return
     */
    public void DeletePlaylist() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void ChangeRandom() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void AddToQueue() {
        // TODO implement here
    }

}