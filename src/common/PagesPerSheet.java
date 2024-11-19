package common;

public class PagesPerSheet {
    public static final String className = "Pages Per Sheet";
    private static final int[] VALID_OPTIONS = {1, 2, 4, 6, 9, 16};

    public static boolean isValidNumberOfPagesPerSheet(String input) {
        try {
            int value = Integer.parseInt(input);
            for (int option : VALID_OPTIONS) {
                if (value == option) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public static String getPagesPerSheetOptions() {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < VALID_OPTIONS.length; i++) {
            options.append(VALID_OPTIONS[i]);
            if (i < VALID_OPTIONS.length - 1) {
                options.append(", ");
            }
        }
        return options.toString();
    }

}
