package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Artist;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Music;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ArtistDAOPG implements ArtistDAO {

    private final PGconnector pg = PGconnector.getInstance();
    public ArtistDAOPG() {}

    public Connection connection;

    @Override
    public void addArtist(String email, String username, String password) {
        String sql = "INSERT INTO artist (email, username, password) VALUES (?, ?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.addArtist : " + e);
        }
    }

    public Artist getArtistByEmail(String email) {
        System.out.println("SELECT * FROM artist WHERE email = " + email);
        String sql = "SELECT * FROM artist WHERE email = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.getUserByEmail : " + e);
        }
        return null;
    }

    public Artist getArtistById(int id) {
        System.out.println("SELECT * FROM artist WHERE id = " + id);
        String sql = "SELECT * FROM artist WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistById : " + e);
        }
        return null;
    }

    public ArrayList<OrdUser> getSubscribers(int id) {
        System.out.println("SELECT ordinaryuser.id, ordinaryuser.username " +
                "FROM subscriber " +
                "JOIN ordinaryuser ON ordinaryuser.id = subscriber.user_id " +
                "WHERE subscriber.artist_id = " + id);
        String sql = "SELECT ordinaryuser.id, ordinaryuser.username " +
                "FROM subscriber " +
                "JOIN ordinaryuser ON ordinaryuser.id = subscriber.user_id " +
                "WHERE subscriber.artist_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<OrdUser> subscribers = new ArrayList<>();
            while (rs.next()) {
                OrdUser user = new OrdUser(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                subscribers.add(user);
            }
            return subscribers;
        } catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getSubscribers : " + e);
        }
        return null;
    }

    public Artist getAllInfoArtistById(int id) {
        System.out.println("SELECT a.id AS artist_id, a.username AS artist_username, a.email AS artist_email, a.password AS artist_password, u.username as user_username, " +
                "m.id AS music_id, m.title AS music_title, " +
                "u.id AS user_id " +
                "FROM artist a " +
                "LEFT JOIN music m ON a.id = m.artist_id " +
                "LEFT JOIN subscriber s ON a.id = s.artist_id " +
                "LEFT JOIN ordinaryuser u ON s.user_id = u.id " +
                "WHERE a.id = " + id);
        String sql = "SELECT a.id AS artist_id, a.username AS artist_username, a.email AS artist_email, a.password AS artist_password, u.username as user_username, " +
                "m.id AS music_id, m.title AS music_title, " +
                "u.id AS user_id " +
                "FROM artist a " +
                "LEFT JOIN music m ON a.id = m.artist_id " +
                "LEFT JOIN subscriber s ON a.id = s.artist_id " +
                "LEFT JOIN ordinaryuser u ON s.user_id = u.id " +
                "WHERE a.id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("id : " + id);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Artist artist = null;
            ArrayList<OrdUser> subscriber = new ArrayList<>();
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                System.out.println("music_id : " + rs.getInt("music_id"));
                if (artist == null) {
                    artist = new Artist(rs.getInt("artist_id"), rs.getString("artist_username"), rs.getString("artist_email"), rs.getString("artist_password"));
                }
                if (rs.getInt("user_id") != 0) {
                    OrdUser user = new OrdUser(rs.getInt("user_id"));
                    user.setUsername(rs.getString("user_username"));
                    subscriber.add(user);
                }
                if (rs.getInt("music_id") != 0) {
                    musics.add(new Music(rs.getInt("music_id"), rs.getString("music_title"), artist.getId()));
                }
            }
            if (artist != null) {
                artist.setSubscribers(subscriber);
                artist.setMusics(musics);
            }
            return artist;
        } catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistById : " + e);
        }
        return null;
    }


    @Override
    public ArrayList<Artist> getArtistsByName(String name) {
        System.out.println("SELECT * FROM artist WHERE LOWER(username) LIKE LOWER(" + name +")");
        String sql = "SELECT * FROM artist WHERE LOWER(username) LIKE LOWER(?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Artist> artists = new ArrayList<Artist>();
            while (rs.next()) {
                artists.add(new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }
            return artists;
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistsByName : " + e);
        }
        return null;
    }

    public ArrayList<Artist> getAllArtists() {
        System.out.println("SELECT * FROM artist");
        String sql = "SELECT * FROM artist";
        ArrayList<Artist> artists = new ArrayList<Artist>();
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artists.add(new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getUserByEmail : " + e);
        }
        return artists;
    }

    @Override
    public Artist getArtistByMusicId(int musicId) {
        System.out.println("SELECT * FROM artist WHERE id = (SELECT artist_id FROM music WHERE id = " + musicId + ")");
        String sql = "SELECT * FROM artist WHERE id = (SELECT artist_id FROM music WHERE id = ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, musicId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistByMusicId : " + e);
        }
        return null;
    }

    public void updateArtist(int id, String username, String email, String password) {
        if (password == null || password.trim().isEmpty()) {
            updateWithOutPassword(id, username, email);
        } else {
            updateWithPassword(id, username, email, password);
        }

    }

    private void updateWithPassword(int id, String username, String email, String password) {
        String sql = "UPDATE ordinaryuser SET username = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.updateUser : " + e);
        }
    }

    private void updateWithOutPassword(int id, String username, String email) {
        String sql = "UPDATE ordinaryuser SET username = ?, email = ? WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.updateUser : " + e);
        }
    }


}