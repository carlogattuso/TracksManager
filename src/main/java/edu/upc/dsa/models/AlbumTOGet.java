package edu.upc.dsa.models;

public class AlbumTOGet {
    private String id;
    private String name;
    private String singer;

    public AlbumTOGet() {
    }

    public AlbumTOGet(String id, String name, String singer) {
        this.id = id;
        this.name = name;
        this.singer = singer;
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

    @Override
    public String toString() {
        return "AlbumTOGet [Id="+id+", Name=" + name + ", Singer=" + singer +"]";
    }
}
