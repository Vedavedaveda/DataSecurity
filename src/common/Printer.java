package common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class Printer {
    private static final String PRINTER1 = "Printer 1";
    private static final String PRINTER2 = "Printer 2";
    private static final String PRINTER3 = "Printer 3";

    public static final String[] VALID_PRINTERS = {
            PRINTER1,
            PRINTER2,
            PRINTER3
    };

    private static final Map<String, Integer> PRINTER_ID_MAP = new HashMap<>();
    static {
        for (int i = 0; i < VALID_PRINTERS.length; i++) {
            PRINTER_ID_MAP.put(VALID_PRINTERS[i], i);
        }
    }

    private final Queue<String> jobQueue;
    private final String name;

    public static int getPrinterId(String printer) {
        return PRINTER_ID_MAP.getOrDefault(printer, -1);
    }

    public static boolean isValidPrinter(String printer) {
        for (String validPrinter : VALID_PRINTERS) {
            if (validPrinter.equals(printer)) {
                return true;
            }
        }
        return false;
    }

    public static String getPrinterOptions() {
        StringBuilder options = new StringBuilder();
        for (String printer : VALID_PRINTERS) {
            options.append(printer).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

    public Printer(String printerName) {
        jobQueue = new LinkedList<>();
        name = printerName;
    }

    public String print(String filename) {
        jobQueue.add(filename);
        return "File: '" + filename + "' add to queue on printer: " + name ;
    }

    public String queue() {
        if (jobQueue.isEmpty()) {
            return "The print queue is empty.";
        } else {
            StringBuilder output = new StringBuilder();
            int jobNumber = 1;

            for (String job : jobQueue) {
                output.append(jobNumber).append(" ").append(job).append("\n");
                jobNumber++;
            }

            return output.toString();
        }
    }

    public String topQueue(int jobNumber) {
        if (jobNumber < 1 || jobNumber > jobQueue.size()) {
            return "Invalid job number.";
        }

        String[] jobs = jobQueue.toArray(new String[0]);
        String topJob = jobs[jobNumber - 1];
        jobQueue.clear();
        jobQueue.add(topJob);

        for (int i = 0; i < jobs.length; i++) {
            if (i != jobNumber - 1) {
                jobQueue.add(jobs[i]);
            }
        }

        return "Moved job " + jobNumber + " to the top of the queue.";
    }

    public String status() {
        return "Jobs in queue for " + name + ": " + jobQueue.size();
    }

    public void clear() {
        jobQueue.clear();
    }

}
