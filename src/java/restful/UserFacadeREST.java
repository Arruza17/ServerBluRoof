package restful;

import entities.User;
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
 * RESTful service for Users.
 *
 * @author Yeray Sampedro
 */
@Stateless
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger("UserFacadeREST");

    /**
     * EJB object implementing the bussiness logic
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, User entity) {
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
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("login/{login}/password/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public User logInUser(@PathParam("login") String login, @PathParam("password") String password) {
        User user = null;
        try {
            LOGGER.info("Getting the login information");
            //"SELECT u FROM user u WHERE u.login=:user and u.password=:password"
            user = (User) em.createNamedQuery("logInUser").setParameter("login", login).setParameter("password", password).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            throw new NotAuthorizedException(e);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            //Throw new read exception
        }
        return user;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
