import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authorizator {
    private static final String USER_ROLES_FILE = "user_roles.txt";
    private static final Roles roles = new Roles();
    private final Map<String, List<String>> userRoles = new HashMap<>();

    protected Authorizator() {
        this.loadRoleBasedAccessControlFromFile();
    }

    public boolean checkIfUserHasAccess(String username, String operation) {
        if (!userRoles.containsKey(username)) {
            return false;
        }

        List<String> assignedRoles = userRoles.get(username);
        for (String roleName : assignedRoles) {
            if (roles.checkIfRoleHasOperation(roleName, operation)) {
                return true;
            }
        }

        return false;
    }


    private void loadRoleBasedAccessControlFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_ROLES_FILE));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String username = parts[0];
                        List<String> roles = List.of(parts[1].split(","));
                        userRoles.put(username, roles);
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



}
