package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Tests the functionality in the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */

    public MainMethodResult invokeMain(String... args) {

        return invokeMain(Project3.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */

    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(),
                containsString("java -jar target/phonebill-2022.0.0.jar <options> <arguments>"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void testAccurateCommandLineWithPrint() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from 5/24/22, 12:50 PM to 5/24/22, 1:00 PM"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void testAccurateCommandLineWithPrintAndTextFile() {
        MainMethodResult result = invokeMain("-textfile", "src/test/resources/edu/pdx/cs410J/nmuller/valid-phonebill.txt", "-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from 5/24/22, 12:50 PM to 5/24/22, 1:00 PM"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void testInaccurateCommandLineWithPrintAndTextFile() {
        MainMethodResult result = invokeMain("-textfile", "src/test/resources/edu/pdx/cs410J/nmuller/valid-phonebill.txt", "-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from 5/24/22, 12:50 PM to 5/24/22, 1:00 PM"));

    }


    /**
     * Tests accurate command line arguments
     */
    @Test
    void testPrintwithTextFileOptionsWithPrintFirst() {
        MainMethodResult result = invokeMain("-print", "-textfile", "src/test/resources/edu/pdx/cs410J/nmuller/valid-phonebill.txt", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from 5/24/22, 12:50 PM to 5/24/22, 1:00 PM"));
    }


    /**
     * Tests inaccurate phone number argument
     */
    @Test
    void testInaccuratePhoneNumber() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-55", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use NNN-NNN-NNNN where N is 0-9 for phone numbers"));
    }

    /**
     * Tests inaccurate date argument
     */
    @Test
    void testInaccurateDate() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "5/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use MM/DD/YYYY format for date"));
    }

    /**
     * Tests inaccurate time argument
     */
    @Test
    void testInaccurateTime() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", ":50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use HH:MM format for time"));
    }

    /**
     * Tests inaccurate amount of arguments
     */
    @Test
    void notEnoughArguments() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("missing customer phone call information"));
    }

    /**
     * Tests inaccurate amount of arguments
     */
    @Test
    void tooManyArguments() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm", "one", "argument", "too", "many");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("extraneous argument on the command line"));
    }

    /**
     * Tests no option argument (-print, -readme)
     */
    @Test
    void noOptionArgument() {
        MainMethodResult result = invokeMain("Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString(""));

    }


    @Test
    void tryReadMeArgument() {
        MainMethodResult result = invokeMain("-readme");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("The command line options are -print, -readme, and -textfile <filepath>"));

    }

    /**
     * Tests no option argument (-print, -readme)
     */
    @Test
    void unknownOptionGiven() {
        MainMethodResult result = invokeMain("-fred", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("unknown option used"));

    }

    /**
     * Tests no option argument (-print, -readme)
     */
    @Test
    void onlyPrintGiven() {
        MainMethodResult result = invokeMain("-print");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString(""));

    }
//
//    /**
//     * Tests accurate command line arguments
//     */
//    @Test
//    void customerDoesNotMatchTheBill() {
//        MainMethodResult result = invokeMain("-print", "-textfile", "src/test/resources/edu/pdx/cs410J/nmuller/valid-phonebill.txt", "BillyBob 'the dipster' Dumper", "425-555-5555", "206-555-5555",
//                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
//        assertThat(result.getTextWrittenToStandardError(),
//                containsString("error reading from file: given customer does not match the bill."));
//    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void billHasNoCustomer() {
        MainMethodResult result = invokeMain("-print", "-textfile", "no-customer", "BillyBob Dumper", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("error reading from file: bill has no customer name"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void PhoneNumbersHaveCharacters() {
        MainMethodResult result = invokeMain("Test3", "ABC-123-4567", "123-456-7890", "03/03/2022", "12:00", "pm", "03/03/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("use NNN-NNN-NNNN where N is 0-9 for phone numbers"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void unknownOptionFromProject1Tests() {
        MainMethodResult result = invokeMain("--fred", "Test3", "425-123-4567", "123-456-7890", "03/03/2022", "pm", "12:00", "03/03/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("unknown option used"));
    }

    /**
     * Tests accurate command line arguments
     */
    @Test
    void extraneousArgumentsTest() {
        MainMethodResult result = invokeMain("Test3", "425-123-4567", "123-456-7890", "03/03/2022", "12:00", "pm", "03/03/2022", "1:00", "pm", "fred");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("extraneous argument"));
    }


    /**
     * Tests accurate command line arguments
     */
    @Test
    void testValidSTDOUTPrettyPrinter() {
        MainMethodResult result = invokeMain("-print", "-pretty", "-", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "pm", "05/24/2022", "1:00", "pm");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("10 minutes"));
    }


}