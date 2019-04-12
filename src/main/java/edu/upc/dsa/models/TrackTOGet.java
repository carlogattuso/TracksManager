package edu.upc.dsa.models;

public class TrackTOGet {
    String id;
    String title;
    String singer;
    String album;

    public TrackTOGet() {
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "TrackTOGet [Id="+id+", Title=" + title + ", Singer=" + singer +", Album=" + album +"]";
    }
}
