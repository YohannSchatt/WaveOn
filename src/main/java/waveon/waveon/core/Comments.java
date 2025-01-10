package waveon.waveon.core;

public class Comments {

    private int id;
    private String content;
    private int iduser;
    private int idmusic;
    private int numberLike;
    private Comments[] answers;

    // Default constructor
    public Comments() {
    }

    // Parameterized constructor
    public Comments(int id, int idmusic, String content, int iduser) {
        this.id = id;
        this.content = content;
        this.idmusic = idmusic;
        this.iduser = iduser;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIDUser() {
        return iduser;
    }

    public void setIDUser(int iduser) {
        this.id = iduser;
    }

    public int getMusic() {
        return idmusic;
    }

    public void setMusic(int  idmusic) {
        this.idmusic = idmusic;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public Comments[] getAnswers() {
        return answers;
    }

    public void setAnswers(Comments[] answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user_id=" + iduser +
                ", music=" + idmusic +
                ", numberLike=" + numberLike +
                '}';
    }
}