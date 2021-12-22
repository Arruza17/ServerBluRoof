package restful;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.Facility;
import entities.FacilityType;
import entities.Service;
import entities.ServiceType;
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
 * RESTful service for Service.
 *
 * @author Adrián
 */
@Stateless
@Path("entities.service")
public class ServiceFacadeREST extends AbstractFacade<Service> {

    /**
     * Logger for this class.
     */
    private final Logger LOGGER = Logger.getLogger(ServiceFacadeREST.class.getName());

    
    /**
     * EJB object implementing business logic.
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    /**
     * Constructor of the ServiceFacadeREST object
     */
    public ServiceFacadeREST() {
        super(Service.class);
    }

    /**
     * POST method to create a service.
     *
     * @param entity The new service.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Service entity) {
        super.create(entity);
    }

    /**
     * PUT method to edit a service.
     *
     * @param id The id of the service.
     * @param entity The new service that overrides the previous one.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Service entity) {
        super.edit(entity);
    }

    /**
     * DELETE method that deletes a service.
     *
     * @param id The id of the service.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * GET method for getting a service by its id
     *
     * @param id The service id.
     * @return A service object
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Service find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * GET method that returns all the service.
     *
     * @return all the service.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findAll() {
        return super.findAll();
    }

    /**
     * GET method that returns a list with the expecified number in range
     *
     * @param from the start value
     * @param to the finish value
     * @return the service list
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
     * GET method that returns the services with an specific type.
     *
     * @param serviceType The type of service.
     * @return all the services of an specific type.
     */
    @GET
    @Path("type/{serviceType}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findServiceByType(@PathParam("serviceType") String serviceType) {
        List<Service> services = null;
        try {
            LOGGER.log(Level.INFO, "Getting the Services by type {0}", serviceType);
            ServiceType type = ServiceType.valueOf(serviceType);
            services = new ArrayList<>(em.createNamedQuery("findServiceByType").setParameter("serviceType", type).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return services;
    }

    /**
     * GET method that returns the services with an specific address.
     *
     * @param serviceAddress The service address.
     * @return all the services with a specific address.
     */
    @GET
    @Path("address/{serviceAddress}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findServiceByAddress(@PathParam("serviceAddress") String serviceAddress) {
        List<Service> services = null;
        try {
            LOGGER.log(Level.INFO, "Getting the Services by type {0}", serviceAddress);

            services = new ArrayList<>(em.createNamedQuery("findServiceByAddress").setParameter("serviceAddress", serviceAddress).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return services;
    }

    /**
     * GET method that returns the services with an specific name.
     *
     * @param serviceName The name of the service.
     * @return all the services with a specific name.
     */
    @GET
    @Path("name/{serviceName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findServiceByName(@PathParam("serviceName") String serviceName) {
        List<Service> services = null;
        try {
            LOGGER.log(Level.INFO, "Getting the Services by name {0}", serviceName);

            services = new ArrayList<>(em.createNamedQuery("findServiceByName").setParameter("serviceName", serviceName).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            //throw new 
        }
        return services;
    }

}
