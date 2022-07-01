package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */

    public MainMethodResult invokeMain(String... args) {

        return invokeMain(Project1.class, args);
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

    @Test
    void testAccurateCommandLineWithPrint() {
        MainMethodResult result = invokeMain("-print", "Nick Muller", "425-555-5555", "206-555-5555",
                "05/24/2022", "12:50", "05/24/2022", "1:00");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 425-555-5555 to 206-555-5555 from " +
                        "05/24/2022 12:50 to 05/24/2022 1:00"));
    }
}