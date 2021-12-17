/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author 2dam
 */
@Stateless
@Path("entities.facility")
public class FacilityFacadeREST extends AbstractFacade<Facility> {

    private final Logger LOGGER = Logger.getLogger(FacilityFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public FacilityFacadeREST() {
        super(Facility.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Facility entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Facility entity) {
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
    public Facility find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    /**
    @GET
    @Path("adquisitionDate/{date}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Facility> findByAdqDate(@PathParam("date") Date adquisitionDate) {
        List<Facility> facilities=null;
        try{
            facilities=new ArrayList<>(em.createNamedQuery("findByAdqDate").setParameter("date",adquisitionDate).getResultList());
        }catch(Exception e){
            LOGGER.severe("FacilityEJB -> findByAdqDate()"+ e.getMessage());
            
        }                
        return facilities;
    }
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
            LOGGER.log(Level.SEVERE,e.getMessage());
            //throw new 
        }
        return facilities;
    } 
    
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
            LOGGER.log(Level.SEVERE,e.getMessage());
            //throw new 
        }
        return facilities;
    } 
}
