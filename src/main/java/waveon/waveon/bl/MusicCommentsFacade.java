package waveon.waveon.bl;

import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.CommentsDAO;
import waveon.waveon.persist.MusicDAO;

import java.util.ArrayList;

/**
 * The MusicCommentsFacade class provides methods to manage comments on music tracks,
 * including retrieving all music tracks, fetching music by title, and managing comments.
 */
public class MusicCommentsFacade {

    private CommentsDAO commentsDAO;
    private MusicDAO musicDAO;

    /**
     * Constructs a new MusicCommentsFacade instance and initializes the comments and music DAOs.
     */
    public MusicCommentsFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        commentsDAO = factory.createCommentsDAO();
        musicDAO = factory.createMusicDAO();
    }

    /**
     * Returns all music tracks from the DAO without the music file content to avoid memory issues.
     *
     * @return a list of all music tracks
     */
    public ArrayList<Music> getAllMusics() {
        return musicDAO.getAllMusics();
    }

    /**
     * Returns a music track by its title.
     *
     * @param title the title of the music track
     * @return the music track with the specified title, or null if not found
     */
    public Music getMusicByTitle(String title) {
        return getAllMusics().stream()
                .filter(music -> music.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the list of comments for a music track.
     *
     * @param musicId the ID of the music track
     * @return a list of comments for the specified music track
     */
    public ArrayList<Comments> getCommentsForMusic(int musicId) {
        return commentsDAO.getCommentsByMusicId(musicId);
    }

    /**
     * Adds a new comment to a music track.
     *
     * @param content the content of the comment
     * @param userId the ID of the user adding the comment
     * @param musicId the ID of the music track
     */
    public void addComment(String content, int userId, int musicId) {
        Comments newComment = new Comments(0, musicId, content, userId);
        commentsDAO.addComment(newComment);
    }

    /**
     * Updates an existing comment.
     *
     * @param comment the comment to update
     */
    public void updateCommentary(Comments comment) {
        commentsDAO.updateComment(comment);
    }

    /**
     * Deletes a comment.
     *
     * @param commentId the ID of the comment to delete
     */
    public void deleteComment(int commentId) {
        commentsDAO.deleteComment(commentId);
    }
}