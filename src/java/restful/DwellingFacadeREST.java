package restful;

import com.sun.istack.logging.Logger;
import entities.Dwelling;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public DwellingFacadeREST() {
        super(Dwelling.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Dwelling entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Dwelling entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Dwelling find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("rate/{rate}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findByMinRating(@PathParam("rate") Short rate) {
        List<Dwelling> dwellings = null;
        try {
            LOGGER.log(Level.INFO, "Getting the dwellings by min Rating {0}", rate);
            dwellings = new ArrayList<>(em.createNamedQuery("findByMinRating").setParameter("rate", rate).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new 
        }
        return dwellings;
    }
    
    @GET
    @Path("minConstructionDate/{date}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Dwelling> findByMinConstructionDate(@PathParam("date") Date date) {
        List<Dwelling> dwellings = null;
        try {
            LOGGER.log(Level.INFO, "Getting the dwellings by min ConstructionDate {0}", date);
            dwellings = new ArrayList<>(em.createNamedQuery("findByMinConstructionDate").setParameter("date", date).getResultList());
        } catch (Exception e) {
            //LOGGER.log(Level.SEVERE,);
            //throw new 
        }
        return dwellings;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
