package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Singer {
    private String id;
    private String name;
    private List<Track> trackList = new LinkedList<>();
    private List<Album> albumList = new LinkedList<>();

    public Singer(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public void addTrack(Track t){
        this.trackList.add(t);
    }

    public void addAlbum(Album a){
        this.albumList.add(a);
    }

    public void deleteTrack(Track t){
        this.trackList.remove(t);
    }

    public void deleteAlbum(Album a){
        this.albumList.remove(a);
    }

    public int trackSize(){ return this.trackList.size(); }

    public int albumSize(){
        return this.albumList.size();
    }

    @Override
    public String toString() {
        return "Singer [id="+id+", name=" + name +"]";
    }
}
