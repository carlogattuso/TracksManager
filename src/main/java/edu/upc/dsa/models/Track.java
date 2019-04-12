package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Track {

    String id;
    String title;
    String singerId;
    /*static int lastId;*/
    String albumId;

    /*public Track() {
        this.id = RandomUtils.getId();
    }*/

    /*public Track(String title, String singer) {
        this();
        this.setSinger(singer);
        this.setTitle(title);
    }*/

    public Track (){}

    public Track(String id, String title, String singerId, String albumId) {
        this.id = id;
        this.title = title;
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
        return "Track [id="+id+", title=" + title + ", singerId=" + singerId +", albumId=" + albumId+"]";
    }

}