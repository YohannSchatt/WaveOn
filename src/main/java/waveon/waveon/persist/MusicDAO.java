// File: src/main/java/waveon/waveon/dao/MusicDAO.java
package waveon.waveon.persist;

import waveon.waveon.core.Music;

import java.util.List;

public interface MusicDAO {
     List<Music> getAllMusic();
}