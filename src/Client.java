import common.Parameters;
import common.Printer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.security.MessageDigest;
import java.util.Scanner;


public class Client {
    private PrintServerI printServer = null;
    private final Scanner scanner = new Scanner(System.in);
    private final Authenticator authenticator = new Authenticator();

    public Client() {
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        Client client = new Client();
        client.connectToPrintServer();

        System.out.println("Login to the print server");
        boolean loggedIn = false;
        while (!loggedIn) {
            loggedIn = client.login();
        }

        try {
            while(true) {
                System.out.print("Enter command: ");
                String command = client.scanner.nextLine();

                switch (command) {
                    case "login":
                        client.login();
                        break;

                    case "logout":
                        client.logout();
                        break;

                    case "start":
                        client.start_server();
                        break;

                    case "stop":
                        client.stop_server();
                        break;

                    case "restart":
                        client.restart_server();
                        break;

                    case "print":
                        client.print();
                        break;

                    case "queue":
                        client.queue();
                        break;

                    case "topQueue":
                        client.topQueue();
                        break;

                    case "status":
                        client.status();
                        break;

                    case "readConfig":
                        client.readConfig();
                        break;

                    case "setConfig":
                        client.setConfig();
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

    private void connectToPrintServer() throws MalformedURLException, NotBoundException, RemoteException {
        this.printServer = (PrintServerI) Naming.lookup("rmi://localhost:5099/print");
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

    private boolean login() {
        try {

            String username = get_username_input();
            String password =  get_password_input();

            if (!this.authenticator.userExists(username)) {
                System.out.println("No user with given username.");
                return false;
            }

            String salt = this.authenticator.getSaltForUser(username);
            String passwordHash = hashPassword(password, salt);

            if (this.authenticator.login(username, passwordHash)) {
                this.printServer.login();
                System.out.println("Login successful.");
                return true;

            } else {
                System.out.println("Login failed.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void logout() throws RemoteException {
        this.printServer.logout();
        System.out.println("Logout successful.");
    }

    private void start_server() throws RemoteException {
        System.out.println(this.printServer.start());
    }

    private void stop_server() throws RemoteException {
        System.out.println(this.printServer.stop());
    }

    private void restart_server() throws RemoteException {
        System.out.println(this.printServer.restart());
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

    private void print() throws RemoteException {
        System.out.print("Enter filename: ");
        String filename = this.scanner.nextLine();

        String printer = this.getPrinterFromInput();
        if (printer == null) return;

        System.out.println(this.get_printServer().print(filename, printer));
    }

    private void queue() throws RemoteException {
        String printer = this.getPrinterFromInput();
        if (printer == null) return;

        System.out.println(this.get_printServer().queue(printer));
    }

    private void topQueue() throws RemoteException {
        String printer = this.getPrinterFromInput();
        if (printer == null) return;

        System.out.print("Enter job number: ");
        int job;
        try {
            job = Integer.parseInt(this.scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid job number.\n");
            return;
        }

        System.out.println(this.get_printServer().topQueue(printer, job));
    }

    private void status() throws RemoteException {
        String printer = this.getPrinterFromInput();
        if (printer == null) return;

        System.out.println(this.get_printServer().status(printer));
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

    private void readConfig() throws RemoteException {
        String parameter = this.getParameterFromInput();
        if (parameter == null) return;

        System.out.println(this.get_printServer().readConfig(parameter));
    }

    private void setConfig() throws RemoteException {
        String parameter = this.getParameterFromInput();
        if (parameter == null) return;

        String value = this.getParameterValueFromInput(parameter);
        if (value == null) return;

        System.out.println(this.get_printServer().setConfig(parameter, value));
    }

}