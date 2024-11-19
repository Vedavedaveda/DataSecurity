package common;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameters
{
    private static final String PAPER_SIZE = "Paper Size";
    private static final String PAGE_LAYOUT = "Page Layout";
    private static final String PAGES_PER_SHEET = "Pages Per Sheet";
    private static final String COLOR_PROFILE = "Color Profile";

    private static final String[] VALID_PARAMETERS = {
            PAPER_SIZE,
            PAGE_LAYOUT,
            PAGES_PER_SHEET,
            COLOR_PROFILE
    };

    private static final Map<String, List<String>> VALID_PARAMETERS_VALUES = new HashMap<>();
    static {
        VALID_PARAMETERS_VALUES.put(PAPER_SIZE, Arrays.asList("A1", "A2", "A3", "A4", "A5", "Letter", "Legal"));
        VALID_PARAMETERS_VALUES.put(PAGE_LAYOUT, Arrays.asList("Portrait", "Landscape"));
        VALID_PARAMETERS_VALUES.put(PAGES_PER_SHEET, Arrays.asList("1", "2", "4", "6", "9", "16"));
        VALID_PARAMETERS_VALUES.put(COLOR_PROFILE, Arrays.asList("Color", "Greyscale"));
    }

    private static final Map<String, String> parameters = new HashMap<>();
    static {
        parameters.put(PAPER_SIZE, "A4");
        parameters.put(PAGE_LAYOUT, "Portrait");
        parameters.put(PAGES_PER_SHEET, "1");
        parameters.put(COLOR_PROFILE, "Color");
    }


    public Parameters() {
    }

    public static boolean isValidParameter(String input) {
        for (String parameter : VALID_PARAMETERS) {
            if (parameter.equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String getParameterOptions() {
        StringBuilder options = new StringBuilder();
        for (String parameter : VALID_PARAMETERS) {
            options.append(parameter).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

    public static String getParameterPossibleValue(String parameter) {
        StringBuilder options = new StringBuilder();
        for (String value : VALID_PARAMETERS_VALUES.get(parameter)) {
            options.append(value).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

    public static Boolean isValidParameterValue(String parameter, String value) {
        for (String possible_value : VALID_PARAMETERS_VALUES.get(parameter)) {
            if (possible_value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public String readConfig(String parameter) {
        return "Value of " + parameter + " is set to: " + parameters.get(parameter) + "\n";
    }

    public String setConfig(String parameter, String value) {
        parameters.put(parameter, value);
        return "Value of " + parameter + " changed to: " + value + "\n";
    }

    public void clear() {
        parameters.put(PAPER_SIZE, "A4");
        parameters.put(PAGE_LAYOUT, "Portrait");
        parameters.put(PAGES_PER_SHEET, "1");
        parameters.put(COLOR_PROFILE, "Color");
    }
}
