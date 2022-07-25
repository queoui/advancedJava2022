package edu.pdx.cs410J.nmuller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
    static String BEGIN_DATE_PARAMETER = "beginDate";
    static String END_DATE_PARAMETER = "endDate";


    static final String CUSTOMER_PARAMETER = "customer";




    private final Map<String, PhoneBill> dictionary = new HashMap<>();

//    /**
//     * Handles an HTTP GET request from a client by writing the definition of the
//     * word specified in the "word" HTTP parameter to the HTTP response.  If the
//     * "word" parameter is not specified, all of the entries in the dictionary
//     * are written to the HTTP response.
//     */
//    @Override
//    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
//    {
//        response.setContentType( "text/plain" );
//
//        String word = getParameter(WORD_PARAMETER, request );
//        if (word != null) {
//            writeDefinition(word, response);
//
//        } else {
//            writeAllDictionaryEntries(response);
//        }
//    }

    //^^^^^^^^^^^^^^^STILL NEEDS TO BE RE_IMPLEMENTED^^^^^^^^^^^^^
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
        if (customer != null) {
            writePhoneCall(customer, response);

        } else {
            writeAllDictionaryEntries(response);
        }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {

        response.setContentType( "text/plain" );
        if(this.dictionary.containsKey(CUSTOMER_PARAMETER)){
            try {
                this.dictionary.get(CUSTOMER_PARAMETER).addPhoneCall(PhoneCall.createNewCall(getParameter(CALLER_PARAMETER, request), getParameter(CALLEE_PARAMETER, request), getParameter(BEGIN_DATE_PARAMETER, request), getParameter(END_DATE_PARAMETER, request)));
//                String putCalls = this.dictionary.get(CUSTOMER_PARAMETER).toString();
//                this.dictionary.put(putCalls);
            }catch (ErrorCheck.MissingCommandLineArguments e){}
        } else {
            this.dictionary.put(CUSTOMER_PARAMETER, new
                    PhoneBill(CUSTOMER_PARAMETER));
            try{
                this.dictionary.get(CUSTOMER_PARAMETER).addPhoneCall(PhoneCall.createNewCall(getParameter(CALLER_PARAMETER, request), getParameter(CALLEE_PARAMETER, request), getParameter(BEGIN_DATE_PARAMETER, request), getParameter(END_DATE_PARAMETER, request)));
            }catch (ErrorCheck.MissingCommandLineArguments e){}
        }

//        else {
//
//            String phoneBill = getParameter(PHONE_BILL_PARAMETER, request);
//            if (phoneBill == null) {
//                missingRequiredParameter(response, PHONE_BILL_PARAMETER);
//                return;
//            }
//
//            String phoneCall = getParameter(PHONE_CALL_PARAMETER, request);
//            if (phoneCall == null) {
//                missingRequiredParameter(response, PHONE_CALL_PARAMETER);
//                return;
//            }

//            this.dictionary.put(phoneBill, phoneCall);
//
//            PrintWriter pw = response.getWriter();
//            pw.println(Messages.displayPhoneCallAs(phoneBill, phoneCall));
//            pw.flush();
//        }

        response.setStatus( HttpServletResponse.SC_OK);
    }






//    /**
//     * Handles an HTTP POST request by storing the dictionary entry for the
//     * "word" and "definition" request parameters.  It writes the dictionary
//     * entry to the HTTP response.
//     */
//@Override
//protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
//{
//    response.setContentType( "text/plain" );
//
//    String customer = getParameter(CUSTOMER_PARAMETER, request );
//    if (customer == null) {
//        missingRequiredParameter(response, CUSTOMER_PARAMETER);
//        return;
//    }
//
//    String caller = getParameter(CALLER_PARAMETER, request );
//    if (caller == null) {
//        missingRequiredParameter(response, CALLER_PARAMETER);
//        return;
//    }
//
//    String callee = getParameter(CALLEE_PARAMETER, request );
//    if (callee == null) {
//        missingRequiredParameter(response, CALLEE_PARAMETER);
//        return;
//    }
//
//    String beginDate = getParameter(BEGIN_DATE_PARAMETER, request );
//    if (beginDate == null) {
//        missingRequiredParameter(response, BEGIN_DATE_PARAMETER);
//        return;
//    }
//
//    String endDate = getParameter(END_DATE_PARAMETER, request );
//    if (endDate == null) {
//        missingRequiredParameter(response, END_DATE_PARAMETER);
//        return;
//    }
//
//    this.dictionary.put(customer, (caller +" "+ callee +" "+ beginDate +" "+ endDate));
//
//    PrintWriter pw = response.getWriter();
//    pw.println(Messages.displayPhoneCallAs(customer, (caller +" "+ callee +" "+ beginDate +" "+ endDate)));
//    pw.flush();
//
//    response.setStatus( HttpServletResponse.SC_OK);
//}

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
        String phoneCall= this.dictionary.get(customer).getPhoneCalls().toString();

        if (phoneCall == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();

            Map<String, PhoneBill> wordDefinition = Map.of(customer, this.dictionary.get(customer));
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(wordDefinition);

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

//
//    @VisibleForTesting
//    String getDefinition(String word) {
//        return this.dictionary.get(word);
//    }

}
