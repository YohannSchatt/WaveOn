package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.AbstractFactory;

import java.sql.Date;

public class UploadMusicFacade {
    private final MusicDAO musicDAO;

    public UploadMusicFacade() {
        this.musicDAO = AbstractFactory.getInstance().createMusicDAO();
    }

    public boolean uploadMusic(String title, byte[] fileContent, byte[] coverImage) {
        if (LoginFacade.getCurrentUser() != null && LoginFacade.getCurrentUser() instanceof Artist) {
            Artist currentArtist = (Artist) LoginFacade.getCurrentUser();
            int artistId = currentArtist.getId();
            String artistName = currentArtist.getUsername(); // Récupérer le nom de l'artiste
            Date releaseDate = new Date(System.currentTimeMillis()); // Date actuelle

            // Créer l'objet Music avec les informations nécessaires
            Music music = new Music(title, fileContent, coverImage, artistId, artistName, releaseDate);

            // Sauvegarder dans la base de données
            return musicDAO.saveMusic(music);
        }
        return false;
    }

}