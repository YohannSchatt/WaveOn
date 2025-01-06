// File: src/main/java/waveon/waveon/dao/MusicDAO.java
package waveon.waveon.persist;

import waveon.waveon.core.Music;

import java.util.ArrayList;

public interface MusicDAO {
     ArrayList<Music> getAllMusic();
}