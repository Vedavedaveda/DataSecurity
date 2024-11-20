import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Roles {
    private static final String ROLE_BASED_ACCESS_CONTROL_FILE = "role_based_access_control.txt";
    private final Map<String, List<String>> roleOperations = new HashMap<>();

    protected Roles() {
        this.loadRolesFromFile();
    }

    public void loadRolesFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ROLE_BASED_ACCESS_CONTROL_FILE));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String roleName = parts[0];
                        List<String> permissions = List.of(parts[1].split(","));
                        roleOperations.put(roleName, permissions);
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
            System.err.println("Error loading file: " + var6.getMessage());
        }

    }

    public boolean checkIfRoleHasOperation(String roleName, String operation) {
        if (!roleOperations.containsKey(roleName)) {
            return false;
        }

        List<String> permissions = roleOperations.get(roleName);
        return permissions != null && permissions.contains(operation);
    }
}
