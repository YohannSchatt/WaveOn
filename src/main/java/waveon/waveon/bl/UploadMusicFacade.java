package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAOPG;

import java.sql.Date;

public class UploadMusicFacade {
    private final MusicDAO musicDAO;

    public UploadMusicFacade() {
        this.musicDAO = AbstractFactory.getInstance().createMusicDAO();
    }

    public boolean uploadMusic(String title, byte[] fileContent, byte[] coverImage) {
        if (UserSessionFacade.getCurrentUser() != null && UserSessionFacade.getCurrentUser() instanceof Artist currentArtist) {
            int artistId = currentArtist.getId();
            String artistName = currentArtist.getUsername(); // Récupérer le nom de l'artiste
            Date releaseDate = new Date(System.currentTimeMillis()); // Date actuelle
            int id = MusicDAOPG.getLastId() + 1;
            // Créer l'objet Music avec les informations nécessaires
            Music music = new Music(id, title, fileContent, coverImage, artistId, artistName, releaseDate, 0);

            // Sauvegarder dans la base de données
            return musicDAO.saveMusic(music);
        }
        return false;
    }

}