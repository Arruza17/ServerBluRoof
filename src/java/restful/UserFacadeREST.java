package restful;

import cipher.ServerCipher;
import entities.LastSignIn;
import entities.User;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Random;
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
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import resources.EmailService;

/**
 * RESTful service for Users.
 *
 * @author Yeray Sampedro
 */
@Stateless
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {

    private ServerCipher serverCipher = new ServerCipher();

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(UserFacadeREST.class.getName());

    /**
     * EJB object implementing the bussiness logic
     */
    @PersistenceContext(unitName = "ServerBluRoofPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    /**
     * Method that creates a user
     *
     * @param entity the user to be crated
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    /**
     * Method used to edit data of a user
     *
     * @param id the id of the user to be edited
     * @param entity the user with the modified data
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, User entity) {
        super.edit(entity);
    }

    /**
     * Deletes information about a user by id
     *
     * @param id the id of the user to be removed
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Method that gets the data of an specific user by id
     *
     * @param id the id of the user
     * @return User the user to be found
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Method that finds ALL users
     *
     * @return a list of users
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
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
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Method that counts the amount of users
     *
     * @return the amount of users
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Method used to log in
     *
     * @param login the username
     * @param password the password, encrypted
     * @return user all the data of the user
     */
    @GET
    @Path("login/{login}/password/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public User logInUser(@PathParam("login") String login, @PathParam("password") String password) {
        User user = null;
        try {
            LOGGER.info("Getting the login information");
            //Decipher pasword
            String decipheredPassword = serverCipher.decipherClientPetition(password);
            //Hash password       
            String hashedPassword = serverCipher.hash(decipheredPassword.getBytes());
            //"SELECT u FROM user u WHERE u.login=:user and u.password=:password" 
            user = (User) em.createNamedQuery("logInUser").setParameter("login", login).setParameter("password", hashedPassword).getSingleResult();
            //Take all the last signins of a user to the persistance context
            //SELECT l FROM LastSignIn l WHERE l.user =(SELECT u FROM User u WHERE u.login= :login) ORDER BY l.lastSignIn ASC 
            List<LastSignIn> lastSignIns = new ArrayList<>();
            lastSignIns = (ArrayList) em.createNamedQuery("findByUserLogin").setParameter("user", user).getResultList();

            //If they signed in less than 10 times, a new sign in is added
            if (lastSignIns.size() < 10) {
                LastSignIn lastSignIn = new LastSignIn();
                lastSignIn.setId(null);
                lastSignIn.setLastSignIn(new Date());
                lastSignIn.setUser(user);
                em.persist(lastSignIn);
            } else {
                //If they signed in more than 10 times, the sign in with the minimum date is updated
                LastSignIn lis = lastSignIns.get(0);
                lis.setLastSignIn(new Date());
                em.merge(lis);
            }

        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            throw new NotAuthorizedException(e);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            //Throw new read exception
        }
        return user;
    }

    /**
     * Method used to reset the password of a user
     *
     * @param login the user to have the password reseted
     */
    @GET
    @Path("reset/{user}")
    @Consumes({MediaType.APPLICATION_XML})
    public void resetPassword(@PathParam("user") String login) {
        try {
            LOGGER.info("Creating new password");
            //Search all the data of a user          
            User user = (User) em.createNamedQuery("findByLogin").setParameter("login", login).getSingleResult();
            //Generate new password
            String pass = generateRandomPassword();
            //Hash password
            String hasshedPass = serverCipher.hash(pass.getBytes());
            // "UPDATE User u SET u.password=:newPass WHERE u.login= :login")
            em.createNamedQuery("changePassword").setParameter("login", login).setParameter("newPass", hasshedPass).executeUpdate();
            //Sending email with new password
            EmailService es = new EmailService();
            //Type 1 because of reset
            es.sendEmail(user.getEmail(), pass);

            //Hashing the password
            // password = "HASHED PASSWORD";
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> resetPassword():{0}", e.getLocalizedMessage());
            throw new NotFoundException(e);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> resetPassword():{0}", e.getLocalizedMessage());
            //Throw new read exception
        }
    }

    /**
     * Method used to update the password of an specific user
     *
     * @param login the user of which the password will be changed
     * @param password the new password
     */
    @GET
    @Path("update/{user}/password/{pass}")
    @Consumes({MediaType.APPLICATION_XML})
    public void changePassword(@PathParam("user") String login, @PathParam("pass") String password) {
        try {
            LOGGER.info("Updating password");
            //Search all the data of a user          
            User user = (User) em.createNamedQuery("findByLogin").setParameter("login", login).getSingleResult();
            //Decipher pasword
            //String decipheredPassword = serverCipher.decipherClientPetition(password);
            //Hash password       
            //String hashedPassword = serverCipher.hash(decipheredPassword.getBytes());
            String hashedPassword = serverCipher.hash(password.getBytes());
            // "UPDATE User u SET u.password=:newPass WHERE u.login= :login")
            em.createNamedQuery("changePassword").setParameter("login", login).setParameter("newPass", hashedPassword).executeUpdate();
            //Sending email with new password
            EmailService es = new EmailService();
            //Type 2 because of change
            es.sendEmail(user.getEmail(), "change");

        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> changePassword():{0}", e.getLocalizedMessage());
            throw new NotFoundException(e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> changePassword():{0}", e.getLocalizedMessage());

        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private String generateRandomPassword() {
        String uppercase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String lowercase = uppercase.toLowerCase();
        String specialChars = "1234567890!·$%&/()=?¿@#~€¬";
        String all = uppercase + lowercase + specialChars;
        String pass = "";

        for (int i = 0; i < 16; i++) {
            pass = pass + all.charAt(new Random().nextInt(all.length()));
        }

        return pass;
    }

}
