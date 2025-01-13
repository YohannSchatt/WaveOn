package waveon.waveon.core;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Message {

    /**
     * Default constructor
     */
    public Message() {
    }

    /**
     * 
     */
    public OrdUser Author;

    /**
     * 
     */
    public int Id;

    /**
     * 
     */
    public String content;

    /**
     * 
     */
    public Date date;

    public OrdUser getAuthor() {
        return Author;
    }

    public void setAuthor(OrdUser author) {
        Author = author;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Message(OrdUser author, int id, String content, Date date) {
        Author = author;
        Id = id;
        this.content = content;
        this.date = date;
    }



}