package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */

@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");


    @Ignore
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

//    @Test
//    void test2EmptyServer() {
//        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(PrettyPrinter.formatWordCount(0)));
//    }

//    @Test
//    void test3NoDefinitionsThrowsAppointmentBookRestException() {
//        String word = "WORD";
//        try {
//            invokeMain(Project4.class, HOSTNAME, PORT, word);
//            fail("Expected a RestException to be thrown");
//
//        } catch (UncaughtExceptionInMain ex) {
//            RestException cause = (RestException) ex.getCause();
//            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
//        }
//    }
//
//    @Test
//    void test4AddPhoneCall() {
//        String customer = "customer2";
//        String host = "localhost";
//        String port = "8080";
//        try {
//            PhoneCall newCall = PhoneCall.createNewCall("425-741-1269", "425-239-9870", "05/24/2022 11:50am", "05/24/2022 12:00pm");
//
//
//            MainMethodResult result = invokeMain( Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
//                                                                        "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//            String out = result.getTextWrittenToStandardOut();
//            assertThat(out, out, containsString(Messages.displayPhoneCallAs(customer, newCall.getPhoneCall())));
//
//
//            result = invokeMain(  Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
//                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//            out = result.getTextWrittenToStandardOut();
//            assertThat(out, out, containsString(PrettyPrinter.formatPhoneCallEntry(customer, newCall.getPhoneCall())));
//
//            result = invokeMain( Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
//                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//            out = result.getTextWrittenToStandardOut();
//            assertThat(out, out, containsString(PrettyPrinter.formatPhoneCallEntry(customer, newCall.getPhoneCall())));
//
//        }catch (ErrorCheck.MissingCommandLineArguments e){}
//    }
}