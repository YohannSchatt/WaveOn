package waveon.waveon.bl;

import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.CommentsDAO;
import waveon.waveon.persist.MusicDAO;

import java.util.List;

public class MusicCommentaryFacade {

    private CommentsDAO commentsDAO;
    private MusicDAO musicDAO;

    public MusicCommentaryFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        commentsDAO = factory.createCommentsDAO();
        musicDAO = factory.createMusicDAO();
    }

    public List<Music> getAllMusic() {
        return musicDAO.getAllMusic();
    }

    public Music getMusicByTitle(String title) {
        // This method should be updated to fetch music by title from the database
        // For now, it returns dummy data
        return getAllMusic().stream()
                .filter(music -> music.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public List<Comments> getCommentariesForMusic(int musicId) {
        return commentsDAO.getCommentsByMusicId(musicId);
    }

    public void addCommentary(String content, int userId, int musicId) {
        Comments newComment = new Comments(0, musicId, content, userId);
        commentsDAO.addComment(newComment);
    }

    public void updateCommentary(Comments comment) {
        commentsDAO.updateComment(comment);
    }

    public void deleteCommentary(int commentId) {
        commentsDAO.deleteComment(commentId);
    }
}