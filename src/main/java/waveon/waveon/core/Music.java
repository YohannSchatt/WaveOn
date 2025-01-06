package waveon.waveon.core;

import java.sql.Date;
/**
 * 
 */
public class Music {
    private int id;
    private final String title;
    private final byte[] fileContent; // Contenu binaire du fichier
    private final byte[] coverMusic; // Contenu binaire de la pochette
    private final int artistId;
    private final String artistName;
    private final Date releaseDate;
    private final int streamCount;

    public Music(int id, String title, byte[] fileContent, byte[] coverMusic, int artistId, String artistName, Date releaseDate, int streamCount) {
        this.id = id;
        this.title = title;
        this.fileContent = fileContent;
        this.coverMusic = coverMusic;
        this.artistId = artistId;
        this.artistName = artistName;
        this.releaseDate = releaseDate;
        this.streamCount = streamCount;
    }



    public int getId() {return id;}

    public String getTitle() {
        return title;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public byte[] getCoverMusic() {
        return coverMusic;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

      public Date getReleaseDate() {
        return releaseDate;
    }

    public int getStreamCount() {
        return streamCount;
    }
}