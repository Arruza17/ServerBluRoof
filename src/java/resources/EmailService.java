package resources;

import cipher.ServerCipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Ander Arruza, Yeray Sampedro, Jorge Crespo, Adrián Pérez
 */
public class EmailService {

    /**
     *
     */
    private static ResourceBundle configFile;
    /**
     *
     */
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
    /**
     *
     */
    private static String from;
    /**
     *
     */
    private static String password;

    public EmailService() {
        configFile = ResourceBundle.getBundle("resources.mail");
    }

    public void sendEmail(String email, String message) throws Exception {

        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        from = configFile.getString("MAIL");
        password = new ServerCipher().decipherServerData();

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage sendMsg = new MimeMessage(session);

            // Set From: header field of the header.
            sendMsg.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            sendMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            sendMsg.setSubject("BluRoof password changed!!");

            // Now set the actual message
            StringBuilder contentBuilder = new StringBuilder();

            String route;
            if (message.length() == 16) {
                route = "EmailReset.html";
            } else {
                route = "EmailChange.html";
            }

            BufferedReader in = new BufferedReader(new FileReader(getClass().getResource(route).getPath()));
            String str;

            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
            String content = contentBuilder.toString().trim();
            if (message.length() == 16) {
                content = content.replace("THISISYOURPASSWORD", message);
            }
            sendMsg.setText(content, "utf-8", "html");

            LOGGER.info("Sending email");
            // Send message
            Transport.send(sendMsg);

        } catch (MessagingException ex) {
            throw new Exception(ex);
        } catch (IOException ex) {
            throw new Exception(ex);
        }

    }

}
