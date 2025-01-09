package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAOPG;
import waveon.waveon.persist.NotificationDAO;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;

public class UploadMusicFacade {
    private final MusicDAO musicDAO;
    private final NotificationDAO notificationDAO;

    public UploadMusicFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        this.musicDAO = factory.createMusicDAO();
        this.notificationDAO = factory.createNotificationDAO();
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
            int notifId = notificationDAO.getLastId();
            notificationDAO.createNotification("Music uploaded", title + " has been uploaded successfully", "/artist/"+artistId);
            notificationDAO.linkNotificationToArtist(notifId, artistId);

            // Créer une notification pour les followers de l'artiste
            ArrayList<Integer> followerIds = notificationDAO.getUserIdsFollowingArtist(artistId);
            notifId = notificationDAO.getLastId();
            notificationDAO.createNotification("New music from " + artistName, artistName + " has uploaded a new music: " + title, "/artist/"+artistId);
            System.out.println("nb : " + followerIds.size());

            for (int followerId : followerIds) {
                System.out.println("followerId: " + followerId);
                notificationDAO.linkNotificationToUser(notifId, followerId);
            }
            return true;

        }
        return false;
    }

}