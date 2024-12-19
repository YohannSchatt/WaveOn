package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.AbstractFactory;

public class UploadMusicFacade {
    private final MusicDAO musicDAO;

    public UploadMusicFacade() {
        this.musicDAO = AbstractFactory.getInstance().createMusicDAO();
    }

    public boolean uploadMusic(String title, byte[] fileContent) {
        if (LoginFacade.getCurrentUser() != null && LoginFacade.getCurrentUser() instanceof Artist) {
            Music music = new Music(title, fileContent, ((Artist) LoginFacade.getCurrentUser()).getId());
            return musicDAO.saveMusic(music);
        }
        return false;
    }

}