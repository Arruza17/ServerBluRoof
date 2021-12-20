package restful;

import entities.Flat;
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
 *
 * @author jorge
 */
@Stateless
@Path("entities.flat")
public class FlatFacadeREST extends AbstractFacade<Flat> {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(RoomFacadeREST.class.getName());
    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public FlatFacadeREST() {
        super(Flat.class);
    }

    /**
     * Post method to create a Flat
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Flat entity) {
        super.create(entity);
    }

    /**
     * Put method to edit a room by its id
     *
     * @param id contains the id
     * @param entity the Flat object containing the data
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Flat entity) {
        super.edit(entity);
    }

    /**
     * DELETE method to remove flats
     *
     * @param id The id for the flat to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Get method to obtain Flat by id
     *
     * @param id The Flat id
     * @return the Flat by its id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Flat find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Get method to obtain a list with all flats
     *
     * @return returns a list with all flats
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findAll() {
        return super.findAll();
    }

    /**
     * GET method for getting a range of flats
     *
     * @param from parameter of start
     * @param to parameter of end
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Get method for getting a List of flats with x number of bathrooms
     *
     * @param nBathrooms number of bathrooms
     * @return a list of flats
     */
    @GET
    @Path("nBathrooms/{nBathrooms}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findFlatByNofBathrooms(@PathParam("nBathrooms") Short nBathrooms) {
        List<Flat> resultado = null;
        try {
            LOGGER.log(Level.INFO, "Getting the flats by number of bathrooms {0}", nBathrooms);
            resultado = new ArrayList<>(em.createNamedQuery("findFlatByNofBathrooms").setParameter("nBathrooms", nBathrooms).getResultList());
        } catch (ServiceUnavailableException ex) {
            throw new ServiceUnavailableException();
        }catch(InternalServerErrorException ex){
        throw new InternalServerErrorException();
        }
        return resultado;
    }

    /**
     * Get method for getting a List of flats with x number of rooms
     *
     * @param nRooms number of rooms
     * @return a list of rooms
     */
    @GET
    @Path("nRooms/{nRooms}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findByNofRooms(@PathParam("nRooms") Short nRooms) {
        List<Flat> resultado = null;
        try {
            LOGGER.log(Level.INFO, "Getting the flats by number of Rooms {0}", nRooms);
            resultado = new ArrayList<>(em.createNamedQuery("findByNofRooms").setParameter("nRooms", nRooms).getResultList());
        } catch (ServiceUnavailableException ex) {
            throw new ServiceUnavailableException();
        }catch(InternalServerErrorException ex){
        throw new InternalServerErrorException();
        }
        return resultado;
    }

    /**
     * Get method for getting amount of Flat in plain text
     *
     * @return returns the number of Flat as String
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
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
