import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException
    {
        PrintServerI printServer = (PrintServerI) Naming.lookup("rmi://localhost:5099/print");
        System.out.println(printServer.print("Hello", "printer 2"));
    }


}
