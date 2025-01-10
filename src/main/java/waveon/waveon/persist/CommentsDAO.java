package waveon.waveon.persist;

import waveon.waveon.core.Comments;
import java.util.List;

public interface CommentsDAO {
    Comments getCommentById(int id);
    List<Comments> getAllComments();
    List<Comments> getCommentsByMusicId(int musicId);
    void addComment(Comments comment);
    void updateComment(Comments comment);
    void deleteComment(int commentId);
}