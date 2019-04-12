package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.List;

public interface TracksManager {

    //POST REQUEST
    Singer addSinger (String id, String name);
    Album addAlbum (String id, String name, String singerId);
    Track addTrack (String id, String title, String singer, String album); //DONE

    //GET REQUESTS
    Track getTrack(String id) throws TrackNotFoundException;
    Album getAlbum (String id) throws AlbumNotFoundException;
    Singer getSinger (String id) throws SingerNotFoundException;
    List<Track> getTracksByAlbum (String albumId) throws EmptyTrackListException;
    List<Track> getTracksBySinger (String singerId) throws EmptyTrackListException;
    List<Album> getAlbumsBySinger (String singerId) throws EmptyAlbumListException;
    List<Album> findAllAlbums() throws EmptyAlbumListException;
    List<Track> findAllTracks() throws EmptyTrackListException;
    List<Singer> findAllSingers() throws EmptySingerListException;

    //DELETE REQUESTS
    void deleteTrack(String id);
    void deleteSinger(String id);
    void deleteAlbum(String id);

    //PUT REQUESTS
    Track updateTrack(TrackTOUpdate t);
    Album updateAlbum(AlbumTOUpdate a);
    Singer updateSinger(SingerTO s);

    //SIZES
    int trackSize();
    int albumSize();
    int singerSize();

    //TRANSFERENCE OBJECTS
    TrackTOGet sendTrack(Track t);
    AlbumTOGet sendAlbum(Album a);
    SingerTO sendSinger(Singer s);
    TrackTOInfo sendInfoTrack(Track t);
    AlbumTOInfo sendInfoAlbum(Album a);
    SingerTOInfo sendInfoSinger(Singer s);
    List<AlbumTOGet> sendAlbumList();
    List<SingerTO> sendSingerList();
    List<TrackTOUpdate> sendTracksByAlbum(Album a);
    List<TrackTOBySinger> sendTracksBySinger(Singer s);
    List<AlbumTOUpdate> sendAlbumsBySinger(Singer s);
    List<TrackTOGet> sendTrackList();
}
