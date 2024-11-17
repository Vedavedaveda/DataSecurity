import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        try {
            PrintServerI printServer = (PrintServerI) Naming.lookup("rmi://localhost:5099/print");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to print server.");

            while(true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();

                switch (command) {
                    case "print":
                        System.out.println(printServer.print("hello.file", "Printer 3"));
                        break;
                    case "queue":
                        System.out.println("Command not yet functional");
                        break;
                    case "topQueue":
                        System.out.println("Command not yet functional");
                        break;
                    case "start":
                        System.out.println("Command not yet functional");
                        break;
                    case "stop":
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
}