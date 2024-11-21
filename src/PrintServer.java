import common.Parameters;
import common.Printer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Currency;

public class PrintServer extends UnicastRemoteObject implements PrintServerI {

    private static final Printer[] printers;
    private static final Parameters parameters = new Parameters();
    private final SessionManager session_manager = new SessionManager();
    private final Authorizator authorizator = new Authorizator();
    private boolean active = true;
    private String currentUsername = null;

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

    private String checkServerState(String operation) {
        return checkServerState(operation, true);
    }

    private String checkServerState(String operation, boolean checkIfServerIsActive) {
        if (this.currentUsername == null) return "No user is logged in. Please log in to continue.";
        if (!session_manager.check_session()) return "Session expired. Please log in again.";
        if (!authorizator.checkIfUserHasAccess(this.currentUsername, operation))
            return "User " + this.currentUsername + " has no access to operation " + operation + "."; ;
        if (checkIfServerIsActive && !this.active) return "Print server is not running.";

        return null;
    }

    public String start() throws RemoteException {
        String checkError = checkServerState("start", false);
        if (checkError != null) return checkError;
        if (this.active) return "Print server is already running.";

        this.active = true;
        return "Print server started.";
    }

    public String stop() throws RemoteException {
        String checkError = checkServerState("stop", false);
        if (checkError != null) return checkError;
        if (!this.active) return "Print server is already stopped.";

        this.active = false;
        this.clear();
        return "Print server stopped.";
    }

    public String restart() throws RemoteException {
        String checkError = checkServerState("restart", false);
        if (checkError != null) return checkError;
        if (!this.active) return "Print server is not running and cannot be restarted.";

        this.clear();
        return "Print server restarted.";
    }

    public String print(String filename, String printer) throws RemoteException {
        String checkError = checkServerState("print");
        if (checkError != null) return checkError;

        return printers[Printer.getPrinterId(printer)].print(filename);
    }

    public String queue(String printer) throws RemoteException {
        String checkError = checkServerState("queue");
        if (checkError != null) return checkError;

        return printers[Printer.getPrinterId(printer)].queue();
    }

    public String topQueue(String printer, int job) throws RemoteException {
        String checkError = checkServerState("topQueue");
        if (checkError != null) return checkError;

        return printers[Printer.getPrinterId(printer)].topQueue(job);
    }

    public String status(String printer) throws RemoteException {
        String checkError = checkServerState("status");
        if (checkError != null) return checkError;

        return printers[Printer.getPrinterId(printer)].status();
    }

    public String readConfig(String parameter) throws RemoteException {
        String checkError = checkServerState("readConfig");
        if (checkError != null) return checkError;

        return parameters.readConfig(parameter);
    }

    public String setConfig(String parameter, String value) throws RemoteException {
        String checkError = checkServerState("setConfig");
        if (checkError != null) return checkError;

        return parameters.setConfig(parameter, value);
    }

    public void login(String username) throws RemoteException {
        this.currentUsername = username;
        this.session_manager.start_session();
    }

    public void logout() throws RemoteException {
        this.currentUsername = null;
        this.session_manager.stop_session();
    }

    public void clear() throws RemoteException {
        for (Printer printer : printers) {
            printer.clear();
        }
        parameters.clear();
    }
}
