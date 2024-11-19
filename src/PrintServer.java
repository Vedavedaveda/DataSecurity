import common.Parameters;
import common.Printer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class PrintServer extends UnicastRemoteObject implements PrintServerI {

    private static final Printer[] printers;
    private static final Parameters parameters = new Parameters();

    static {
        printers = new Printer[Printer.VALID_PRINTERS.length];
        for (int i = 0; i < printers.length; i++) {
            printers[i] = new Printer(Printer.VALID_PRINTERS[i]);
        }
    }

    public PrintServer() throws RemoteException {
        super();
    }

    public String print(String filename, String printer) throws RemoteException {
        // prints file filename on the specified printer
        return printers[Printer.getPrinterId(printer)].print(filename);
    }

    public String queue(String printer) throws RemoteException {
        // lists the print queue for a given printer on the user’s display in lines of the form ¡job number¿ ¡file name¿
        return printers[Printer.getPrinterId(printer)].queue();
    }

    public String topQueue(String printer, int job) throws RemoteException {
        // moves job to the top of the queue
        return printers[Printer.getPrinterId(printer)].topQueue(job);
    }

    public String status(String printer) throws RemoteException {
        // prints status of printer on the user’s display
        return printers[Printer.getPrinterId(printer)].status();
    }

    public String readConfig(String parameter) throws RemoteException {
        // prints the value of the parameter on the print server to the user’s display
        return parameters.readConfig(parameter);
    }

    public String setConfig(String parameter, String value) throws RemoteException {
        // sets the parameter on the print server to value
        return parameters.setConfig(parameter, value);
    }

    public void clear() throws RemoteException {
        for (Printer printer : printers) {
            printer.clear();
        }
        parameters.clear();
    }
}
