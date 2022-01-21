package restful;

import com.sun.istack.logging.Logger;
import entities.Dwelling;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
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
 * RESTful service for Dwelling.
 *
 * @author Ander Arruza
 */
@Stateless
@Path("entities.dwelling")
public class DwellingFacadeREST extends AbstractFacade<Dwelling> {

    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    /**
     * Logger for this class.
     */
    private final Logger LOGGER = Logger.getLogger(DwellingFacadeREST.class);

    /**
     * Constructor of the DwellingFacadeREST object
     */
    public DwellingFacadeREST() {
        super(Dwelling.class);
    }

    /**
     * POST method to create dwellings: uses create business logic method.
     *
     * @param entity the new Dwelling object
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Dwelling entity) {
        LOGGER.info("Creating entity dwelling");
        super.create(entity);
    }

    /**
     * PUT method to edit dwelling: uses
     *
     * @param id the id of the dwelling
     * @param entity the entity overriding the previous one
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Dwelling entity) {
        LOGGER.info("Editting entity dwelling");
        super.edit(entity);
    }

    /**
     * DELETE method that deletes a dwelling
     *
     * @param id the id of the dwelling
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        LOGGER.info("Removing entity dwelling");
        super.remove(super.find(id));
    }

    /**
     * GET method for getting a dwelling by its id
     *
     * @param id The dwelling id
     * @return A Dwelling object
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Dwelling find(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Getting Dwelling with id {0}", id);
        return super.find(id);
    }

    /**
     * GET method that returns all the dwellings
     *
     * @return all the dwellings
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findAll() {
        LOGGER.info("Getting all the Dwellings");
        return super.findAll();
    }

    /**
     * GET method that returns a list with the expecified number in range
     *
     * @param from the start value
     * @param to the finish value
     * @return the dwelling list
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * GET method that finds all the dwelling starting with a minimun rating
     *
     * @param rate the minimun rate
     * @return the dwelling higher than the minimum rate
     */
    @GET
    @Path("minRate/{rate}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findByMinRating(@PathParam("rate") String rate) {
        List<Dwelling> dwellings = null;
        try {
            LOGGER.log(Level.INFO, "Getting the dwellings by min Rating {0}", rate);
            dwellings = new ArrayList<>(em.createNamedQuery("findByMinRating").setParameter("rate", Float.valueOf(rate)).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new 
        }
        return dwellings;
    }

    /**
     * GET method that returns all the dwellings by the minimum construction
     * date
     *
     * @param date the minimum construction date
     * @return all the dwelling's higher than the minimum construction date
     */
    @GET
    @Path("minConstructionDate/{date}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findByMinConstructionDate(@PathParam("date") String date) {
        List<Dwelling> dwellings = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateParsed = simpleDateFormat.parse(date);
            date = simpleDateFormat.format(dateParsed);
            LOGGER.log(Level.INFO, "Getting the dwellings by min ConstructionDate {0}", date); //throw new
            dwellings = new ArrayList<>(em.createNamedQuery("findByMinConstructionDate")
                    .setParameter("date", dateParsed).getResultList());

        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DwellingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dwellings;
    }

    /**
     * GET method that returns the amount of all the dwellings
     *
     * @return the number of Dwellings as String
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

}
