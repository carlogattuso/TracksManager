package edu.upc.dsa.models;

public class TrackTOBySinger {
    String id;
    String title;
    String album;

    public TrackTOBySinger() {
    }

    public TrackTOBySinger(String id, String title, String album) {
        this.id = id;
        this.title = title;
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
