package edu.pdx.cs410J.nmuller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static String CALLER_PARAMETER = "caller";
    static String CALLEE_PARAMETER = "callee";
    static String BEGIN_DATE_PARAMETER = "begin";
    static String END_DATE_PARAMETER = "end";


    static String CUSTOMER_PARAMETER = "customer";
    private final Map<String, PhoneBill> dictionary = new HashMap<>();



    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request);
        String beginString = getParameter(BEGIN_DATE_PARAMETER, request);
        String endString = getParameter(END_DATE_PARAMETER, request);
        if(beginString != null && endString != null && customer != null){
            try {
                writePhoneCallParams(customer, beginString, endString, response);
            } catch (ErrorCheck.MissingCommandLineArguments e) {
                e.printStackTrace();
            }
        }

        else if (customer != null) {
            if(dictionary.containsKey(customer))

                writePhoneCall(customer, response);
            else
                try {
                    throw new ErrorCheck.MissingCommandLineArguments("" +
                            "Invalid customer: customer does not exist");
                } catch (ErrorCheck.MissingCommandLineArguments e) {
                    e.printStackTrace();
                }
        } else {
            writeAllDictionaryEntries(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {

        response.setContentType( "text/plain" );
        if(this.dictionary.containsKey(getParameter(CUSTOMER_PARAMETER, request))){
            try {
                this.dictionary.get(getParameter(CUSTOMER_PARAMETER, request)).addPhoneCall(PhoneCall.createNewCall(getParameter(CALLER_PARAMETER, request), getParameter(CALLEE_PARAMETER, request), getParameter(BEGIN_DATE_PARAMETER, request), getParameter(END_DATE_PARAMETER, request)));
            }catch (ErrorCheck.MissingCommandLineArguments e){}
        } else {
            this.dictionary.put(getParameter(CUSTOMER_PARAMETER, request), new
                    PhoneBill(CUSTOMER_PARAMETER));
            try{
                this.dictionary.get(getParameter(CUSTOMER_PARAMETER, request)).addPhoneCall(PhoneCall.createNewCall(getParameter(CALLER_PARAMETER, request), getParameter(CALLEE_PARAMETER, request), getParameter(BEGIN_DATE_PARAMETER, request), getParameter(END_DATE_PARAMETER, request)));
            }catch (ErrorCheck.MissingCommandLineArguments e){}
        }

        response.setStatus( HttpServletResponse.SC_OK);
    }


    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writePhoneCall(String customer, HttpServletResponse response) throws IOException {
        ArrayList<PhoneCall> phoneCall = this.dictionary.get(CUSTOMER_PARAMETER).getPhoneCalls();


        if (phoneCall == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();


            Map<String, PhoneBill> wordDefinition = Map.of(customer, this.dictionary.get(CUSTOMER_PARAMETER));
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(wordDefinition);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * Writes the definition of the given word to the HTTP response with date/time params
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writePhoneCallParams(String customer, String beginDate, String endDate, HttpServletResponse response) throws IOException, ErrorCheck.MissingCommandLineArguments {
        ArrayList<PhoneCall> phoneCall = this.dictionary.get(customer).getPhoneCalls();
        if (phoneCall == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US);
            Date begin = new Date();
            Date end = new Date();
            try {
                begin = formatter.parse(beginDate);
                end = formatter.parse(endDate);
            }catch(Exception errParse){
                System.err.println("Unknown Date Format " + errParse);
            }
            if(!ErrorCheck.checkTimeTravel(begin, end)) {
                throw new ErrorCheck.MissingCommandLineArguments("Time travel has been detected, please input accurate date and time");
            }
                PrintWriter pw = response.getWriter();
                PhoneBill newBill = new PhoneBill(customer);
            for(PhoneCall oneCall : phoneCall) {
                if (oneCall.getBeginTime().compareTo(begin) <= 0 && oneCall.getEndTime().compareTo(end) >= 0) {
                    newBill.addPhoneCall(oneCall);
                }
            }

                Map<String, PhoneBill> phoneParams = Map.of(customer, newBill);

                TextDumper dumper = new TextDumper(pw);
                dumper.dump(phoneParams);

                response.setStatus(HttpServletResponse.SC_OK);
        }
    }









    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(dictionary);

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

}
