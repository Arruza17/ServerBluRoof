package restful;

import entities.Dwelling;
import entities.Neighbourhood;
import entities.Service;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * RESTful service for Neighbourhood.
 *
 * @author Adrián
 */
@Stateless
@Path("entities.neighbourhood")
public class NeighbourhoodFacadeREST extends AbstractFacade<Neighbourhood> {

    /**
     * Logger for this class.
     */
    private final Logger LOGGER = Logger.getLogger(NeighbourhoodFacadeREST.class.getName());

    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    /**
     * Constructor of the NeighbourhoodFacadeREST object
     */
    public NeighbourhoodFacadeREST() {
        super(Neighbourhood.class);
    }

    /**
     * POST method to create a neighbourhood.
     *
     * @param entity The new neighbourhood.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Neighbourhood entity) {
        super.create(entity);
    }

    /**
     * PUT method to edit a neighbourhood.
     *
     * @param id The id of the neighbourhood.
     * @param entity The new neighbourhood that overrides the previous one.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Neighbourhood entity) {
        super.edit(entity);
    }

    /**
     * DELETE method that deletes a neighbourhood.
     *
     * @param id The id of the neighbourhood.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * GET method for getting a neighbourhood by its id
     *
     * @param id The neighbourhood id.
     * @return A neighbourhood object
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Neighbourhood find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * GET method that returns all the neighbourhoods.
     *
     * @return all the neighbourhood.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Neighbourhood> findAll() {
        return super.findAll();
    }

    /**
     * GET method that returns a list with the expecified number in range
     *
     * @param from the start value
     * @param to the finish value
     * @return the neighbourhood list
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Neighbourhood> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * GET method that returns the amount of all the neighbourhoods
     *
     * @return the number of neighbourhoods as String
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
     * GET method that returns all dwellings of a neighbourhood with an specific
     * name.
     *
     * @param neighbourhoodName The name of the neighbourhood.
     * @return all the dwellings of a neighbourhood.
     */
    @GET
    @Path("DwellingName/{neighbourhoodName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findNeighbourhoodDwelligs(@PathParam("neighbourhoodName") String neighbourhoodName) {
        List<Dwelling> dwellings = null;
        try {
            LOGGER.log(Level.INFO, "Getting the neighbourhood dwellings {0}", neighbourhoodName);

            dwellings = new ArrayList<>(em.createNamedQuery("findNeighbourhoodDwelligs").setParameter("neighbourhoodName", neighbourhoodName).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return dwellings;
    }

    /**
     * GET method that returns all dwellings of a neighbourhood with an specific
     * name.
     *
     * @param neighbourhoodName The name of the neighbourhood.
     * @return all the dwellings of a neighbourhood.
     */
    @GET
    @Path("ServiceName/{neighbourhoodName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findNeighbourhoodServices(@PathParam("neighbourhoodName") String neighbourhoodName) {
        List<Service> dwellings = null;
        try {
            LOGGER.log(Level.INFO, "Getting the Neighbourhood services  {0}", neighbourhoodName);

            dwellings = new ArrayList<>(em.createNamedQuery("findNeighbourhoodServices").setParameter("neighbourhoodName", neighbourhoodName).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return dwellings;
    }

}
