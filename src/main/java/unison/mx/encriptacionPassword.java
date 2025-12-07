package unison.mx;

import java.security.MessageDigest;

public class encriptacionPassword {

    public static String md5(String texto) {
        if (texto == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(texto.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            System.out.println("Error en hash MD5: " + e.getMessage());
            return null;
        }
    }
}
