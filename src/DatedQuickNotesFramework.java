import java.time.LocalDate;
import java.util.List;

public class DatedQuickNotesFramework {

    static String datedToDoListNotesRootNoteName = "datedQuickNotesRootNote";

    // this list will hold the LocalDate objects that correspond to each DatedQuickNote object
    // is List and not LocalDate[] because the method used to generate the sequence of LocalDates returns a list
    List<LocalDate> localDates;
    DatedQuickNote[] datedQuickNotes; // this array will hold the individual DatedQuickNote objects that will be part of the framework
    String[] rootNoteStrings;

    public DatedQuickNotesFramework(LocalDate startDate, LocalDate endDate)
    {
        localDates = DateFunctions.generateSequentialDates(startDate, endDate);
        datedQuickNotes = new DatedQuickNote[localDates.size()];
        rootNoteStrings = new String[localDates.size()];

        // instantiate the individual DatedQuickNote objects and store them into the array
        for(int i = 0; i < datedQuickNotes.length; i++)
        {
            // generate the file name of the markdown file
            String[] currentDateRelevantStrings = DateFunctions.returnLocalDateRelevantStrings(localDates.get(i));
            String dayOfMonth = currentDateRelevantStrings[0];
            String abbreviatedMonth = currentDateRelevantStrings[1];
            String year = currentDateRelevantStrings[2];
            String fileName = dayOfMonth + abbreviatedMonth + year + "QuickNote";

            // generate the note content
            String[] specificNoteContent = DatedQuickNote.returnSpecificNoteContent(localDates.get(i));

            datedQuickNotes[i] = new DatedQuickNote(fileName, specificNoteContent, dayOfMonth, abbreviatedMonth, year);
        }
    }

    void generateFiles()
    {
        for(DatedQuickNote datedQuickNote : datedQuickNotes)
        {
            datedQuickNote.generateNoteFile();
        }
    }

    void generateRootNoteStrings()
    {
        System.out.println("Entries to be added to the root note file: ");
        int index = 0;
        for(LocalDate localDate : localDates)
        {
            String month = DateFunctions.returnAbbreviatedStringOfMonth(localDate.getMonth());
            String dayOfMonth = Integer.toString(localDate.getDayOfMonth());
            String year = Integer.toString(localDate.getYear());
            String noteEntry = month + " " + dayOfMonth + ", " + year + ": " + "[[" + dayOfMonth + month + year + "QuickNote]]";
            rootNoteStrings[index] = noteEntry;
            index++;
        }

        // print the strings in reverse order to comply with the root note format
        for(int i = rootNoteStrings.length-1; i >= 0; i--)
        {
            System.out.println(rootNoteStrings[i]);
        }
    }

    public static void main(String[] args) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        boolean validStartAndEndDatesEntered = false; // assume false initially

        // before moving forward, have to make sure that the end date comes after the start date
        while(!validStartAndEndDatesEntered)
        {
            System.out.println("***Enter information for the start date***");
            startDate = DateFunctions.promptForDate();
            System.out.println("***Enter information for the end date***");
            endDate = DateFunctions.promptForDate();

            if(startDate.isBefore(endDate))
            {
                validStartAndEndDatesEntered = true;
            }
        }

        DatedQuickNotesFramework datedQuickNotesFramework = new DatedQuickNotesFramework(startDate, endDate);
        datedQuickNotesFramework.generateFiles();

        datedQuickNotesFramework.generateRootNoteStrings();
        return;
    }
}
