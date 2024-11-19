package common;

import java.util.LinkedList;
import java.util.Queue;


public class Printer {
    private final Queue<String> jobQueue;
    private final String name;

    public Printer(String printerName) {
        jobQueue = new LinkedList<>();
        name = printerName;
    }

    public String print(String filename) {
        jobQueue.add(filename);
        return "File: '" + filename + "' add to queue on printer: " + name + "\n";
    }

    public String queue() {
        if (jobQueue.isEmpty()) {
            return "The print queue is empty.\n";
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
            return "Invalid job number.\n";
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

        return "Moved job " + jobNumber + " to the top of the queue.\n";
    }

    public String status() {
        return "Jobs in queue for " + name + ": " + jobQueue.size() + "\n";
    }

    public void clear() {
        jobQueue.clear();
    }

}
