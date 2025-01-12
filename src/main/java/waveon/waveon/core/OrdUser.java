package waveon.waveon.core;

public class OrdUser implements IUser {

    public int id;
    public String username;
    public String email;
    public String password;
    public OrdUser(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public OrdUser(int id ) {
        this.id = id;
    }

    public Boolean IsModerator = false;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsModerator() {
        return this.IsModerator;
    }

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