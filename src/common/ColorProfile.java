package common;

public enum ColorProfile {
    COLOR("Color"),
    GREYSCALE("Greyscale");

    private final String label;
    public static final String className = "Color Profile";

    ColorProfile(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValidColorProfile(String input) {
        for (ColorProfile colorProfile : ColorProfile.values()) {
            if (colorProfile.getLabel().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String getColorProfileOptions() {
        StringBuilder options = new StringBuilder();
        for (ColorProfile colorProfile : ColorProfile.values()) {
            options.append(colorProfile.getLabel()).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

}
