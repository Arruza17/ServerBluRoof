/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jorge
 */
@Stateless
@Path("entities.room")
public class RoomFacadeREST extends AbstractFacade<Room> {

    /**
     * Logger for this class.
     */
    private Logger LOGGER = Logger.getLogger(RoomFacadeREST.class.getName());

    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public RoomFacadeREST() {
        super(Room.class);
    }

    /**
     * POST method to create Rooms
     *
     * @param entity the Room object containing the data
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Room entity) {
        super.create(entity);
    }

    /**
     * PUT method to modify a room
     * @param entity the Room object containing the data
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Room entity) {
        super.edit(entity);
    }
    /**
     *  Delete method to remove a Room by id
     * @param id the id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }
    /**
     * GET method for getting a Room by its id
     * @param id The Room id.
     * @return  id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Room find(@PathParam("id") Long id) {
        return super.find(id);
    }
/**
 * GET method for getting a list of all rooms
 * @return returns a list with all rooms
 */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findAll() {
        return super.findAll();
    }
/**
 * GET method for getting a range of rooms
 * @param from 
 * @param to
 * @return 
 */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
/**
 * 
 * @return 
 */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    /**
     * 
     * @param outlets
     * @return 
     */
    @GET
    @Path("nOutlets/{outlets}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRoomsByNOutlets(@PathParam("outlets")Short outlets){
        List<Room> resultado=null;
        try {
            resultado=new ArrayList<>(em.createNamedQuery("findRoomsByNOutlets").setParameter("outlets",outlets).getResultList());
        } catch (Exception e) {
        }
        return resultado;
    }
    @GET
    @Path("naturalLight/{naturalLight}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRoomsWNaturalLight(@PathParam("naturalLight")Short naturalLight){
        List<Room> resultado=null;
        try {
            resultado=new ArrayList<>(em.createNamedQuery("findRoomsWNaturalLight").setParameter("naturalLight",naturalLight).getResultList());
        } catch (Exception e) {
        }
        return resultado;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
