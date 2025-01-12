package waveon.waveon.persist;

import waveon.waveon.core.Comments;
import waveon.waveon.connector.PGconnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDAOPG implements CommentsDAO {
    private final PGconnector pg = PGconnector.getInstance();

    @Override
    public Comments getCommentById(int id) {
        System.out.println("SELECT * FROM comments WHERE id = ?" + id);
        String sql = "SELECT * FROM comments WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Comments(rs.getInt("idcomment"), rs.getInt("music_id"), rs.getString("content"), rs.getInt("user_id"));
            }
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.getCommentById: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Comments> getAllComments() {
        System.out.println("SELECT * FROM comments");
        ArrayList<Comments> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        try (Connection conn = pg.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comments.add(new Comments(rs.getInt("idcomment"), rs.getInt("music_id"), rs.getString("content"), rs.getInt("user_id")));
            }
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.getAllComments: " + ex.getMessage());
        }
        return comments;
    }

    @Override
    public ArrayList<Comments> getCommentsByMusicId(int musicId) {
        System.out.println("SELECT * FROM comments WHERE music_id = ?" + musicId);
        ArrayList<Comments> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE music_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, musicId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comments.add(new Comments(rs.getInt("idcomment"), rs.getInt("music_id"), rs.getString("content"), rs.getInt("user_id")));
            }
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.getCommentsByMusicId: " + ex.getMessage());
        }
        return comments;
    }

    @Override
    public void addComment(Comments comment) {
        String sql = "INSERT INTO comments (music_id, content, user_id) VALUES (?, ?,?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, comment.getMusic());
            pstmt.setString(2, comment.getContent());
            pstmt.setInt(3, comment.getIDUser());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.addComment: " + ex.getMessage());
        }
    }

    @Override
    public void updateComment(Comments comment) {
        String sql = "UPDATE comments SET content = ? WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.updateComment: " + ex.getMessage());
        }
    }

    @Override
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM comments WHERE idcomment = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error in CommentsDAOPG.deleteComment: " + ex.getMessage());
        }
    }
}