package waveon.waveon.core;

public class Comments {

    private int id;
    private String content;
    private int userid;
    private int musicid;
    private int numberLike;
    private Comments[] answers;

    // Default constructor
    public Comments() {
    }

    // Parameterized constructor
    public Comments(int id, int musicid, String content, int userid) {
        this.id = id;
        this.content = content;
        this.musicid = musicid;
        this.userid = userid;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userid;
    }

    public int getMusic() {
        return musicid;
    }

    public void setMusic(int musicid) {
        this.musicid = musicid;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user_id=" + userid +
                ", music=" + musicid +
                ", numberLike=" + numberLike +
                '}';
    }
}