package restful;

import entities.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;

/**
 *RESTFUL Service for flat
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
    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public RoomFacadeREST() {
        super(Room.class);
    }

    /**
     * POST method to create Rooms
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
 * @param from parameter of start
 * @param to parameter of end
 * @return  
 */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    /**
     * Get method for getting amount of rooms in plain text
     *
     * @return returns the number of Rooms as String
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    /**
     * Get method for getting a list of the Rooms who have x number of outlets
     * @param outlets Short with n of outlets
     * @return  list with rooms that have x number of outlets
     */
    @GET
    @Path("nOutlets/{outlets}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRoomsByNOutlets(@PathParam("outlets")Short outlets){
        List<Room> resultado=null;
        try {
             LOGGER.log(Level.INFO, "Getting the flats by number of Rooms {0}", outlets);
            resultado=new ArrayList<>(em.createNamedQuery("findRoomsByNOutlets").setParameter("outlets",outlets).getResultList());
        } catch (ServiceUnavailableException ex) {
            throw new ServiceUnavailableException();
        }catch(InternalServerErrorException ex){
        throw new InternalServerErrorException();
        }
        return resultado;
    }
    /**
     * Get method for getting a list of Rooms who have natural light
     * @param naturalLight Short with the value of natural light
     * @return list with rooms that have or dont have light
     */
    @GET
    @Path("naturalLight/{naturalLight}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Room> findRoomsWNaturalLight(@PathParam("naturalLight")Short naturalLight){
        List<Room> resultado=null;
        try {
              LOGGER.log(Level.INFO, "Getting the flats by number of Rooms {0}", naturalLight);
            resultado=new ArrayList<>(em.createNamedQuery("findRoomsWNaturalLight").setParameter("naturalLight",naturalLight).getResultList());
        } catch (ServiceUnavailableException ex) {
            throw new ServiceUnavailableException();
        }catch(InternalServerErrorException ex){
        throw new InternalServerErrorException();
        }
        return resultado;
    }
    /**
     * Gets the Entity Manager
     * @return entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
