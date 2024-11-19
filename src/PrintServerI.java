import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintServerI extends Remote {
    String print(String filename, String printer) throws RemoteException; // prints file filename on the specified printer
    String queue(String printer) throws RemoteException; // lists the print queue for a given printer on the user’s display in lines of the form ¡job number¿ ¡file name¿
    String topQueue(String printer, int job) throws RemoteException; // moves job to the top of the queue
    String status(String printer) throws RemoteException; // prints status of printer on the user’s display
    String readConfig(String parameter) throws RemoteException; // prints the value of the parameter on the print server to the user’s display
    String setConfig(String parameter, String value) throws RemoteException; // sets the parameter on the print server to value
    void clear() throws RemoteException;
}
