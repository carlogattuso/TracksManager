package edu.upc.dsa.models;

public class AlbumTOInfo {
    private String id;
    private String name;
    private String singer;
    private String singerId;
    private int tracks;

    public AlbumTOInfo() {
    }

    public AlbumTOInfo(String id, String name, String singer, String singerId, int tracks) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.singerId = singerId;
        this.tracks = tracks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "AlbumTOInfo [Id="+id+", Name=" + name + ", Singer=" + singer + ", SingerId="+singerId + ", Tracks=" + tracks + "]";
    }
}
