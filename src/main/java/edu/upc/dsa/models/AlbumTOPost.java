package edu.upc.dsa.models;

public class AlbumTOPost {
    private String id;
    private String name;
    private String singerId;

    public AlbumTOPost() {
    }

    public AlbumTOPost(String id, String name, String singerId) {
        this.id = id;
        this.name = name;
        this.singerId = singerId;
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

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }
}
