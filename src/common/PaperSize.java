package common;

public enum PaperSize {
    LEGAL("Legal"),
    LETTER("Letter"),
    TABLOID("Tabloid"),
    A0("A0"),
    A1("A1"),
    A2("A2"),
    A3("A3"),
    A4("A4"),
    A5("A5");

    private final String label;
    public static final String className = "Paper Size";

    PaperSize(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValidPaperSize(String input) {
        for (PaperSize paperSize : PaperSize.values()) {
            if (paperSize.getLabel().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String getPaperSizeOptions() {
        StringBuilder options = new StringBuilder();
        for (PaperSize paperSize : PaperSize.values()) {
            options.append(paperSize.getLabel()).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

}
