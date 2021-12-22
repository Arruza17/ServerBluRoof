package restful;

import entities.Owner;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * RESTful service for Owners.
 *
 * @author Yeray Sampedro
 */
@Stateless
@Path("entities.owner")
public class OwnerFacadeREST extends AbstractFacade<Owner> {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(OwnerFacadeREST.class.getName());
    /**
     * EJB object implementing the bussiness logic
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public OwnerFacadeREST() {
        super(Owner.class);
    }

    /**
     * Method that creates an owner
     *
     * @param entity the owner to be crated
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Owner entity) {
        super.create(entity);
    }

    /**
     * Method used to edit data of an owner
     *
     * @param id the id of the owner to be edited
     * @param entity the owner with the modified data
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Owner entity) {
        super.edit(entity);
    }

    /**
     * Deletes information about an owner by id
     *
     * @param id the id of the owner to be removed
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Method that gets the data of an specific owner by id
     *
     * @param id the id of the owner
     * @return owner the owner to be found
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Owner find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Method that finds ALL owners
     *
     * @return a list of owners
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Owner> findAll() {
        return super.findAll();
    }

    /**
     * Method used to page all the information
     *
     * @param from the first element of the pagination
     * @param to the last element of the pagination
     * @return paginated data
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Owner> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Method used to find the data of an owner of an specific dwelling
     *
     * @param host the owner to get the information from
     */
    @GET
    @Path("owner/{dwellingId}")
    @Consumes({MediaType.APPLICATION_XML})
    public Owner findOwnerByDwelling(@PathParam("dwellingId") Long id) {
        Owner owner = null;
        try {
            LOGGER.info("Searching for the owner");
            // SELECT o FROM Owner o WHERE o = (SELECT d.host FROM Dwelling d WHERE d.id = :id)
            owner = (Owner) em.createNamedQuery("findOwnerByDwelling").setParameter("dwellingId", id).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "OwnerEJB --> findOwnerByDwelling():{0}", e.getLocalizedMessage());
            throw new NotAuthorizedException(e);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "OwnerEJB --> findOwnerByDwelling():{0}", e.getLocalizedMessage());
            //Throw new read exception
        }
        return owner;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
