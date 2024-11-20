import common.UserPasswordInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authorizator {
    private static final String ACCESS_CONTROL_LIST_FILE = "access_control_list.txt";
    private final Map<String, List<String>> userAccessStore = new HashMap<>();

    protected Authorizator() {
        this.loadAccessControlListFromFile();
    }

    public boolean checkIfUserHasAccess(String username, String operation) {
        if (!userAccessStore.containsKey(username)) {
            return false;
        }

        List<String> permissions = userAccessStore.get(username);
        return permissions != null && permissions.contains(operation);
    }

    private void loadAccessControlListFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ACCESS_CONTROL_LIST_FILE));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String username = parts[0];
                        String operations = parts[1];
                        List<String> permissions = List.of(operations.split(","));
                        userAccessStore.put(username, permissions);
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
