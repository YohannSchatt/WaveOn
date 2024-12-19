package waveon.waveon.core;

public class Music {
    private final String title;
    private final byte[] fileContent; // Contenu binaire du fichier
    private final int artistId;

    public Music(String title, byte[] fileContent, int artistId) {
        this.title = title;
        this.fileContent = fileContent;
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public int getArtistId() {
        return artistId;
    }
}
