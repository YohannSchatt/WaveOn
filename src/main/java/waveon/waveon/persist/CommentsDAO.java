package waveon.waveon.persist;

import waveon.waveon.core.Comments;
import java.util.ArrayList;

public interface CommentsDAO {
    Comments getCommentById(int id);
    ArrayList<Comments> getAllComments();
    ArrayList<Comments> getCommentsByMusicId(int musicId);
    void addComment(Comments comment);
    void updateComment(Comments comment);
    void deleteComment(int commentId);
}