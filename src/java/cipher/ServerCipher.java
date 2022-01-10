package cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 *
 * @author BluRoof
 */
public class ServerCipher {

    private static byte[] salt = "this is the salt".getBytes();
    private static String key = "key";
    private static byte[] privateKey;

    public ServerCipher() {
        try {
            InputStream is = getClass().getResourceAsStream("Private.key");
            byte[] fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
            privateKey = fileContent;
        } catch (IOException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String decipherServerData() {
        String ret = null;
        // Fichero leído
        InputStream is = getClass().getResourceAsStream("Server.key");

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            byte[] fileContent = new byte[is.available()];;
            is.read(fileContent, 0, is.available());
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(key.toCharArray(), salt, 65536, 128); // AES-128
            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Leemos el fichero codificado 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(DECRYPT_MODE, privateKey, ivParam);
            // Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            // Texto descifrado
            ret = new String(decodedMessage);

        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;

    }

    public byte[] cipherServerData(String msgToCipher) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        Cipher cipher;
        try {
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(key.toCharArray(), salt, 65536, 128); // AES-128
            // Obtenemos una instancia del SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            cipher.init(ENCRYPT_MODE, privateKey);
            // Le decimos que cifre (método doFinal(mensaje))
            byte[] encodedMessage = cipher.doFinal(msgToCipher.getBytes());
            // Obtenemos el vector CBC del Cipher (método getIV())
            byte[] iv = cipher.getIV();
            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = concatArrays(iv, encodedMessage);
            // Escribimos el fichero cifrado 
            fileWriter("/java/cipher/Server.key", combined);
            // Retornamos el texto cifrado
            ret = new String(encodedMessage);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, e);
        }
        return ret.getBytes();
    }

    /**
     * Method used to HASH
     *
     * @param msg the message to be hashed
     * @return ret the ciphered message
     */
    public String hash(byte[] msg) {
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
    public String decipherClientPetition(String cipheredMsg) {
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
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    private String hexadecimal(byte[] resumen) {
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

    private byte[] byteArray(String hex) {
        String result = new String();
        char[] charArray = hex.toCharArray();
        for (int i = 0; i < charArray.length; i = i + 2) {
            String st = "" + charArray[i] + "" + charArray[i + 1];
            char ch = (char) Integer.parseInt(st, 16);
            result = result + ch;
        }
        return result.getBytes();
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            Logger.getLogger(ServerCipher.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
