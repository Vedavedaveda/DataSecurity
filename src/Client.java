import common.Parameters;
import common.Printer;

import java.rmi.Naming;
import java.rmi.RemoteException;

import java.security.MessageDigest;
import java.util.Scanner;


public class Client {
    private PrintServerI printServer = null;
    private final Scanner scanner = new Scanner(System.in);
    private final Authenticator authenticator = new Authenticator();

    public Client() {
    }

    public static void main(String[] args) {
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
                        client.stop_server();
                        break;

                    case "print":
                        client.print();
                        break;

                    case "queue":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            String printer = client.getPrinterFromInput();
                            if (printer == null) break;

                            System.out.println(client.get_printServer().queue(printer));
                        }
                        break;

                    case "topQueue":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            String printer = client.getPrinterFromInput();
                            if (printer == null) break;

                            System.out.print("Enter job number: ");
                            int job;
                            try {
                                job = Integer.parseInt(client.scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid job number.\n");
                                break;
                            }

                            System.out.println(client.get_printServer().topQueue(printer, job));
                        }
                        break;

                    case "restart":
                        client.stop_server();
                        client.start_server();
                        break;

                    case "status":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            String printer = client.getPrinterFromInput();
                            if (printer == null) break;

                            System.out.println(client.get_printServer().status(printer));
                        }
                        break;

                    case "readConfig":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            String parameter = client.getParameterFromInput();
                            if (parameter == null) break;

                            System.out.println(client.get_printServer().readConfig(parameter));
                        }
                        break;

                    case "setConfig":
                        if (client.get_printServer() == null) {
                            System.out.println("Server needs to be started!");
                        } else{
                            String parameter = client.getParameterFromInput();
                            if (parameter == null) break;

                            String value = client.getParameterValueFromInput(parameter);
                            if (value == null) break;

                            System.out.println(client.get_printServer().setConfig(parameter, value));
                        }
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

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((password+salt).getBytes("UTF-8"));
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

    private String get_username_input() {
        System.out.print("Enter username: ");
        return this.scanner.nextLine();
    }

    private String get_password_input() {
        System.out.print("Enter password: ");
        return this.scanner.nextLine();
    }

    private void start_server() {
        try {

            String username = get_username_input();
            String password =  get_password_input();
            String salt = this.authenticator.getSaltForUser(username);
            String passwordHash = hashPassword(password, salt);

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

    private void stop_server() throws RemoteException {
        this.get_printServer().clear();
        this.printServer = null;
        System.out.println("Disconnected from print server.");
    }

    private void print() throws RemoteException {
        if (this.get_printServer() == null) {
            System.out.println("Server needs to be started!");
        } else {
            System.out.print("Enter filename: ");
            String filename = this.scanner.nextLine();

            String printer = this.getPrinterFromInput();
            if (printer == null) return;

            System.out.println(this.get_printServer().print(filename, printer));
        }
    }

    private String getPrinterFromInput() {
        System.out.print("Enter printer (options: " + Printer.getPrinterOptions() + "): ");
        String printer = this.scanner.nextLine();
        if (Printer.isValidPrinter(printer)) {
            return printer;
        } else {
            System.out.println("Invalid printer selected.\n");
            return null;
        }
    }

    private String getParameterFromInput() {
        System.out.print("Enter parameter (options: " + Parameters.getParameterOptions() + "): ");
        String parameter = this.scanner.nextLine();

        if (Parameters.isValidParameter(parameter)) {
            return parameter;
        } else {
            System.out.println("Invalid parameter selected.\n");
            return null;
        }
    }

    private String getParameterValueFromInput(String parameter) {
        System.out.print("Enter value (options: " + Parameters.getParameterPossibleValue(parameter) + "): ");
        String value = this.scanner.nextLine();

        if (Parameters.isValidParameterValue(parameter, value)) {
            return value;
        } else {
            System.out.println("Invalid value for parameter selected.\n");
            return null;
        }
    }
}