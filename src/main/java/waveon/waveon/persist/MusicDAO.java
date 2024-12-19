package waveon.waveon.persist;

import waveon.waveon.core.Music;

public interface MusicDAO {
    /**
     * Saves a music object to the database.
     * @param music the music object to save
     * @return true if the operation was successful, false otherwise
     */
    boolean saveMusic(Music music);
}