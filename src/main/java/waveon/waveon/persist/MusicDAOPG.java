// File: src/main/java/waveon/waveon/persist/MusicDAOPG.java
package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Music;
import waveon.waveon.core.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDAOPG implements MusicDAO {

    private ArtistDAOPG artistDAOPG;

    public MusicDAOPG() {
        this.artistDAOPG = new ArtistDAOPG();
    }

    @Override
    public List<Music> getAllMusic() {
        List<Music> musicList = new ArrayList<>();
        String query = "SELECT * FROM music";

        try (Connection connection = PGconnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                Artist artist = artistDAOPG.getArtistById(artistId);
                byte[] fileContent = resultSet.getBytes("file_content");

                Music music = new Music(id, name, artist, null, fileContent);
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicList;
    }
}