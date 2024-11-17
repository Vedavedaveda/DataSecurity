import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;


public class Client {
    private PrintServerI printServer = null;
    private final Scanner scanner = new Scanner(System.in);
    private final Authenticator authenticator = new Authenticator();

    public Client() throws RemoteException {
    }

    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        Client client = new Client();
        try {

            while(true) {
                System.out.print("Enter command: ");
                String command = client.scanner.nextLine();

                switch (command) {
                    case "start":
                        client.start_server();
                        break;
                    case "stop":
                        //this.printServer = null;
                        System.out.println("Disconnected from print server.");
                        break;
                    case "print":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            System.out.println(client.get_printServer().print("hello.file", "Printer 3"));
                        }
                        break;
                    case "queue":
                        System.out.println("Command not yet functional");
                        break;
                    case "topQueue":
                        System.out.println("Command not yet functional");
                        break;
                    case "restart":
                        System.out.println("Command not yet functional");
                        break;
                    case "status":
                        System.out.println("Command not yet functional");
                        break;
                    case "readConfig":
                        System.out.println("Command not yet functional");
                        break;
                    case "setConfig":
                        System.out.println("Command not yet functional");
                        break;
                    case "exit":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid command.");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private PrintServerI get_printServer() {
        return this.printServer;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get_username_input() {
        System.out.print("Enter username: ");
        return this.scanner.nextLine();
    }

    public String get_password_input() {
        System.out.print("Enter password: ");
        return this.scanner.nextLine();
    }

    public void start_server() throws NotBoundException, MalformedURLException, RemoteException {
        try {
            //Authenticator authenticator = new Authenticator();

            String username = get_username_input();
            String password =  get_password_input();
            String passwordHash = hashPassword(password);
            System.out.println(username);
            System.out.println(passwordHash);

            if (this.authenticator.login(username, passwordHash)) {
                this.printServer = (PrintServerI) Naming.lookup("rmi://localhost:5099/print");
                System.out.println("Login successful, print server started.");

            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}