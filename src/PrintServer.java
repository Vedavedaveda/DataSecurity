import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class PrintServer extends UnicastRemoteObject implements PrintServerI {
    public PrintServer() throws RemoteException {
        super();
    }


    public String print(String filename, String printer) throws RemoteException {
        return "Printing file: " + filename + " on printer: " + printer;
    }

    public String queue(String printer) throws RemoteException {
        return "wow.";
    }

    public void topQueue(String printer, int job) throws RemoteException {

    }

    public void start() throws RemoteException {

    }

    public void stop() throws RemoteException {

    }

    public void restart() throws RemoteException {

    }

    public void status(String printer) throws RemoteException {

    }

    public void readConfig(String parameter) throws RemoteException {

    }

    public void setConfig(String parameter, String value) throws RemoteException {

    }
}
