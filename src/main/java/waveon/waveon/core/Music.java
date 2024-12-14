package waveon.waveon.core;

import java.io.*;
import java.sql.Time;

/**
 * 
 */
public class Music {


    public Music(int id, String name, waveon.waveon.core.Artist Artist, Time Duration, File Content) {
        this.id = id;
        this.name = name;
        this.Artist = Artist;
        this.Duration = Duration;
        this.Content = Content;
    }

    public Music() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private waveon.waveon.core.Artist Artist;

    /**
     * 
     */
    private Time Duration;

    /**
     * 
     */
    private File Content;

    /**
     * 
     */
    public void Getters() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation2() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation3() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation4() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation5() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void AddToQueue() {
        // TODO implement here
    }

}