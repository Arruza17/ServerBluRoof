package restful;

import cipher.ServerCipher;
import entities.LastSignIn;
import entities.User;
import entities.UserPrivilege;
import exceptions.ConflictException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
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
import javax.ws.rs.ServerErrorException;
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
        try {
            if (entity.getPrivilege().equals(UserPrivilege.ADMIN)) {
                entity.setPassword(serverCipher.hash("TEST".getBytes()));
            }
            entity.setId(null);
            super.create(entity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> create():{0}", e.getLocalizedMessage());
            throw new ConflictException();

        }
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
    public void edit(@PathParam("id") Long id, User entity
    ) {
        super.edit(entity);
    }

    /**
     * Deletes information about a user by id
     *
     * @param id the id of the user to be removed
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id
    ) {
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
    public User find(@PathParam("id") Long id
    ) {
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
    public List<User> findRange(@PathParam("from") Integer from,
            @PathParam("to") Integer to
    ) {
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
    public User logInUser(@PathParam("login") String login,
            @PathParam("password") String password
    ) {

        User retUser = null;
        try {
            LOGGER.info("Getting the login information");
            //Decipher pasword
            byte[] decipheredPassword = serverCipher.decipherClientPetition(password);
            String mypass = new String(decipheredPassword);
            //Hash password      

            String hashedPassword = serverCipher.hash(mypass.getBytes());
            //"SELECT u FROM user u WHERE u.login=:user and u.password=:password" 
            User user = (User) em.createNamedQuery("logInUser").setParameter("login", login).setParameter("password", hashedPassword).getSingleResult();
            retUser = new User();
            retUser.setBirthDate(user.getBirthDate());
            retUser.setEmail(user.getEmail());
            retUser.setFullName(user.getFullName());
            retUser.setId(user.getId());
            retUser.setLastPasswordChange(user.getLastPasswordChange());
            retUser.setLastSignIns(user.getLastSignIns());
            retUser.setLogin(user.getLogin());
            retUser.setPassword(user.getPassword());
            retUser.setPhoneNumber(user.getPhoneNumber());
            retUser.setPrivilege(user.getPrivilege());
            retUser.setStatus(user.getStatus());

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
            throw new NotAuthorizedException("The username or password are incorrect");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            throw new ServerErrorException("There was a problem with the server", 500);
        }
        return retUser;
    }

    /**
     * Method used to reset the password of a user
     *
     * @param login the user to have the password reseted
     */
    @GET
    @Path("reset/{user}")
    @Consumes({MediaType.APPLICATION_XML})
    public void resetPassword(@PathParam("user") String login
    ) {
        try {
            LOGGER.info("Creating new password");
            //Search all the data of a user          
            User user = (User) em.createNamedQuery("findByLogin").setParameter("login", login).getSingleResult();
            //Generate new password
            String pass = generateRandomPassword();
            //Hash password
            String hashedPass = serverCipher.hash(pass.getBytes());
            user.setPassword(hashedPass);
            em.merge(user);
            //Sending email with new password
            EmailService es = new EmailService();
            //Type 1 because of reset
            es.sendEmail(user.getEmail(), pass);

            //Hashing the password
            // password = "HASHED PASSWORD";
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> resetPassword():{0}", e.getLocalizedMessage());
            throw new NotFoundException("The username provided could not be found in the database", e);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> resetPassword():{0}", e.getLocalizedMessage());
            throw new BadRequestException("The username provided does not have the required format", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> resetPassword():{0}", e.getLocalizedMessage());
            throw new ServerErrorException("There was a problem with the server", 500);
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
    public void changePassword(@PathParam("user") String login,
            @PathParam("pass") String password
    ) {
        try {
            LOGGER.info("Updating password");
            //Search all the data of a user          
            User user = (User) em.createNamedQuery("findByLogin").setParameter("login", login).getSingleResult();
            //Decipher pasword
            byte[] decipheredPassword = serverCipher.decipherClientPetition(password);
            //Hash password       
            String hashedPass = serverCipher.hash(decipheredPassword);
            user.setPassword(hashedPass);
            em.merge(user);
            //Sending email with new password
            EmailService es = new EmailService();
            //Type 2 because of change
            es.sendEmail(user.getEmail(), "change");

        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> changePassword():{0}", e.getLocalizedMessage());
            throw new NotFoundException("There was a problem finding the user to change the password, try again later", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> changePassword():{0}", e.getLocalizedMessage());
            throw new ServerErrorException("There was a problem with the server", 500);

        }
    }

    @GET
    @Path("admin")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAllAdmins() {
        List<User> admins = null;
        try {
            LOGGER.info("Searching for all the admins");
            // SELECT u FROM User u WHERE u.privilege=ADMIN
            admins = em.createNamedQuery("findAllAdmins").getResultList();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> findAllAdmins():{0}", e.getLocalizedMessage());
            throw new NotFoundException("There are no admins in the database yet, contact with an administrator if you think this might be an error", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> findAllAdmins():{0}", e.getLocalizedMessage());
            throw new ServerErrorException("There was a problem with the server", 500);

        }
        return admins;
    }

    @GET
    @Path("admin/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAllAdminsByLogin(@PathParam("login") String login
    ) {
        List<User> admins = null;
        try {
            LOGGER.info("Searching for all the admins that contain " + login);
            // SELECT u FROM User u WHERE u.privilege=ADMIN
            admins = em.createNamedQuery("findAllAdminsByLogin").setParameter("login", "%" + login + "%").getResultList();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> findAllAdminsByLogin():{0}", e.getLocalizedMessage());
            throw new NotFoundException("No admins could be found with the specified login "+ login,e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> findAllAdminsByLogin():{0}", e.getLocalizedMessage());
            throw new ServerErrorException("There was a problem with the server", 500);

        }
        return admins;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private String generateRandomPassword() {
        String uppercase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String lowercase = uppercase.toLowerCase();
        String specialChars = "1234567890!/()=?@#€";
        String all = uppercase + lowercase + specialChars;
        String pass = "";

        for (int i = 0; i < 16; i++) {
            pass = pass + all.charAt(new Random().nextInt(all.length()));
        }

        return pass;
    }

}
