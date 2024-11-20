import common.UserPasswordInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private static final String PASSWORD_FILE = "passwords.txt";
    private final Map<String, UserPasswordInfo> userPasswordInfoStore = new HashMap<>();

    protected Authenticator() {
        this.loadPasswordsFromFile();
    }

    public boolean login(String username, String passwordHash) {
        return passwordHash.equals(this.userPasswordInfoStore.get(username).getHashedPassword());
    }

    public boolean userExists(String username) {
        return userPasswordInfoStore.containsKey(username);
    }

    public String getSaltForUser(String username) {
        return userPasswordInfoStore.get(username).getSalt();
    }

    private void loadPasswordsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 3) {
                        String username = parts[0];
                        String hashedPassword = parts[1];
                        String salt = parts[2];

                        UserPasswordInfo userInfo = new UserPasswordInfo();
                        userInfo.setHashedPassword(hashedPassword);
                        userInfo.setSalt(salt);

                        userPasswordInfoStore.put(username, userInfo);
                    }
                }
            } catch (Throwable var5) {
                try {
                    reader.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            reader.close();
        } catch (IOException var6) {
            System.err.println("Error loading password file: " + var6.getMessage());
        }

    }

}


