package cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 *
 * @author BluRoof
 */
public class ServerCipher {

    private static byte[] salt = "this is the salt".getBytes();
    private static byte[] privateKey;

    public ServerCipher() {
        try {
            privateKey = Files.readAllBytes(Paths.get("Private.key"));
        } catch (IOException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method used to HASH
     *
     * @param msg the message to be hashed
     * @return ret the ciphered message
     */ 
    public static String hash(byte[] msg) {
        String ret = null;
        MessageDigest messageDigest;
        try {
            // Obtén una instancia de MessageDigest que usa SHA
            messageDigest = MessageDigest.getInstance("SHA");
            // Actualiza el MessageDigest con el array de bytes
            messageDigest.update(msg);
            // Calcula el resumen (función digest) y lo convierte en hexadecimal
            ret = hexadecimal(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     * Method used to decipher the message received from the client
     *
     * @param cipheredMsg the message ciphered
     * @return msg the message deciphered
     */
    public static String decipher(String cipheredMsg) {
        //Pasa de hexadecimal a string, con el mensaje cifrado real
        byte[] codedMsg = byteArray(cipheredMsg);
        String msg = null;
        try {
            //Creamos la clave privada 
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pvt = kf.generatePrivate(ks);
            //Instanciamos el cipher en modo RSA/ECB/PKCS1Padding
            Cipher decipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //Desciframos con la clave privada
            decipher.init(Cipher.DECRYPT_MODE, pvt);
            //Conseguimos el mensaje descifrado
            msg = new String(decipher.doFinal(codedMsg));

            return msg;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    private static String hexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }

    private static byte[] byteArray(String hex) {
        String result = new String();
        char[] charArray = hex.toCharArray();
        for (int i = 0; i < charArray.length; i = i + 2) {
            String st = "" + charArray[i] + "" + charArray[i + 1];
            char ch = (char) Integer.parseInt(st, 16);
            result = result + ch;
        }
        return result.getBytes();
    }

}
