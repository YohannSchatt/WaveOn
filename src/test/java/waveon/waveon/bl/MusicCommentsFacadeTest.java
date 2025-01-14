package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Comments;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.CommentsDAO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MusicCommentsFacadeTest {

    private MusicCommentsFacade musicCommentsFacade;
    private CommentsDAO mockCommentsDAO;



    @BeforeEach
    public void setUp() {
        mockCommentsDAO = mock(CommentsDAO.class);
        AbstractFactory mockFactory = mock(AbstractFactory.class);

        when(mockFactory.createCommentsDAO()).thenReturn(mockCommentsDAO);
        AbstractFactory.setInstance(mockFactory);

        musicCommentsFacade = new MusicCommentsFacade();
    }

    @Test
    public void testGetCommentsForMusic() {
        ArrayList<Comments> mockComments = new ArrayList<>();
        mockComments.add(new Comments(1, 1, "Great song!", 1));
        mockComments.add(new Comments(2, 1, "Love it!", 2));

        when(mockCommentsDAO.getCommentsByMusicId(1)).thenReturn(mockComments);

        ArrayList<Comments> result = musicCommentsFacade.getCommentsForMusic(1);

        assertEquals(mockComments, result);
        verify(mockCommentsDAO, times(1)).getCommentsByMusicId(1);
    }

    @Test
    public void testAddComment() {
        musicCommentsFacade.addComment("Nice track!", 1, 1);

        verify(mockCommentsDAO, times(1)).addComment(any(Comments.class));
    }

    @Test
    public void testDeleteComment() {
        musicCommentsFacade.deleteComment(1);

        verify(mockCommentsDAO, times(1)).deleteComment(1);
    }


}