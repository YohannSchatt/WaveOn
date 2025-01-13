package waveon.waveon.core;


public class Notification {

    public Notification(int id, String title, String content, int link) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public int id;

    public String title;

    public String content;

    public int link;

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public int getLink() {
        return link;
    }
}