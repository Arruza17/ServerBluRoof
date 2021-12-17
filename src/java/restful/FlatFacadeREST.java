package restful;

import entities.Flat;
import java.util.ArrayList;
import java.util.List;
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
 * @author jorge
 */
@Stateless
@Path("entities.flat")
public class FlatFacadeREST extends AbstractFacade<Flat> {

    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public FlatFacadeREST() {
        super(Flat.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Flat entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Flat entity) {
        super.edit(entity);
    }
      /**
     * DELETE method to remove accounts: uses removeAccount business logic method.
     * @param id The id for the account to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Flat find(@PathParam("id") Long id) {
        return super.find(id);
    }
    @GET
    @Path("(nBathrooms)")
    @Produces({MediaType.APPLICATION_XML})
    public Short find(@PathParam("nBathrooms") Short nBathrooms){
    return nBathrooms;
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
     @GET
    @Path("nBathrooms/{nBathrooms}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findFlatByNofBathrooms(@PathParam("nBathrooms")Short nBathrooms){
        List<Flat> resultado=null;
        try {
            resultado=new ArrayList<>(em.createNamedQuery("findFlatByNofBathrooms").setParameter("nBathrooms",nBathrooms).getResultList());
        } catch (Exception e) {
        }
        return resultado;
    }
     @GET
    @Path("nRooms/{nRooms}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Flat> findByNofRooms(@PathParam("nRooms")Short nRooms){
        List<Flat> resultado=null;
        try {
            resultado=new ArrayList<>(em.createNamedQuery("findByNofRooms").setParameter("nRooms",nRooms).getResultList());
        } catch (Exception e) {
        }
        return resultado;
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
