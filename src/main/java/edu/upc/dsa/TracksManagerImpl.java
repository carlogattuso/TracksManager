package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.*;

import org.apache.log4j.Logger;

public class TracksManagerImpl implements TracksManager {
    private static TracksManagerImpl instance;

    //Structures
    protected List<Track> tracks;
    protected HashMap<String, Album> albumHashMap;
    protected HashMap<String, Singer> singerHashMap;

    final static Logger logger = Logger.getLogger(TracksManagerImpl.class);

    private TracksManagerImpl() {
        this.tracks = new LinkedList<>();
        this.albumHashMap = new HashMap<>();
        this.singerHashMap = new HashMap<>();
    }

    public static TracksManagerImpl getInstance() {
        if (instance==null) instance = new TracksManagerImpl();
        return instance;
    }

    public int trackSize() {
        int ret = this.tracks.size();
        return ret;
    }

    public int albumSize() {
        int ret = this.albumHashMap.size();
        return ret;
    }

    public int singerSize() {
        int ret = this.singerHashMap.size();
        return ret;
    }

    public Singer addSinger(String id, String name) {
        Singer s = new Singer(id, name);
        logger.info("new Singer: " + s.toString());
        singerHashMap.put(s.getId(), s);
        return s;
    }

    public Album addAlbum(String id, String name, String singerId) {
        Album a = new Album(id,name,singerId);
        logger.info("new Album: " + a.toString());
        albumHashMap.put(a.getId(),a);
        this.singerHashMap.get(singerId).addAlbum(a);
        return a;
    }

    public Track addTrack(String id, String title, String singerId, String albumId) {
        Track t = new Track(id, title, singerId, albumId);
        logger.info("new Track: " + t.toString());
        tracks.add(t);
        this.albumHashMap.get(albumId).addTrack(t);
        this.singerHashMap.get(singerId).addTrack(t);
        return t;
    }

    public Track getTrack(String id) throws TrackNotFoundException{
        for (Track t: this.tracks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        logger.error("Track not found");
        throw new TrackNotFoundException("Track not found");
    }

    public Singer getSinger(String id) throws SingerNotFoundException{
        Singer s = this.singerHashMap.get(id);
        if (s == null){
            logger.error("Singer not found");
            throw new SingerNotFoundException("Singer not found");
        }
        return s;
    }

    public Album getAlbum(String id) throws AlbumNotFoundException {
        Album a = this.albumHashMap.get(id);
        if (a == null){
            logger.error("Album not found");
            throw new AlbumNotFoundException("Album not found");
        }
        return a;
    }

    public List<Track> getTracksByAlbum(String albumId) throws EmptyTrackListException{
        Album  a = this.albumHashMap.get(albumId);
        if(a.trackSize()==0){
            logger.warn("Empty trackList");
            throw new EmptyTrackListException("Empty trackList");
        }
        List<Track> trackList = a.getTrackList();
        logger.info("getTracksByAlbum [id="+a.getId()+", name=" + a.getName() + ", tracks=" + trackList.size() +"]");
        return trackList;
    }

    public List<Track> getTracksBySinger (String singerId) throws EmptyTrackListException{
        Singer s = this.singerHashMap.get(singerId);
        if(s.trackSize()==0){
            logger.warn("Empty trackList");
            throw new EmptyTrackListException("Empty trackList");
        }
        List<Track> trackList = s.getTrackList();
        logger.info("getTracksBySinger [id="+s.getId()+", name=" + s.getName() + ", tracks=" + trackList.size() +"]");
        return trackList;
    }

    public List<Album> getAlbumsBySinger (String singerId) throws EmptyAlbumListException{
        Singer s = this.singerHashMap.get(singerId);
        if(s.albumSize()==0){
            logger.warn("Empty albumList");
            throw new EmptyAlbumListException("empty album list");
        }
        List<Album> albumList = s.getAlbumList();
        logger.info("getAlbumBySinger [id="+s.getId()+", name=" + s.getName() + ", albums=" + albumList.size() +"]");
        return albumList;
    }

    public List<Track> findAllTracks() throws EmptyTrackListException{
        if(this.trackSize()==0){
            logger.warn("Empty trackList");
            throw new EmptyTrackListException("Empty trackList");
        }
        logger.info("Find all tracks [tracks="+this.trackSize()+"]");
        return this.tracks;
    }

    public List<Album> findAllAlbums() throws EmptyAlbumListException {
        if(this.albumSize()==0){
            logger.warn("Empty albumList");
            throw new EmptyAlbumListException("Empty albumList");
        }
        List<Album> albumList = new LinkedList<>();
        Set<String> stringSet = this.albumHashMap.keySet();
        for(String set: stringSet){
            albumList.add(this.albumHashMap.get(set));
        }
        logger.info("Find all albums [albums="+this.albumSize()+"]");
        return albumList;
    }

    public List<Singer> findAllSingers() throws EmptySingerListException {
        if(this.singerSize()==0){
            logger.warn("Empty singerList");
            throw new EmptySingerListException("Empty singerList");
        }
        List<Singer> singerList = new LinkedList<>();
        Set<String> stringSet = this.singerHashMap.keySet();
        for(String set: stringSet){
            singerList.add(this.singerHashMap.get(set));
        }
        logger.info("Find all singers [singers="+this.singerSize()+"]");
        return singerList;
    }

    public Track updateTrack(TrackTOUpdate t){
        Track track = new Track();
        for(Track track1 : this.tracks) {
            if (track1.getId().equals(t.getId())) {
                logger.info("Update track: "+track1.toString());
                track1.setTitle(t.getTitle());
                logger.info("Updated track: "+track1.toString());
                track = track1;
            }
        }
        return track;
    }

    public Singer updateSinger(SingerTO s) {
        Singer singer = singerHashMap.get(s.getId());
        logger.info("Update singer: "+singer.toString());
        singer.setName(s.getName());
        logger.info("Updated singer: "+singer.toString());
        return singer;
    }

    public Album updateAlbum(AlbumTOUpdate a){
        Album album = albumHashMap.get(a.getId());
        logger.info("Update album: "+album.toString());
        album.setName(a.getName());
        logger.info("Updated album: "+album.toString());
        return album;
    }

    public void deleteTrack(String id) {
        Track rm = new Track();
        for(Track t:this.tracks){
            if(t.getId().equals(id)){
                rm = t;
            }
        }
        this.tracks.remove(rm);
        logger.info("Delete track: "+rm.toString());
        logger.info("Tracks list [tracks="+this.trackSize()+"]");

        singerHashMap.get(rm.getSingerId()).deleteTrack(rm);
        logger.info("Singers hash map [singers="+this.singerSize()+"]");

        albumHashMap.get(rm.getAlbumId()).deleteTrack(rm);
        logger.info("Albums hash map [albums="+this.albumSize()+"]");
    }

    public void deleteSinger(String id){
        Singer rm = this.singerHashMap.get(id);
        this.singerHashMap.remove(id);
        logger.info("Delete singer: "+rm.toString());
        logger.info("Singers hash map [singers="+this.singerSize()+"]");

        List<Track> needToRemove = new LinkedList<>();
        for (Track t : this.tracks) {
            if (t.getSingerId().equals(rm.getId())) {
                needToRemove.add(t);
            }
        }
        this.tracks.removeAll(needToRemove);
        logger.info("Tracks list [tracks="+this.trackSize()+"]");

        List<Album> albumList = rm.getAlbumList();
        Set<String> set = new HashSet<>();
        for (Album a : albumList) {
            if (a.getSingerId().equals(rm.getId())) {
                set.add(a.getId());
            }
        }
        this.albumHashMap.keySet().removeAll(set);
        logger.info("Albums hash map [albums="+this.albumSize()+"]");
    }

    public void deleteAlbum(String id){
        Album rm = this.albumHashMap.get(id);
        this.albumHashMap.remove(id);
        logger.info("Delete album: "+rm.toString());
        logger.info("Albums hash map [albums="+this.albumSize()+"]");

        List<Track> needToRemove = new LinkedList<>();
        for (Track t : this.tracks) {
            if (t.getAlbumId().equals(rm.getId())) {
                needToRemove.add(t);
            }
        }
        this.tracks.removeAll(needToRemove);
        logger.info("Tracks list [tracks="+this.trackSize()+"]");

        this.singerHashMap.get(rm.getSingerId()).getAlbumList().remove(rm);
        logger.info("Albums hash map [albums="+this.albumSize()+"]");
    }

    public TrackTOGet sendTrack(Track t) {
        TrackTOGet send = new TrackTOGet();
        send.setId(t.getId());
        send.setTitle(t.getTitle());
        send.setSinger(this.singerHashMap.get(t.getSingerId()).getName());
        send.setAlbum(this.albumHashMap.get(t.getAlbumId()).getName());
        logger.info("pass Track to TrackTOGet: "+ t.toString());
        logger.info("sendTrack (TrackTOGet): "+ send.toString());
        return send;
    }

    public AlbumTOGet sendAlbum(Album a){
        AlbumTOGet send = new AlbumTOGet();
        send.setId(a.getId());
        send.setName(a.getName());
        send.setSinger(this.singerHashMap.get(a.getSingerId()).getName());
        logger.info("pass Album to AlbumTOGet: "+ a.toString());
        logger.info("sendAlbum (AlbumTOGet): "+ send.toString());
        return send;
    }

    public SingerTO sendSinger(Singer s){
        SingerTO send = new SingerTO();
        send.setId(s.getId());
        send.setName(s.getName());
        logger.info("pass Singer to SingerTO: "+ s.toString());
        logger.info("sendSinger (SingerTO): "+ send.toString());
        return send;
    }

    public AlbumTOInfo sendInfoAlbum(Album a){
        AlbumTOInfo info = new AlbumTOInfo();
        info.setId(a.getId());
        info.setName(a.getName());
        info.setSinger(this.singerHashMap.get(a.getSingerId()).getName());
        info.setSingerId(a.getSingerId());
        info.setTracks(a.trackSize());
        logger.info("pass Album to AlbumTOInfo: "+ a.toString());
        logger.info("sendInfoAlbum (AlbumTOInfo): "+ info.toString());
        return info;
    }

    public SingerTOInfo sendInfoSinger(Singer s){
        SingerTOInfo info = new SingerTOInfo();
        info.setId(s.getId());
        info.setName(s.getName());
        info.setAlbums(s.albumSize());
        info.setTracks(s.trackSize());
        logger.info("pass Singer to SingerTOInfo: "+ s.toString());
        logger.info("sendInfoSinger (SingerTOInfo): "+ info.toString());
        return info;
    }

    public TrackTOInfo sendInfoTrack(Track t){
        TrackTOInfo info = new TrackTOInfo();
        info.setId(t.getId());
        info.setTitle(t.getTitle());
        info.setSinger(this.singerHashMap.get(t.getSingerId()).getName());
        info.setAlbum(this.albumHashMap.get(t.getAlbumId()).getName());
        info.setAlbumId(t.getAlbumId());
        info.setSingerId(t.getSingerId());
        logger.info("pass Track to TrackTOInfo: "+ t.toString());
        logger.info("sendInfoTrack (TrackTOInfo): "+ info.toString());
        return info;
    }

    public List<TrackTOGet> sendTrackList(){
        List<TrackTOGet> list = new LinkedList<>();
        for(Track t : this.tracks){
            list.add(sendTrack(t));
        }
        return list;
    }

    public List<AlbumTOGet> sendAlbumList(){
        List<AlbumTOGet> list = new LinkedList<>();
        Set<String> stringSet = this.albumHashMap.keySet();
        for(String set: stringSet){
            list.add(sendAlbum(this.albumHashMap.get(set)));
        }
        return list;
    }

    public List<SingerTO> sendSingerList() {
        List<SingerTO> list = new LinkedList<>();
        Set<String> stringSet = this.singerHashMap.keySet();
        for(String set: stringSet){
            list.add(sendSinger(this.singerHashMap.get(set)));
        }
        return list;
    }

    public List<TrackTOUpdate> sendTracksByAlbum(Album a){
        List<TrackTOUpdate> send = new LinkedList<>();
        for(Track t : a.getTrackList()){
            send.add(new TrackTOUpdate(t.getId(),t.getTitle()));
        }
        logger.info("sendTracksByAlbum: "+ a.toString());
        logger.info("[tracks="+a.trackSize()+"]");
        return send;
    }

    public List<TrackTOBySinger> sendTracksBySinger(Singer s){
        List<TrackTOBySinger> send = new LinkedList<>();
        for(Track t : s.getTrackList()){
            send.add(new TrackTOBySinger(t.getId(),t.getTitle(),this.albumHashMap.get(t.getAlbumId()).getName()));
        }
        logger.info("sendTracksBySinger: "+ s.toString());
        logger.info("[tracks="+s.trackSize()+"]");
        return send;
    }

    public List<AlbumTOUpdate> sendAlbumsBySinger(Singer s){
        List<AlbumTOUpdate> send = new LinkedList<>();
        for(Album a : s.getAlbumList()){
            send.add(new AlbumTOUpdate(a.getId(),a.getName()));
        }
        logger.info("sendAlbumsBySinger: "+ s.toString());
        logger.info("[albums="+s.albumSize()+"]");
        return send;
    }

    public void clear(){
        this.singerHashMap.clear();
        this.albumHashMap.clear();
        this.tracks.clear();
    }
}