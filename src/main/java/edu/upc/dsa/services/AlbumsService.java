package edu.upc.dsa.services;

import edu.upc.dsa.TracksManager;
import edu.upc.dsa.TracksManagerImpl;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/albums", description = "Endpoint to Track Service")
@Path("/albums")
public class AlbumsService {

    private TracksManager tm;

    public AlbumsService() {
        this.tm = TracksManagerImpl.getInstance();
        if(tm.albumSize()==0){

            tm.addSinger("estopa","Estopa");
            tm.addSinger("michaeljackson","Michael Jackson");

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
    }

    @GET
    @ApiOperation(value = "get all Albums", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = AlbumTOGet.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "No content", responseContainer="List")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbums() throws EmptyAlbumListException {
        try {
            List<Album> albums = this.tm.findAllAlbums();
        }
        catch (EmptyAlbumListException e){
            return Response.status(204).build();
        }
        GenericEntity<List<AlbumTOGet>> entity = new GenericEntity<List<AlbumTOGet>>(this.tm.sendAlbumList()) {
        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get an Album", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Album.class),
            @ApiResponse(code = 404, message = "Album not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbum(@PathParam("id") String id) {
        Album a;
        try {
            a = this.tm.getAlbum(id);
        }
        catch(AlbumNotFoundException e) {
            return Response.status(404).build();
        }
        GenericEntity<Album> entity = new GenericEntity<Album>(a) {
        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get tracks by Album", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = TrackTOUpdate.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Album not found")
    })
    @Path("/{id}/tracks/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksByAlbum(@PathParam("id") String id) throws AlbumNotFoundException, EmptyTrackListException {
        Album a;
        List<Track> trackList = new LinkedList<>();
        try {
             a = this.tm.getAlbum(id);
             trackList = this.tm.getTracksByAlbum(id);
        }
        catch(AlbumNotFoundException e1) {
            return Response.status(404).build();
        }
        catch (EmptyTrackListException e2) {
            return Response.status(204).build();
        }
        GenericEntity<List<TrackTOUpdate>> entity = new GenericEntity<List<TrackTOUpdate>>(this.tm.sendTracksByAlbum(a)) {
        };
        return Response.status(201).entity(entity).build();
    }

    @DELETE
    @ApiOperation(value = "delete an Album", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Album not found")
    })
    @Path("/{id}")
    public Response deleteAlbum(@PathParam("id") String id) throws AlbumNotFoundException {
        try {
            Album a = this.tm.getAlbum(id);
            this.tm.deleteAlbum(id);
            return Response.status(201).build();
        }
        catch(AlbumNotFoundException e){
            return Response.status(404).build();
        }
    }

    @PUT
    @ApiOperation(value = "update the name of an Album", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    public Response updateAlbum(AlbumTOUpdate t) throws AlbumNotFoundException{
        try {
            Album album = this.tm.getAlbum(t.getId());
            this.tm.updateAlbum(t);
            return Response.status(201).build();
        }
        catch(AlbumNotFoundException e){
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new Album", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=AlbumTOPost.class),
            @ApiResponse(code = 404, message = "Singer not found"),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 409, message = "Duplicated Album"),
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newAlbum(AlbumTOPost album) throws AlbumNotFoundException, SingerNotFoundException {
        if (album.getId() == null || album.getName() == null)
            return Response.status(500).build();
        else {
            try {
                Singer s = this.tm.getSinger(album.getSingerId());
                Album a = this.tm.getAlbum(album.getId());
                return Response.status(409).build();
            } catch (AlbumNotFoundException e1) {
                this.tm.addAlbum(album.getId(), album.getName(), album.getSingerId());
                return Response.status(201).entity(album).build();
            } catch (SingerNotFoundException e2) {
                return Response.status(404).build();
            }
        }
    }
}
