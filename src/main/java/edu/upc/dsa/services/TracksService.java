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
import java.util.List;

@Api(value = "/tracks", description = "Endpoint to Track Service")
@Path("/tracks")
public class TracksService {

    private TracksManager tm;

    public TracksService() {
        this.tm = TracksManagerImpl.getInstance();
        if (tm.trackSize()==0) {

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
    @ApiOperation(value = "get all Tracks", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = TrackTOGet.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "No content", responseContainer="List")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks() {
        try {
            List<Track> tracks = this.tm.findAllTracks();
        }
        catch (EmptyTrackListException e){
            return Response.status(204).build();
        }
        GenericEntity<List<TrackTOGet>> entity = new GenericEntity<List<TrackTOGet>>(this.tm.sendTrackList()) {
            };
            return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = TrackTOInfo.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) throws TrackNotFoundException {
        try {
            Track t = this.tm.getTrack(id);
            return Response.status(201).entity(this.tm.sendInfoTrack(t)).build();
        }
        catch(TrackNotFoundException e) {
            return Response.status(404).build();
        }
    }

    @DELETE
    @ApiOperation(value = "delete a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    public Response deleteTrack(@PathParam("id") String id) throws TrackNotFoundException {
        try {
            Track t = this.tm.getTrack(id);
            this.tm.deleteTrack(id);
            return Response.status(201).build();
        }
        catch(TrackNotFoundException e){
            return Response.status(404).build();
        }
    }

    @PUT
    @ApiOperation(value = "update the title of a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    public Response updateTrack(TrackTOUpdate t) throws TrackNotFoundException{
        try {
            Track track = this.tm.getTrack(t.getId());
            this.tm.updateTrack(t);
            return Response.status(201).build();
        }
        catch(TrackNotFoundException e){
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Track.class),
            @ApiResponse(code = 409, message = "Conflict (Duplicated track)"),
            @ApiResponse(code = 404, message = "Album not found"),
            @ApiResponse(code = 405, message = "Singer not found"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Track track) throws AlbumNotFoundException, SingerNotFoundException, TrackNotFoundException{
        if (track.getId()==null || track.getTitle()==null || track.getSingerId() == null || track.getAlbumId() == null) return Response.status(500).build();
        else {
            try {
                Singer s = this.tm.getSinger(track.getSingerId());
                Album a = this.tm.getAlbum(track.getAlbumId());
                Track t = this.tm.getTrack(track.getId());
                return Response.status(409).build();
            } catch (AlbumNotFoundException e1) {
                return Response.status(404).build();
            } catch (SingerNotFoundException e2) {
                return Response.status(405).build();
            } catch (TrackNotFoundException e3){
                this.tm.addTrack(track.getId(), track.getTitle(), track.getSingerId(), track.getAlbumId());
                return Response.status(201).entity(track).build();
            }
        }
    }
}