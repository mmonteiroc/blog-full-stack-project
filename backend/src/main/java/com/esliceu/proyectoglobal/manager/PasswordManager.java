package com.esliceu.proyectoglobal.manager;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 23/02/2020
 * Package: com.esliceu.proyectoglobal.manager
 * Project: BackEnd
 */
@Service
public class PasswordManager {

    /*
     * Este metodo es un placeholder, a espera de ver que
     * libreria nos recomienda JOAN para encriptar.
     * Lo pongo en un meotodo a parte, asi esta en capsulado,
     * asi cuando queramos modificar nuestra manera de encriptar, solo
     * habra que cambiar el contenido del metodo
     * */
    public String encryptSha265(String toEncrypt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(toEncrypt.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }


            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
