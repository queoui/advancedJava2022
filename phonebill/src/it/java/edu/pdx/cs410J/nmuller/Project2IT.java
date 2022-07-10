package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */

    public MainMethodResult invokeMain(String... args) {

        return invokeMain(Project2.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */

    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Missing command line arguments"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void testAccurateCommandLineWithPrint() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from " +
                        "05/24/2022 12:50 to 05/24/2022 1:00"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void testAccurateCommandLineWithPrintAndTextFile() {
        MainMethodResult result = invokeMain("-textfile", "file", "-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from " +
                        "05/24/2022 12:50 to 05/24/2022 1:00"));
    }


    /**
     * Tests accurate command line arguments
     */
    @Test
    void testPrintwithTextFileOptionsWithPrintFirst() {
        MainMethodResult result = invokeMain("-print", "-textfile", "file", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from " +
                        "05/24/2022 12:50 to 05/24/2022 1:00"));
    }


    /**
     * Tests inaccurate phone number argument
     */
    @Test
    void testInaccuratePhoneNumber() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-55", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use NNN-NNN-NNNN where N is 0-9 for phone numbers"));
    }

    /**
     * Tests inaccurate date argument
     */
    @Test
    void testInaccurateDate() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "5/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use MM/DD/YYYY format for date\nuse HH:MM format for time"));
    }

    /**
     * Tests inaccurate time argument
     */
    @Test
    void testInaccurateTime() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", ":50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use MM/DD/YYYY format for date\nuse HH:MM format for time"));
    }

    /**
     * Tests inaccurate amount of arguments
     */
    @Test
    void notEnoughArguments() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Missing command line arguments."));
    }

    /**
     * Tests inaccurate amount of arguments
     */
    @Test
    void tooManyArguments() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00", "one", "argument", "too", "many");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Too many command line arguments."));
    }

    /**
     * Tests no option argument (-print, -readme)
     */
    @Test
    void noOptionArgument() {
        MainMethodResult result = invokeMain("Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString(""));

    }


    @Test
    void tryReadMeArgument() {
        MainMethodResult result = invokeMain("-readme");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("The command line options are -print and -readme."));

    }


}