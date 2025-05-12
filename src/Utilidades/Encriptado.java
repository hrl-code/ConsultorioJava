/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Destro
 */
public class Encriptado {

    private static final String MILLAVE = "vJMnURwFuojTiaJT";

    public static String encriptar(String textoEncriptado) {

        String encriptado = null;
        try {
            Key millaveEnBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");
            Cipher encriptador = Cipher.getInstance("AES");
            encriptador.init(Cipher.ENCRYPT_MODE, millaveEnBytes);
            byte[] bytesEncriptados = encriptador.doFinal(textoEncriptado.getBytes());
            encriptado = Base64.encodeBase64String(bytesEncriptados);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encriptado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encriptado;
    }

    public static String desencriptar(String textoEncriptado) {
        String desencriptado = null;
        try {

            byte[] bytesEncriptados = Base64.decodeBase64(textoEncriptado);
            Key millaveEnBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");
            Cipher encriptador = Cipher.getInstance("AES");
            encriptador.init(Cipher.DECRYPT_MODE, millaveEnBytes);
            desencriptado = new String(encriptador.doFinal(bytesEncriptados));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Encriptado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return desencriptado;
    }
}
