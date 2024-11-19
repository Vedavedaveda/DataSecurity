package common;

public class Parameters
{
    PaperSize paperSize = PaperSize.A4;
    PageLayout pageLayout = PageLayout.PORTRAIT;
    int pagesPerSheet = 1;
    ColorProfile colorProfile = ColorProfile.COLOR;

    private static final String[] VALID_PARAMETERS = {
            PaperSize.className,
            PageLayout.className,
            PagesPerSheet.className,
            ColorProfile.className
    };

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

    public static String gerParameterOptions() {
        StringBuilder options = new StringBuilder();
        for (String parameter : VALID_PARAMETERS) {
            options.append(parameter).append(", ");
        }
        return options.substring(0, options.length() - 2);
    }

    public static String getParameterPossibleValue(String parameter) {
        return switch (parameter) {
            case PaperSize.className -> PaperSize.getPaperSizeOptions();
            case PageLayout.className -> PageLayout.getPageLayoutOptions();
            case PagesPerSheet.className -> PagesPerSheet.getPagesPerSheetOptions();
            case ColorProfile.className -> ColorProfile.getColorProfileOptions();
            default -> "";
        };
    }

    public static Boolean isValidParameterValue(String parameter, String value) {
        return switch (parameter) {
            case PaperSize.className -> PaperSize.isValidPaperSize(value);
            case PageLayout.className -> PageLayout.isValidPageLayout(value);
            case PagesPerSheet.className -> PagesPerSheet.isValidNumberOfPagesPerSheet(value);
            case ColorProfile.className -> ColorProfile.isValidColorProfile(value);
            default -> false;
        };
    }

    public String readConfig(String parameter) {
        return switch (parameter) {
            case PaperSize.className -> "Value of " + parameter + " is set to: " + paperSize.getLabel() + "\n";
            case PageLayout.className -> "Value of " + parameter + " is set to: " + pageLayout.getLabel() + "\n";
            case PagesPerSheet.className -> "Value of " + parameter + " is set to: " + pagesPerSheet + "\n";
            case ColorProfile.className -> "Value of " + parameter + " is set to: " + colorProfile.getLabel() + "\n";
            default -> "Invalid parameter" + "\n";
        };
    }

    public String setConfig(String parameter, String value) {
        switch (parameter) {
            case PaperSize.className:
                paperSize = PaperSize.valueOf(value);
                break;

            case PageLayout.className:
                pageLayout = PageLayout.valueOf(value);
                break;

            case PagesPerSheet.className:
                pagesPerSheet = Integer.parseInt(value);
                break;

            case ColorProfile.className:
                colorProfile = ColorProfile.valueOf(value);
                break;
        }
        return "Value of " + parameter + " changed to: " + value + "\n";
    }

    public void clear() {
        paperSize = PaperSize.A4;
        pageLayout = PageLayout.PORTRAIT;
        pagesPerSheet = 1;
        colorProfile = ColorProfile.COLOR;
    }
}
