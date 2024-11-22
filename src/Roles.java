import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Roles {
    private static final String ROLE_BASED_ACCESS_CONTROL_FILE = "role_based_access_control.txt";
    private final Map<String, List<String>> roleOperations = new HashMap<>();
    private final Map<String, List<String>> roleInheritance = new HashMap<>();
    private final Map<String, Set<String>> resolvedPermissions = new HashMap<>();


    protected Roles() {
        this.loadRolesFromFile();
        this.resolveAllPermissions();
    }

    public void loadRolesFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ROLE_BASED_ACCESS_CONTROL_FILE));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 3) {
                        String roleName = parts[0];
                        String parentRoles = parts[1];
                        String permissions = parts[2];

                        List<String> parents = parentRoles.equals("-") ? Collections.emptyList() : List.of(parentRoles.split(","));
                        List<String> perms = permissions.equals("-") ? Collections.emptyList() : List.of(permissions.split(","));

                        roleInheritance.put(roleName, parents);
                        roleOperations.put(roleName, perms);
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

    private void resolveAllPermissions() {
        for (String role : roleOperations.keySet()) {
            resolvePermissionsForRole(role);
        }
    }

    private Set<String> resolvePermissionsForRole(String role) {
        if (resolvedPermissions.containsKey(role)) {
            return resolvedPermissions.get(role);
        }

        Set<String> permissions = new HashSet<>(roleOperations.getOrDefault(role, Collections.emptyList()));

        for (String parentRole : roleInheritance.getOrDefault(role, Collections.emptyList())) {
            permissions.addAll(resolvePermissionsForRole(parentRole));
        }

        resolvedPermissions.put(role, permissions);
        return permissions;
    }

    public boolean checkIfRoleHasOperation(String roleName, String operation) {
        return resolvedPermissions.getOrDefault(roleName, Collections.emptySet()).contains(operation);
    }
}
