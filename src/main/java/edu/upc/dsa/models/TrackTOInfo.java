package edu.upc.dsa.models;

public class TrackTOInfo {
    String id;
    String title;
    String singer;
    String album;
    String singerId;
    String albumId;

    public TrackTOInfo() {
    }

    public TrackTOInfo(String id, String title, String singer, String album, String singerId, String albumId) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.singerId = singerId;
        this.albumId = albumId;
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

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return "TrackTOInfo [Id="+id+", Title=" + title + ", Singer=" + singer +", Album=" + album +", SingerId=" + singerId +", AlbumId=" + albumId +"]";
    }
}
