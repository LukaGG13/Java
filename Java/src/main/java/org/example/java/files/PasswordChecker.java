package org.example.java.files;

import org.mindrot.jbcrypt.BCrypt;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class PasswordChecker {

    private PasswordChecker () {}
    //TOOD finish
    public static Boolean check(UUID userId, String password) {

        var filePath = Path.of(userId.toString());
        if (Files.exists(filePath)) return false;
        try(var fileReader = new FileReader(filePath.toFile())) {
            // TODO move to create account screen String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
            var hashed = fileReader.readAllAsString();
            return BCrypt.checkpw(password, hashed);
        } catch(IOException e) {
            throw new RuntimeException("Something when wrong");
        }
    }
}
