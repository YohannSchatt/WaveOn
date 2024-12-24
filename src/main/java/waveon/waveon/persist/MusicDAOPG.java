package waveon.waveon.persist;

import waveon.waveon.core.Music;
import waveon.waveon.connector.PGconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MusicDAOPG implements MusicDAO {
    @Override
    public boolean saveMusic(Music music) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO music (title, file_content, cover_image, artist_id, artist_name, release_date, stream_count) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, music.getTitle());
            pstmt.setBytes(2, music.getFileContent()); // Insertion du contenu binaire
            pstmt.setBytes(3, music.getCoverMusic()); // Insertion du contenu binaire
            pstmt.setInt(4, music.getArtistId());
            pstmt.setString(5, music.getArtistName());
            pstmt.setDate(6, music.getReleaseDate());
            pstmt.setInt(7, music.getStreamCount());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in MusicDAOPG.saveMusic: " + e);
            return false;
        }
    }
}
