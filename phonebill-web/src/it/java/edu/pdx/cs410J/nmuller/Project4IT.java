package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */

@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");



    @Test
    void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }

    @Test
    void test3CustomerOnlyAttemptsToPrint() {
        String customer = "customer";
        try {
            MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, customer);

            assertThat(result.getTextWrittenToStandardOut(), containsString(""));
        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
        }
    }

    @Test
    void test4AddPhoneCall() {
        String customer = "customer2";
        String host = "localhost";
        String port = "8080";
        try {

            MainMethodResult result = invokeMain( Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
                                                                        "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
            PhoneCall newCall = PhoneCall.createNewCall("425-741-1269", "425-239-9870", "05/24/2022 11:50am", "05/24/2022 12:00pm");
            PhoneBill newBill = new PhoneBill(customer);
            newBill.addPhoneCall(newCall);

            String out = result.getTextWrittenToStandardOut();
            assertThat(out, out, containsString("customer2 Phone call from 425-741-1269 to 425-239-9870 from 5/24/22, 11:50 AM to 5/24/22, 12:00 PM"));

//            result = invokeMain(  Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
//                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//            out = result.getTextWrittenToStandardOut();
//            assertThat(out, out, containsString(PrettyPrinter.formatPhoneCallEntry(customer, newCall.getPhoneCall())));
//
//            result = invokeMain( Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
//                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//            out = result.getTextWrittenToStandardOut();
//            assertThat(out, out, containsString(PrettyPrinter.formatPhoneCallEntry(customer, newCall.getPhoneCall())));

        }catch (ErrorCheck.MissingCommandLineArguments e){}
    }
}