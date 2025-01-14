package waveon.waveon.bl;

import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.CommentsDAO;
import waveon.waveon.persist.MusicDAO;

import java.util.ArrayList;

public class MusicCommentsFacade {

    private CommentsDAO commentsDAO;
    private MusicDAO musicDAO;

    public MusicCommentsFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        commentsDAO = factory.createCommentsDAO();
        musicDAO = factory.createMusicDAO();
    }

    // Returns all musics from the DAO without the music file content to avoid memory issues
    public ArrayList<Music> getAllMusics() {
        return musicDAO.getAllMusics();
    }

    // Returns a music by its title
    public Music getMusicByTitle(String title) {
        // This method should be updated to fetch music by title from the database
        // For now, it returns dummy data
        return getAllMusics().stream()
                .filter(music -> music.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    // Returns the list of comments for a music
    public ArrayList<Comments> getCommentsForMusic(int musicId) {
        return commentsDAO.getCommentsByMusicId(musicId);
    }

    // Adds a new comment to a music
    public void addComment(String content, int userId, int musicId) {
        Comments newComment = new Comments(0, musicId, content, userId);
        commentsDAO.addComment(newComment);
    }

    // Updates an existing comment
    public void updateCommentary(Comments comment) {
        commentsDAO.updateComment(comment);
    }

    // Deletes a comment
    public void deleteComment(int commentId) {
        commentsDAO.deleteComment(commentId);
    }
}