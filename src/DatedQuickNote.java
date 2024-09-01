import java.util.Arrays;
import java.time.LocalDate;

public class DatedQuickNote extends ObsidianNote{

    // first X: the date (e.g., 27)
    // second X: abbreviation of the month (e.g., dec for December)
    // third X: the year (e.g., 2023)
    static String generalToDoListNoteFileName = "XXX";

    static String[] generalNoteContent = {
            "placeholder", // this is for the very first checkbox
            "",
            "---",
            "Previous Quick Note: [[X]]", // X: file name of previous dated to do list note
            "Next Quick Note: [[X]]" // X: file name of next dated to do list note
    };

    LocalDate localDate; // the values for dayOfMonth, month, and year will be extracted from the corresponding LocalDate object

    String dayOfMonth;
    String month;
    String year;


    DatedQuickNote(String fileName, String[] noteContent, String dayOfMonth, String month, String year) {
        super(fileName, noteContent);
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    /**
     *
     * @param currentDate This is the date of which the specific note content will be for.
     *                    We need this so that we can determine the previous date and the next date which are needed to generate the specific note content
     * @return the specific note content (file content) of current to do list
     */
    public static String[] returnSpecificNoteContent(LocalDate currentDate)
    {
        String[] specificNoteContent = Arrays.copyOf(generalNoteContent, generalNoteContent.length);

        LocalDate prevDate = currentDate.minusDays(1);
        LocalDate nextDate = currentDate.plusDays(1);

        String[] prevDateRelevantStrings = DateFunctions.returnLocalDateRelevantStrings(prevDate);
        String prevDateDayOfMonth = prevDateRelevantStrings[0];
        String prevDateAbbrevMonth = prevDateRelevantStrings[1];
        String prevDateYear = prevDateRelevantStrings[2];
        String prevDateFileName = prevDateDayOfMonth + prevDateAbbrevMonth + prevDateYear + "QuickNote";

        String[] nextDateRelevantStrings = DateFunctions.returnLocalDateRelevantStrings(nextDate);
        String nextDateDayOfMonth = nextDateRelevantStrings[0];
        String nextDateAbbrevMonth = nextDateRelevantStrings[1];
        String nextDateYear = nextDateRelevantStrings[2];
        String nextDateFileName = nextDateDayOfMonth + nextDateAbbrevMonth + nextDateYear + "QuickNote";

        int count = 0; // used to determine which X currently replacing in order to replace with the correct value
        for (int i = 0; i < specificNoteContent.length; i++) {
            if (specificNoteContent[i].contains("X")) {
                count++;
                if (count == 1) // this X is for the file name of the previous note
                {
                    specificNoteContent[i] = specificNoteContent[i].replaceFirst("X", prevDateFileName);
                }
                else if (count == 2) // this X is for the file name of the next note
                {
                    specificNoteContent[i] = specificNoteContent[i].replaceFirst("X", nextDateFileName);
                    break;
                }
            }
        }

        return specificNoteContent;
    }

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        String[] specificNoteContent = returnSpecificNoteContent(localDate);
        System.out.println(Arrays.toString(specificNoteContent));
    }
}
