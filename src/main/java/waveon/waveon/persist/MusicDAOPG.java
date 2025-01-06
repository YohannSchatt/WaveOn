package waveon.waveon.persist;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class MusicDAOPG implements MusicDAO {

    @Override
    public ArrayList<Music> getAllMusic() {
        ArrayList<Music> musicList = new ArrayList<>();
        String query = "SELECT * FROM music";

        try (Connection connection = PGconnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                byte[] fileContent = resultSet.getBytes("file_content");
                byte[] coverMusic = resultSet.getBytes("cover_image");
                int artistId = resultSet.getInt("artist_id");
                String artistName = resultSet.getString("artist_name");
                Date releaseDate = resultSet.getDate("release_date");
                int streamCount = resultSet.getInt("stream_count");


                Music music = new Music(id, title, fileContent, coverMusic, artistId, artistName, releaseDate, streamCount);
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicList;
    }

}