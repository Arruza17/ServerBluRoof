
package restful;

import entities.Facility;
import entities.FacilityType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
 * RESTful service for Facility.
 *
 * @author Adrián
 */
@Stateless
@Path("entities.facility")
public class FacilityFacadeREST extends AbstractFacade<Facility> {

     /**
     * Logger for this class.
     */
    private final Logger LOGGER = Logger.getLogger(FacilityFacadeREST.class.getName());

     /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    /**
     * Constructor of the ServiceFacadeREST object
     */
    public FacilityFacadeREST() {
        super(Facility.class);
    }

    /**
     * POST method to create a facility.
     *
     * @param entity The new facility.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Facility entity) {
        entity.setId(null);
        super.create(entity);
    }

    /**
     * PUT method to edit a service.
     *
     * @param id The id of the facility.
     * @param entity The new service that overrides the previous one.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Facility entity) {
        super.edit(entity);
    }

    /**
     * DELETE method that deletes a facility.
     *
     * @param id The id of the facility.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * GET method for getting a facility by its id
     *
     * @param id The facility id.
     * @return A facility object
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Facility find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * GET method that returns all the facility.
     *
     * @return all the facility.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findAll() {
        return super.findAll();
    }

    /**
     * GET method that returns a list with the expecified number in range
     *
     * @param from the start value
     * @param to the finish value
     * @return the facility list
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * GET method that returns the amount of all the facility.
     *
     * @return the number of facility as String.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Gets the entity Manager
     *
     * @return the entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * GET method that returns the facilities with an specific adquisition date.
     * @param date The facility adquisition date.
     * @return all the facilities with a specific adquisition date.
     */
    
    @GET
    @Path("adquisitionDate/{date}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findFacilityByAdqDate(@PathParam("date") Date date) {
        List<Facility> facilities = null;
        try {
            LOGGER.log(Level.INFO, "Getting the facilities by adquisition date {0}", date);
            facilities = new ArrayList<>(em.createNamedQuery("findFacilityByAdqDate").setParameter("date", date).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return facilities;
    }

    /**
     * GET method that returns the facilities of a specific type.
     * @param facilityType The type of facility.
     * @return all the facilities of a specific type.
     */
    @GET
    @Path("type/{facilityType}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findFacilityByType(@PathParam("facilityType") String facilityType) {
        List<Facility> facilities = null;
        try {
            LOGGER.log(Level.INFO, "Getting the facilities by type {0}", facilityType);
            FacilityType type = FacilityType.valueOf(facilityType);
            facilities = new ArrayList<>(em.createNamedQuery("findFacilityByType").setParameter("facilityType", type).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return facilities;
    }
}
