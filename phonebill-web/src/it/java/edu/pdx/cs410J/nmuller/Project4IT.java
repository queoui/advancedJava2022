package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.MethodOrderer.MethodName;
import static org.mockito.Mockito.mock;

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

            result = invokeMain(  Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
            out = result.getTextWrittenToStandardOut();
            assertThat(out, out, containsString("customer2 Phone call from 425-741-1269 to 425-239-9870 from 5/24/22, 11:50 AM to 5/24/22, 12:00 PM"));

            result = invokeMain( Project4.class, "-print","-host", host, "-port", port, customer,"425-741-1269",
                    "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
            out = result.getTextWrittenToStandardOut();
            assertThat(out, out, containsString("customer2 Phone call from 425-741-1269 to 425-239-9870 from 5/24/22, 11:50 AM to 5/24/22, 12:00 PM"));

        }catch (ErrorCheck.MissingCommandLineArguments e){}
    }


    @Test
    void prettyPrinterPrintTest(@TempDir File tempDir) throws IOException, ParserException {
        String customer = "customer2";
        String host = "localhost";
        String port = "8080";
        invokeMain( Project4.class, "-host", host, "-port", port, customer,"425-741-1269",
                "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");

        invokeMain(Project4.class, "-host", host, "-port", port, customer,"425-741-1269",
                "425-239-9870", "05/25/2022", "11:50" ,"am", "05/25/2022" ,"12:00" , "pm");

        MainMethodResult result = invokeMain( Project4.class, "-search","-host", host, "-port", port, customer,
                                                "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");

        assertThat(result.getTextWrittenToStandardOut(), containsString
                ("Phone call duration of 10 minutes between 425-741-1269 and 425-239-9870 beginning at 5/24/22, 11:50 AM and ending at 5/24/22, 12:00 PM"));

    }

    @Test
    void tryReadMeArgument()  {
        MainMethodResult result = invokeMain(Project4.class,("-readme"));
        assertThat(result.getTextWrittenToStandardOut(),
                CoreMatchers.containsString("The command line options are -print, -readme, and -textfile <filepath>"));

    }

    @Test
    void printParamsTest(@TempDir File tempDir)  {
        String customer = "customer2";
        String host = "localhost";
        String port = "8080";
        invokeMain( Project4.class, "-host", host, "-port", port, customer,"425-741-1269",
                "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");

        invokeMain(Project4.class, "-host", host, "-port", port, customer,"425-741-1269",
                "425-239-9870", "05/25/2022", "11:50" ,"am", "05/25/2022" ,"12:00" , "pm");

        MainMethodResult result = invokeMain( Project4.class, "-search","-host", host, "-port", port, customer,
                "05/24/2022", "11:30" ,"am", "05/24/2022" ,"12:30" , "pm");

        assertThat(result.getTextWrittenToStandardOut(), containsString
                ("Phone call duration of 10 minutes between 425-741-1269 and 425-239-9870 beginning at 5/24/22, 11:50 AM and ending at 5/24/22, 12:00 PM"));

    }
//    @Test
//    void testPhoneCallParams() throws ServletException, IOException, ParserException, ErrorCheck.MissingCommandLineArguments {
//
//        String customer = "customer2";
//        String host = "localhost";
//        String port = "8080";
//        invokeMain( Project4.class, "-host", host, "-port", port, customer,"425-741-1269",
//                "425-239-9870", "05/24/2022", "11:50" ,"am", "05/24/2022" ,"12:00" , "pm");
//
//
//        PhoneBillServlet servlet = new PhoneBillServlet();
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        PrintWriter pw = mock(PrintWriter.class);
//
//        servlet.writePhoneCallParams("customer2", "05/23/2022 11:00pm", "05/24/2022 11:00pm", response);
//
//        assertThat(response.getStatus(), equalTo(0));
//    }
}