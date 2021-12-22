package restful;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.FlatFacility;
import entities.FlatfacilityId;
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
import javax.ws.rs.core.PathSegment;

/**
 * RESTFUL service for flatfacility
 * 
 * @author jorge
 */
@Stateless
@Path("entities.flatfacility")
public class FlatFacilityFacadeREST extends AbstractFacade<FlatFacility> {

      /**
     * Logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(RoomFacadeREST.class.getName());
    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    private FlatfacilityId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;flatId=flatIdValue;facilityId=facilityIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.FlatfacilityId key = new entities.FlatfacilityId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> flatId = map.get("flatId");
        if (flatId != null && !flatId.isEmpty()) {
            key.setFlatId(new java.lang.Long(flatId.get(0)));
        }
        java.util.List<String> facilityId = map.get("facilityId");
        if (facilityId != null && !facilityId.isEmpty()) {
            key.setFacilityId(new java.lang.Long(facilityId.get(0)));
        }
        return key;
    }

    public FlatFacilityFacadeREST() {
        super(FlatFacility.class);
    }
     /**
     * Post method to create a FlatFacility
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(FlatFacility entity) {
        super.create(entity);
    }
     /**
     * Put method to edit a FlatFacility by its id
     *
     * @param id contains the id
     * @param entity the FlatFacility object containing the data
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, FlatFacility entity) {
        super.edit(entity);
    }
    /**
     * DELETE method to remove flatfacilities
     *
     * @param id The id for the flatfacility to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.FlatfacilityId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }
   /**
     * Get method to obtain Flatfacility by id
     *
     * @param id The Flatfacilty id
     * @return the Flatfacility by its id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public FlatFacility find(@PathParam("id") PathSegment id) {
        entities.FlatfacilityId key = getPrimaryKey(id);
        return super.find(key);
    }
 /**
     * Get method to obtain a list with all flatfacilities
     *
     * @return returns a list with all flatfacilities
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<FlatFacility> findAll() {
        return super.findAll();
    }
 /**
     * GET method for getting a range of flatfacilities
     *
     * @param from parameter of start
     * @param to parameter of end
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<FlatFacility> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    /**
     * Get method for getting amount of flatfacilities in plain text
     *
     * @return returns the number of flatfacilities as String
     */
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
    /**
     * Get method for getting a List of flatFacilities by condition
     *
     * @param condition condition of rooms
     * @return a list of flatFacilities
     */
    @GET
    @Path("facilityCondition/{condition}")
    @Produces({MediaType.APPLICATION_XML})
    public List<FlatFacility> findByNofRooms(@PathParam("facilityCondition") String condition) {
        List<FlatFacility> resultado = null;
        try {
            LOGGER.log(Level.INFO, "Getting the flatFacilities by condition{0}", condition);
            resultado = new ArrayList<>(em.createNamedQuery("findFlatFacilityByCondition").setParameter("condition", condition).getResultList());
        } catch (ServiceUnavailableException ex) {
            throw new ServiceUnavailableException();
        }catch(InternalServerErrorException ex){
        throw new InternalServerErrorException();
        }
        return resultado;
    }
}
