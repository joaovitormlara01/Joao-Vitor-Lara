package app;

import org.mindrot.jbcrypt.BCrypt;

public class HashGenerator {
    public static void main(String[] args) {
        String senha = "admin123"; // senha base
        String hash = BCrypt.hashpw(senha, BCrypt.gensalt(10));
        System.out.println("Hash gerado: " + hash);
    }
}
