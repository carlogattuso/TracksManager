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

@Api(value = "/singers", description = "Endpoint to Singers Service")
@Path("/singers")
public class SingersService {

    private TracksManager tm;

    public SingersService() {
        this.tm = TracksManagerImpl.getInstance();
        if(tm.singerSize()==0){

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
    @ApiOperation(value = "get all Singers", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = SingerTO.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "No content", responseContainer="List")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingers() throws EmptySingerListException {
        try {
            List<Singer> singers = this.tm.findAllSingers();
        }
        catch (EmptySingerListException e){
            return Response.status(204).build();
        }
        GenericEntity<List<SingerTO>> entity = new GenericEntity<List<SingerTO>>(this.tm.sendSingerList()) {
        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = SingerTOInfo.class),
            @ApiResponse(code = 404, message = "Singer not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSinger(@PathParam("id") String id) throws SingerNotFoundException {
        try {
            Singer s = this.tm.getSinger(id);
            return Response.status(201).entity(this.tm.sendInfoSinger(s)).build();
        }
        catch(SingerNotFoundException e) {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get tracks by Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = TrackTOBySinger.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Singer not found")
    })
    @Path("/{id}/tracks/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksBySinger(@PathParam("id") String id) throws SingerNotFoundException, EmptyTrackListException {
        Singer s;
        List<Track> trackList = new LinkedList<>();
        try {
            s = this.tm.getSinger(id);
            trackList = this.tm.getTracksBySinger(id);
        }
        catch(SingerNotFoundException e1) {
            return Response.status(404).build();
        }
        catch (EmptyTrackListException e2) {
            return Response.status(204).build();
        }
        GenericEntity<List<TrackTOBySinger>> entity = new GenericEntity<List<TrackTOBySinger>>(this.tm.sendTracksBySinger(s)) {
        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get albums by Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = AlbumTOUpdate.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Singer not found")
    })
    @Path("/{id}/albums/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbumsBySinger(@PathParam("id") String id) throws SingerNotFoundException, EmptyAlbumListException {
        Singer s;
        List<Album> albumList = new LinkedList<>();
        try {
            s = this.tm.getSinger(id);
            albumList = this.tm.getAlbumsBySinger(id);
        }
        catch(SingerNotFoundException e1) {
            return Response.status(404).build();
        }
        catch (EmptyAlbumListException e2) {
            return Response.status(204).build();
        }
        GenericEntity<List<AlbumTOUpdate>> entity = new GenericEntity<List<AlbumTOUpdate>>(this.tm.sendAlbumsBySinger(s)) {
        };
        return Response.status(201).entity(entity).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Singer not found")
    })
    @Path("/{id}")
    public Response deleteSinger(@PathParam("id") String id) throws SingerNotFoundException {
        try {
            Singer s = this.tm.getSinger(id);
            this.tm.deleteSinger(id);
            return Response.status(201).build();
        }
        catch(SingerNotFoundException e){
            return Response.status(404).build();
        }
    }

    @PUT
    @ApiOperation(value = "update the name of a Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Singer not found")
    })
    public Response updateAlbum(SingerTO s) throws SingerNotFoundException{
        try {
            Singer singer = this.tm.getSinger(s.getId());
            this.tm.updateSinger(s);
            return Response.status(201).build();
        }
        catch(SingerNotFoundException e){
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new Singer", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=AlbumTOPost.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 409, message = "Duplicated Singer"),
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newSinger(SingerTO singer) throws SingerNotFoundException {
        if (singer.getId() == null || singer.getName() == null)
            return Response.status(500).build();
        else {
            try {
                Singer s = this.tm.getSinger(singer.getId());
                return Response.status(409).build();
            } catch (SingerNotFoundException e2) {
                this.tm.addSinger(singer.getId(), singer.getName());
                return Response.status(201).entity(singer).build();
            }
        }
    }
}
