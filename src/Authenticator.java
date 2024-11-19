import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private static final String PASSWORD_FILE = "passwords.txt";
    private final Map<String, String> passwordStore = new HashMap<>();

    protected Authenticator() throws RemoteException {
        this.loadPasswordsFromFile();
    }

    public boolean login(String username, String passwordHash) throws RemoteException {

        return passwordHash.equals(this.passwordStore.get(username));
    }

    private void loadPasswordsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("passwords.txt"));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        this.passwordStore.put(parts[0], parts[1]);
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

