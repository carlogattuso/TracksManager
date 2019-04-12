package edu.upc.dsa.TracksManagerServerTest;

import edu.upc.dsa.Main;
import edu.upc.dsa.models.Track;
import edu.upc.dsa.models.TrackTOGet;
import edu.upc.dsa.models.TrackTOInfo;
import edu.upc.dsa.models.TrackTOUpdate;
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

public class TracksServiceTest {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
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
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getTracksTest() {
        Response response = target.path("/tracks/").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<TrackTOGet> list = response.readEntity(new GenericType<List<TrackTOGet>>() {
        });
        Assert.assertNotNull("should return track list", list);
        Assert.assertEquals(12,list.size());
    }

    @Test
    public void getTrackTest() {
        Response response = target.path("/tracks/00000001").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        TrackTOInfo t = response.readEntity(new GenericType<TrackTOInfo>() {
        });
        Assert.assertNotNull("Should return a track", t);
        Assert.assertEquals("00000001",t.getId());
    }

    @Test
    public void postTrackTest() {
        Track t = new Track("00000013","Tragicomedia","estopa","allenrok");
        Entity<Track> entity = Entity.entity(t, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/tracks/").request().post(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        Track track = response.readEntity(new GenericType<Track>() {
        });
        Assert.assertNotNull("should return the added track" ,track);
        assertEquals("00000013",track.getId());
    }

    @Test
    public void deleteTrackTest() {
        Response response = target.path("/tracks/00000001").request().delete();
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/tracks/").request().get();
        List<TrackTOGet> list = response2.readEntity(new GenericType<List<TrackTOGet>>() {
        });
        Assert.assertEquals(11,list.size());
    }

    @Test
    public void putTrackTest() {
        TrackTOUpdate t = new TrackTOUpdate("00000005","Pepito");
        Entity<TrackTOUpdate> entity = Entity.entity(t, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/tracks/").request().put(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/tracks/00000005").request().get();
        TrackTOInfo track = response2.readEntity(new GenericType<TrackTOInfo>() {
        });
        Assert.assertEquals("Pepito",track.getTitle());
    }
}
