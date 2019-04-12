package edu.upc.dsa.models;

public class SingerTOInfo {
    private String id;
    private String name;
    private int albums;
    private int tracks;

    public SingerTOInfo() {
    }

    public SingerTOInfo(String id, String name, Integer albums, Integer tracks) {
        this.id = id;
        this.name = name;
        this.albums = albums;
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

    public Integer getAlbums() {
        return albums;
    }

    public void setAlbums(Integer albums) {
        this.albums = albums;
    }

    public Integer getTracks() {
        return tracks;
    }

    public void setTracks(Integer tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "AlbumTOInfo [Id="+id+", Name=" + name + ", Albums=" + albums + ", Tracks=" + tracks + "]";
    }
}
