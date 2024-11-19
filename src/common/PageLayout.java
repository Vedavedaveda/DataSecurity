package common;

public enum PageLayout {
    PORTRAIT("Portrait"),
    LANDSCAPE("Landscape");

    private final String label;
    public static final String className = "Page Layout";

    PageLayout(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValidPageLayout(String input) {
        for (PageLayout pageLayout : PageLayout.values()) {
            if (pageLayout.getLabel().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String getPageLayoutOptions() {
        StringBuilder options = new StringBuilder();
        for (PageLayout pageLayout : PageLayout.values()) {
            options.append(pageLayout.getLabel()).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }
}
