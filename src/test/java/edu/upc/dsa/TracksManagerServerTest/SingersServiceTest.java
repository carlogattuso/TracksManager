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

public class SingersServiceTest {
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
    public void getSingersTest() {
        Response response = target.path("/singers/").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<SingerTO> list = response.readEntity(new GenericType<List<SingerTO>>() {
        });
        Assert.assertNotNull("should return singer list", list);
        Assert.assertEquals(2,list.size());
    }

    @Test
    public void getSingerTest() {
        Response response = target.path("/singers/estopa").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        SingerTOInfo s = response.readEntity(new GenericType<SingerTOInfo>() {
        });
        Assert.assertNotNull("Should return a singer", s);
        Assert.assertEquals("estopa",s.getId());
    }

    @Test
    public void postSingerTest() {
        SingerTO a = new SingerTO("brunomars","Bruno Mars");
        Entity<SingerTO> entity = Entity.entity(a, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/singers/").request().post(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        SingerTO singer = response.readEntity(new GenericType<SingerTO>() {
        });
        assertEquals("brunomars",singer.getId());
    }

    @Test
    public void deleteSingerTest() {
        Response response = target.path("/singers/michaeljackson").request().delete();
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/singers/").request().get();
        List<SingerTO> list = response2.readEntity(new GenericType<List<SingerTO>>() {
        });
        Assert.assertEquals(1,list.size());
    }

    @Test
    public void putTrackTest() {
        AlbumTOUpdate a = new AlbumTOUpdate("estopa","Pepito");
        Entity<AlbumTOUpdate> entity = Entity.entity(a, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.path("/singers/").request().put(entity);
        assertEquals("should return status 201", 201, response.getStatus());
        Response response2 = target.path("/singers/estopa").request().get();
        SingerTOInfo singer = response2.readEntity(new GenericType<SingerTOInfo>() {
        });
        Assert.assertEquals("Pepito",singer.getName());
    }

    @Test
    public void getTracksBySinger() {
        Response response = target.path("/singers/michaeljackson/tracks").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<TrackTOBySinger> list = response.readEntity(new GenericType<List<TrackTOBySinger>>() {
        });
        Assert.assertEquals(6,list.size());
    }

    @Test
    public void getAlbumsBySinger() {
        Response response = target.path("/singers/michaeljackson/albums").request().get();
        assertEquals("should return status 201", 201, response.getStatus());
        List<AlbumTOUpdate> list = response.readEntity(new GenericType<List<AlbumTOUpdate>>() {
        });
        Assert.assertEquals(2,list.size());
    }
}