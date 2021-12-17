/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author 2dam
 */
@Stateless
@Path("entities.service")
public class ServiceFacadeREST extends AbstractFacade<Service> {

       private final Logger LOGGER = Logger.getLogger(ServiceFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public ServiceFacadeREST() {
        super(Service.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Service entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Service entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Service find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Service> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Service> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
     
    @GET
    @Path("type/{serviceType}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Service> findByType(@PathParam("serviceType") String serviceType) {
        List<Service> services = null;
        try {
            LOGGER.log(Level.INFO, "Getting the facilities by type {0}", serviceType);
            ServiceType type = ServiceType.valueOf(serviceType);
            services = new ArrayList<>(em.createNamedQuery("findByType").setParameter("serviceType", type).getResultList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
            //throw new 
        }
        return services;
    } 
}
