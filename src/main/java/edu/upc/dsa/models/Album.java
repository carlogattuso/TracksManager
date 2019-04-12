package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Album {
    private String id;
    private String name;
    private String singerId;

    private List<Track> trackList = new LinkedList<>();

    public Album() {
    }

    public Album(String id, String name, String singer) {
        this.id = id;
        this.name = name;
        this.singerId = singer;
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

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singer) {
        this.singerId = singer;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public void deleteTrack(Track t) {
        this.trackList.remove(t);
    }

    public void addTrack(Track t) {
        this.trackList.add(t);
    }

    public int trackSize(){
        return this.trackList.size();
    }

    @Override
    public String toString() {
        return "Album [id="+id+", name=" + name + ", singerId=" + singerId +"]";
    }
}
