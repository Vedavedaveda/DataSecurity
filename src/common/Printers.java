package common;

public enum Printers {
    PRINTER1("Printer 1"),
    PRINTER2("Printer 2"),
    PRINTER3("Printer 3");

    private final String label;

    Printers(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static int getIdFromLabel(String label) {
        char lastChar = label.charAt(label.length() - 1);
        return Character.getNumericValue(lastChar) - 1;
    }

    public static boolean isValidPrinter(String input) {
        for (Printers printer : Printers.values()) {
            if (printer.getLabel().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String getPrinterOptions() {
        StringBuilder options = new StringBuilder();
        for (Printers printer : Printers.values()) {
            options.append(printer.getLabel()).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

}

