package waveon.waveon.bl;

import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicCommentaryFacade {

    // This is a placeholder for the actual data source
    private List<Music> musicList = new ArrayList<>();
    private List<Comments> commentsList = new ArrayList<>();

    public MusicCommentaryFacade() {
        // Initialize with some dummy data
        Music music1 = new Music(1, "Rick Roll", null, null, 1, "Artist1", null, 0);
        Music music2 = new Music(2, "Never Gonna Give You Up", null, null, 2, "Artist2", null, 0);
        musicList.add(music1);
        musicList.add(music2);

        commentsList.add(new Comments(1, music1.getId(), "Great song!"));
        commentsList.add(new Comments(2, music1.getId(), "Love it!"));
        commentsList.add(new Comments(3, music1.getId(), "Not bad."));
    }

    public List<Music> getAllMusic() {
        return musicList;
    }

    public Music getMusicByTitle(String title) {
        for (Music music : musicList) {
            if (music.getTitle().equals(title)) {
                return music;
            }
        }
        return null;
    }

    public List<Comments> getCommentariesForMusic(int musicId) {
        List<Comments> result = new ArrayList<>();
        for (Comments comment : commentsList) {
            if (comment.getMusic() == musicId) {
                result.add(comment);
            }
        }
        return result;
    }

    public Comments getCommentByContent(String content) {
        for (Comments comment : commentsList) {
            if (comment.getContent().equals(content)) {
                return comment;
            }
        }
        return null;
    }

    public void addCommentary(String content, int userId, int musicId) {
        int newId = commentsList.size() + 1;
        Comments newComment = new Comments(newId, musicId, content);
        commentsList.add(newComment);
    }

    private Music getMusicById(int id) {
        for (Music music : musicList) {
            if (music.getId() == id) {
                return music;
            }
        }
        return null;
    }
}