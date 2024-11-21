import common.Parameters;
import common.Printer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class PrintServer extends UnicastRemoteObject implements PrintServerI {

    private static final Printer[] printers;
    private static final Parameters parameters = new Parameters();
    private final SessionManager session_manager = new SessionManager();
    private boolean active = true;

    static {
        printers = new Printer[Printer.VALID_PRINTERS.length];
        for (int i = 0; i < printers.length; i++) {
            printers[i] = new Printer(Printer.VALID_PRINTERS[i]);
        }
    }

    public PrintServer() throws RemoteException {
        super();
        this.session_manager.start_session();
    }

    private String checkServerState() {
        if (!session_manager.check_session()) return "Session expired. Please log in again.";
        if (!this.active) return "Print server is not running.";
        return null;
    }

    public String start() throws RemoteException {
        if (!session_manager.check_session()) return "Session expired. Please log in again.";
        if (this.active) return "Print server is already running.";
        this.active = true;
        return "Print server started.";
    }

    public String stop() throws RemoteException {
        if (!session_manager.check_session()) return "Session expired. Please log in again.";
        if (!this.active) return "Print server is already stopped.";
        this.active = false;
        this.clear();
        return "Print server stopped.";
    }

    public String restart() throws RemoteException {
        if (!session_manager.check_session()) return "Session expired. Please log in again.";
        if (!this.active) return "Print server is not running and cannot be restarted.";
        this.clear();
        return "Print server restarted.";
    }

    public String print(String filename, String printer) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return printers[Printer.getPrinterId(printer)].print(filename);
    }

    public String queue(String printer) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return printers[Printer.getPrinterId(printer)].queue();
    }

    public String topQueue(String printer, int job) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return printers[Printer.getPrinterId(printer)].topQueue(job);
    }

    public String status(String printer) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return printers[Printer.getPrinterId(printer)].status();
    }

    public String readConfig(String parameter) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return parameters.readConfig(parameter);
    }

    public String setConfig(String parameter, String value) throws RemoteException {
        String checkError = checkServerState();
        if (checkError != null) return checkError;
        return parameters.setConfig(parameter, value);
    }

    public void login() throws RemoteException {
        this.session_manager.start_session();
    }

    public void logout() throws RemoteException {
        this.session_manager.stop_session();
    }

    public void clear() throws RemoteException {
        for (Printer printer : printers) {
            printer.clear();
        }
        parameters.clear();
    }
}
