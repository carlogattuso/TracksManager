package edu.upc.dsa.TracksManagerServerTest;

import edu.upc.dsa.Main;
import edu.upc.dsa.models.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlbumsServiceTest {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() {
        server.shutdown();
    }

    @Test
    public void getAlbumsTest() {
        Response response = target.path("/albums/").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<AlbumTOGet> list = response.readEntity(new GenericType<List<AlbumTOGet>>() {
        });
        Assert.assertNotNull("should return album list", list);
        Assert.assertEquals(4,list.size());
    }

    @Test
    public void getAlbumTest() {
        Response response = target.path("/albums/bad").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        AlbumTOInfo a = response.readEntity(new GenericType<AlbumTOInfo>() {
        });
        Assert.assertNotNull("Should return an album", a);
        Assert.assertEquals("bad",a.getId());
    }

    @Test
    public void postAlbumTest() {
        AlbumTOPost a = new AlbumTOPost("rumbaalodesconocido","Rumba A Lo Desconocido","estopa");
        Entity<AlbumTOPost> entity = Entity.entity(a, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/albums/").request().post(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        AlbumTOPost album = response.readEntity(new GenericType<AlbumTOPost>() {
        });
        assertEquals("rumbaalodesconocido",album.getId());
    }

    @Test
    public void deleteTrackTest() {
        Response response = target.path("/albums/thriller").request().delete();
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/albums/").request().get();
        List<AlbumTOGet> list = response2.readEntity(new GenericType<List<AlbumTOGet>>() {
        });
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void putTrackTest() {
        AlbumTOUpdate a = new AlbumTOUpdate("destrangis","Pepito");
        Entity<AlbumTOUpdate> entity = Entity.entity(a, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/albums/").request().put(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/albums/destrangis").request().get();
        AlbumTOInfo album = response2.readEntity(new GenericType<AlbumTOInfo>() {
        });
        Assert.assertEquals("Pepito",album.getName());
    }

    @Test
    public void getTracksByAlbum() {
        Response response = target.path("/albums/destrangis/tracks").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<TrackTOUpdate> list = response.readEntity(new GenericType<List<TrackTOUpdate>>() {
        });
        Assert.assertEquals(3,list.size());
    }
}