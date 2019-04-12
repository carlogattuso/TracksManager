package edu.upc.dsa.TracksManagerTest;

import edu.upc.dsa.TracksManagerImpl;
import edu.upc.dsa.models.*;
import org.junit.*;

import java.util.LinkedList;
import java.util.List;

public class TracksManagerTest {
    private TracksManagerImpl tm;

    @Before
    public void initialize(){
        tm = TracksManagerImpl.getInstance();

        tm.addSinger("michaeljackson","Michael Jackson");
        tm.addSinger("estopa","Estopa");

        tm.addAlbum("destrangis","Destrangis","estopa");
        tm.addAlbum("allenrok","Allenrok","estopa");

        tm.addAlbum("thriller","Thriller","michaeljackson");
        tm.addAlbum("bad","Bad","michaeljackson");

        tm.addTrack("00000001","Vino Tinto", "estopa","destrangis");
        tm.addTrack("00000002","Luna Lunera", "estopa","destrangis");
        tm.addTrack("00000003","Partiendo La Pana", "estopa","destrangis");

        tm.addTrack("00000004","Cuando Amanece", "estopa","allenrok");
        tm.addTrack("00000005","Pesadilla", "estopa","allenrok");
        tm.addTrack("00000006","El Run Run", "estopa","allenrok");

        tm.addTrack("00000007","Billie Jean", "michaeljackson","thriller");
        tm.addTrack("00000008","Thriller", "michaeljackson","thriller");
        tm.addTrack("00000009","Beat It", "michaeljackson","thriller");

        tm.addTrack("00000010","Bad", "michaeljackson","bad");
        tm.addTrack("00000011","Smooth Criminal", "michaeljackson","bad");
        tm.addTrack("00000012","Man In The Mirror", "michaeljackson","bad");
    }

    @After
    public void tearDown(){
        tm.clear();
    }

    @Test
    public void addSingerTest(){
        tm.addSinger("fitoyfitipaldis","Fito y Fitipaldis");
        Assert.assertEquals(3,tm.singerSize());
    }

    @Test
    public void addAlbumTest() throws EmptyAlbumListException{
        tm.addAlbum("estopa","Estopa","estopa");
        Assert.assertEquals(5,tm.albumSize());
        Assert.assertEquals(3,tm.getAlbumsBySinger("estopa").size());
    }

    @Test
    public void addTrackTest() throws EmptyTrackListException{
        tm.addTrack("00000013","Me Falta el Aliento", "estopa","destrangis");
        tm.addTrack("00000014","Tragicomedia", "estopa","destrangis");
        tm.addTrack("00000015","Por La Raja de Tu Falda", "estopa","destrangis");
        Assert.assertEquals(15,tm.trackSize());
        Assert.assertEquals(6,tm.getTracksByAlbum("destrangis").size());
        Assert.assertEquals(9,tm.getTracksBySinger("estopa").size());
    }

    @Test
    public void getTrackTest() throws TrackNotFoundException {
        tm.addSinger("estopa","Estopa");
        tm.addAlbum("destrangis","Destrangis","estopa");
        tm.addTrack("00000001","Vino Tinto", "estopa","destrangis");
        Track t = tm.getTrack("00000001");
        Assert.assertEquals("Vino Tinto",t.getTitle());
        Assert.assertEquals("estopa",t.getSingerId());
        Assert.assertEquals("destrangis",t.getAlbumId());
    }

    @Test(expected = TrackNotFoundException.class)
    public void getTrackTestException() throws TrackNotFoundException {
        Track t = tm.getTrack("null");
    }

    @Test
    public void getAlbumTest() throws AlbumNotFoundException{
        Album a = tm.getAlbum("destrangis");
        Assert.assertEquals("Destrangis",a.getName());
        Assert.assertEquals("estopa",a.getSingerId());
    }

    @Test(expected = AlbumNotFoundException.class)
    public void getAlbumTestException() throws AlbumNotFoundException {
        Album a = tm.getAlbum("null");
    }

    @Test
    public void getSingerTest() throws SingerNotFoundException{
        Singer s = tm.getSinger("estopa");
        Assert.assertEquals("Estopa",s.getName());
    }

    @Test(expected = SingerNotFoundException.class)
    public void getSingerTestException() throws SingerNotFoundException {
        Singer s = tm.getSinger("null");
    }

    @Test(expected = EmptyTrackListException.class)
    public void findAllTracksTestException() throws EmptyTrackListException{
        this.tm.clear();
        List<Track> trackList = tm.findAllTracks();
    }

    @Test(expected = EmptyAlbumListException.class)
    public void findAllAlbumsTestException() throws EmptyAlbumListException{
        this.tm.clear();
        List<Album> albumList = tm.findAllAlbums();
    }

    @Test(expected = EmptySingerListException.class)
    public void findAllSingersTestException() throws EmptySingerListException{
        this.tm.clear();
        List<Singer> singerList = tm.findAllSingers();
    }

    @Test(expected = EmptyTrackListException.class)
    public void getTracksBySingerTestException() throws EmptyTrackListException{
        tm.deleteTrack("00000001");
        tm.deleteTrack("00000002");
        tm.deleteTrack("00000003");
        tm.deleteTrack("00000004");
        tm.deleteTrack("00000005");
        tm.deleteTrack("00000006");
        tm.deleteTrack("00000007");
        tm.deleteTrack("00000008");
        tm.deleteTrack("00000009");
        tm.deleteTrack("00000010");
        tm.deleteTrack("00000011");
        tm.deleteTrack("00000012");

        List<Track> trackList = tm.getTracksBySinger("estopa");
    }

    @Test(expected = EmptyTrackListException.class)
    public void getTracksByAlbumTestException() throws EmptyTrackListException{
        tm.deleteTrack("00000001");
        tm.deleteTrack("00000002");
        tm.deleteTrack("00000003");
        tm.deleteTrack("00000004");
        tm.deleteTrack("00000005");
        tm.deleteTrack("00000006");
        tm.deleteTrack("00000007");
        tm.deleteTrack("00000008");
        tm.deleteTrack("00000009");
        tm.deleteTrack("00000010");
        tm.deleteTrack("00000011");
        tm.deleteTrack("00000012");

        List<Track> trackList = tm.getTracksByAlbum("destrangis");
    }

    @Test(expected = EmptyAlbumListException.class)
    public void getAlbumsBySingerTestException() throws EmptyAlbumListException{
        tm.deleteAlbum("destrangis");
        tm.deleteAlbum("allenrok");
        tm.deleteAlbum("bad");
        tm.deleteAlbum("thriller");
        List<Album> albumList = tm.getAlbumsBySinger("estopa");
    }

    @Test
    public void trackSizeTest(){
        Assert.assertEquals(12,tm.trackSize());
    }

    @Test
    public void albumSizeTest(){
        Assert.assertEquals(4,tm.albumSize());
    }

    @Test
    public void singerSizeTest(){
        Assert.assertEquals(2,tm.singerSize());
    }

    @Test
    public void getTracksByAlbumTest() throws EmptyTrackListException{
        Assert.assertEquals(3,tm.getTracksByAlbum("destrangis").size());
    }

    @Test
    public void getTracksBySingerTest() throws EmptyTrackListException{
        Assert.assertEquals(6,tm.getTracksBySinger("michaeljackson").size());
    }

    @Test
    public void getAlbumsBySingerTest() throws EmptyAlbumListException{
        Assert.assertEquals(2,tm.getAlbumsBySinger("estopa").size());
    }

    @Test
    public void findAllAlbumsTest() throws EmptyAlbumListException{
        Assert.assertEquals(4, tm.findAllAlbums().size());
    }

    @Test
    public void findAllTracksTest() throws EmptyTrackListException{
        Assert.assertEquals(12,tm.findAllTracks().size());
    }

    @Test
    public void findAllSingersTest() throws EmptySingerListException{
        Assert.assertEquals(2, tm.findAllSingers().size());
    }

    @Test
    public void deleteTrackTest() throws EmptyTrackListException{
        tm.deleteTrack("00000010");
        Assert.assertEquals(11,tm.trackSize());
        Assert.assertEquals(2,tm.getTracksByAlbum("bad").size());
        Assert.assertEquals(5,tm.getTracksBySinger("michaeljackson").size());
    }

    @Test
    public void deleteSingerTest(){
        tm.deleteSinger("michaeljackson");
        Assert.assertEquals(1,tm.singerSize());
        Assert.assertEquals(6,tm.trackSize());
        Assert.assertEquals(2,tm.albumSize());
    }

    @Test
    public void deleteAlbumTest() throws EmptyAlbumListException{
        tm.deleteAlbum("allenrok");
        Assert.assertEquals(3,tm.albumSize());
        Assert.assertEquals(9,tm.trackSize());
        Assert.assertEquals(1,tm.getAlbumsBySinger("estopa").size());
    }

    @Test
    public void updateTrackTest() throws TrackNotFoundException{
        TrackTOUpdate t = new TrackTOUpdate("00000002","Demonios");
        tm.updateTrack(t);
        Assert.assertEquals("Demonios",tm.getTrack("00000002").getTitle());
    }

    @Test
    public void updateAlbumTest() throws AlbumNotFoundException{
        AlbumTOUpdate a = new AlbumTOUpdate("destrangis","Estopa");
        tm.updateAlbum(a);
        Assert.assertEquals("Estopa",tm.getAlbum("destrangis").getName());
    }

    @Test
    public void updateSingerTest() throws SingerNotFoundException{
        SingerTO s = new SingerTO("michaeljackson","michael");
        tm.updateSinger(s);
        Assert.assertEquals("michael",tm.getSinger("michaeljackson").getName());
    }

    @Test
    public void sendTrackTest() throws TrackNotFoundException {
        TrackTOGet t = tm.sendTrack(tm.getTrack("00000003"));
        Assert.assertEquals("00000003",t.getId());
        Assert.assertEquals("Partiendo La Pana",t.getTitle());
        Assert.assertEquals("Destrangis",t.getAlbum());
        Assert.assertEquals("Estopa",t.getSinger());
    }

    @Test
    public void sendSingerTest() throws SingerNotFoundException {
        SingerTO s = tm.sendSinger(tm.getSinger("estopa"));
        Assert.assertEquals("estopa",s.getId());
        Assert.assertEquals("Estopa",s.getName());
    }

    @Test
    public void sendAlbumTest() throws AlbumNotFoundException {
        AlbumTOGet a = tm.sendAlbum(tm.getAlbum("destrangis"));
        Assert.assertEquals("destrangis",a.getId());
        Assert.assertEquals("Destrangis",a.getName());
        Assert.assertEquals("Estopa",a.getSinger());
    }

    @Test
    public void sendInfoTrackTest() throws TrackNotFoundException {
        TrackTOInfo t = tm.sendInfoTrack(tm.getTrack("00000003"));
        Assert.assertEquals("00000003",t.getId());
        Assert.assertEquals("Partiendo La Pana",t.getTitle());
        Assert.assertEquals("Destrangis",t.getAlbum());
        Assert.assertEquals("Estopa",t.getSinger());
        Assert.assertEquals("destrangis",t.getAlbumId());
        Assert.assertEquals("estopa",t.getSingerId());
    }

    @Test
    public void sendInfoAlbumTest() throws AlbumNotFoundException {
        AlbumTOInfo a = tm.sendInfoAlbum(tm.getAlbum("destrangis"));
        Assert.assertEquals("destrangis",a.getId());
        Assert.assertEquals("Destrangis",a.getName());
        Assert.assertEquals("Estopa",a.getSinger());
        Assert.assertEquals("estopa",a.getSingerId());
        Assert.assertEquals(3,a.getTracks());
    }

    @Test
    public void sendInfoSingerTest() throws SingerNotFoundException {
        SingerTOInfo s = tm.sendInfoSinger(tm.getSinger("estopa"));
        Assert.assertEquals("estopa",s.getId());
        Assert.assertEquals("Estopa",s.getName());
        Assert.assertEquals(Integer.valueOf(2),s.getAlbums());
        Assert.assertEquals(Integer.valueOf(6),s.getTracks());
    }

    @Test
    public void sendTrackListTest() {
        List<TrackTOGet> list = tm.sendTrackList();
        Assert.assertEquals(12,list.size());
    }

    @Test
    public void sendAlbumListTest() {
        List<AlbumTOGet> list = tm.sendAlbumList();
        Assert.assertEquals(4,list.size());
    }

    @Test
    public void sendSingerListTest() {
        List<SingerTO> list = tm.sendSingerList();
        Assert.assertEquals(2,list.size());
    }

    @Test
    public void sendTracksByAlbumTest() throws AlbumNotFoundException{
        List<TrackTOUpdate> list = tm.sendTracksByAlbum(tm.getAlbum("allenrok"));
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void sendTracksBySingerTest() throws SingerNotFoundException{
        List<TrackTOBySinger> list = tm.sendTracksBySinger(tm.getSinger("estopa"));
        Assert.assertEquals(6,list.size());
    }

    @Test
    public void sendAlbumsBySingerTest() throws SingerNotFoundException{
        List<AlbumTOUpdate> list = tm.sendAlbumsBySinger(tm.getSinger("michaeljackson"));
        Assert.assertEquals(2,list.size());
    }
}
