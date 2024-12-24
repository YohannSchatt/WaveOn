package waveon.waveon.core;

import java.sql.Date;

public class Music {
    private final String title;
    private final byte[] fileContent; // Contenu binaire du fichier
    private final byte[] coverMusic; // Contenu binaire de la pochette
    private final int artistId;
    private final String artistName;
    private final Date releaseDate;
    private int stream_count;

    public Music(String title, byte[] fileContent, byte[] coverMusic, int artistId, String artistName, Date releaseDate) {
        this.title = title;
        this.fileContent = fileContent;
        this.coverMusic = coverMusic;
        this.artistId = artistId;
        this.artistName = artistName;
        this.releaseDate = releaseDate;
        this.stream_count = 0;
    }

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
        return stream_count;
    }
}

