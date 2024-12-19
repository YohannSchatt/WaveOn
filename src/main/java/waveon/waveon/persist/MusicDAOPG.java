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
        String sql = "INSERT INTO music (title, file_content, artist_id) VALUES (?, ?, ?)";

        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, music.getTitle());
            pstmt.setBytes(2, music.getFileContent()); // Insertion du contenu binaire
            pstmt.setInt(3, music.getArtistId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in MusicDAOPG.saveMusic: " + e);
            return false;
        }
    }
}
