package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.core.OrdUser;
import waveon.waveon.persist.*;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;

public class UploadMusicFacade {
    private final MusicDAO musicDAO;
    private final NotificationDAO notificationDAO;
    private final ArtistDAO artistDAO;

    public UploadMusicFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        this.musicDAO = factory.createMusicDAO();
        this.notificationDAO = factory.createNotificationDAO();
        this.artistDAO = factory.createArtistDAO();
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

            musicDAO.saveMusic(music);

            // Créer une notification pour l'artiste
            int notifId = notificationDAO.getLastId()+1;
            notificationDAO.createNotification("Music uploaded", title + " has been uploaded successfully", "/artist/"+artistId);
            notificationDAO.linkNotificationToArtist(notifId, artistId);

            // Créer une notification pour les followers de l'artiste
            ArrayList<OrdUser> followers = artistDAO.getSubscribers(artistId);
            notifId = notificationDAO.getLastId()+1;
            notificationDAO.createNotification("New music from " + artistName, artistName + " has uploaded a new music: " + title, "/artist/"+artistId);
            System.out.println("nb : " + followers.size());

            for (OrdUser follower : followers) {
                System.out.println("followerId: " + follower);
                notificationDAO.linkNotificationToUser(notifId, follower.getId());
            }
            return true;

        }
        return false;
    }

}