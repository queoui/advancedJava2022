package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        boolean search = false;
        boolean print = false;
        int optionsTotal = 0;
        int port = 0;
        String customer = null;
        String caller = null;
        String callee = null;
        String beginTime = null;
        String endTime = null;
        PhoneBill newBill = null;
        PhoneCall newCall = null;


        if (args.length == 0) {
            //print usage and exit
            usage("Missing command line arguments");
        } else {
            for (int i = 0; i < args.length; ++i) {
                if (args[i].equalsIgnoreCase("-readme")) {
                    //print readme and exit
                    try {
                        getReadMe();
                    } catch (IOException e) {
                        System.err.println("Unable to find README" + e);
                    }
                } else if ((args[i].equalsIgnoreCase("-host"))) {
                    optionsTotal += 2;
                    hostName = args[i + 1];
                    ++i;
                } else if ((args[i].equalsIgnoreCase("-port"))) {
                    optionsTotal += 2;
                    portString = args[i + 1];
                    ++i;
                } else if ((args[i].equalsIgnoreCase("-search"))) {
                    optionsTotal += 1;
                    search = true;
                } else if ((args[i].equalsIgnoreCase("-print"))) {
                    optionsTotal += 1;
                    print = true;
                }
            }
                if (hostName == null) {
                    usage(MISSING_ARGS);

                } else if (portString == null) {
                    usage("Missing port");
                }

                try {
                    port = Integer.parseInt(portString);

                } catch (NumberFormatException ex) {
                    usage("Port \"" + portString + "\" must be an integer");
                    return;
                }

                if (search) {
                    for (int i = optionsTotal; i < args.length; ++i) {

                        if (customer == null) {
                            customer = args[i];

                        } else if (beginTime == null) {

                            //check out of bounds error
                            try{
                            ErrorCheck.checkDate(args[i]);
                            ErrorCheck.checkTime(args[i+1]+args[i+2]);
                            beginTime = args[i] + " " + args[i + 1]  + args[i + 2];
                            i += 2;
                            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                                System.err.println("**"+e);}

                        } else if (endTime == null) {

                            //try block out of bounds error
                            try{
                            ErrorCheck.checkDate(args[i]);
                            ErrorCheck.checkTime(args[i+1]+args[i+2]);
                            endTime = args[i] + " " + args[i + 1] + args[i + 2];
                            i += 2;
                            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                                System.err.println("**"+e);}
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }

                    //get the data between the date times and pretty print to stdout


                } else if ((args.length == 5)) {
                    for (int i = optionsTotal; i < args.length; ++i) {
                        if (customer == null) {
                            customer = args[i];
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }

                    //get the phonebill data for the given customer and pretty print to stdout

                } else {
                    for (int i = optionsTotal; i < args.length; ++i) {

                        if (customer == null) {
                            customer = args[i];

                        } else if (caller == null) {
                            try {
                                ErrorCheck.checkPhoneNumber(args[i]);
                                caller = args[i];
                            }catch(IllegalArgumentException e){
                                System.err.println("**" +e);
                            }

                        } else if (callee == null) {
                            try {
                                ErrorCheck.checkPhoneNumber(args[i]);
                                callee = args[i];
                            }catch(IllegalArgumentException e){
                                System.err.println("**" +e);
                            }

                        } else if (beginTime == null) {

                            //check out of bounds error
                            try{
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i+1]+args[i+2]);
                                beginTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                                System.err.println("**" +e);}

                        } else if (endTime == null) {

                            //try block out of bounds error
                            try{
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i+1]+args[i+2]);
                                endTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                                System.err.println("**"+e);}
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }
                    try{
                        newBill = new PhoneBill(customer);
                        newCall = PhoneCall.createNewCall(caller, callee, beginTime, endTime);
                        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

                        client.addPhoneCallEntry(customer, newCall);




                                        //*******change this error *********************//
                    }catch(ErrorCheck.MissingCommandLineArguments | IOException e){System.err.println("TESTING AN ADD CALL FAILED");}




                    if (print) {

                        //print the new phone call data to stdout
                        System.out.println(Messages.displayPhoneCallAs(customer, newCall.toString()));

                    }
                }
            }



        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
    }

//        String message;
//        try {
//            if (word == null) {
//                // Print all word/definition pairs
//                Map<String, String> dictionary = client.getAllDictionaryEntries();
//                StringWriter sw = new StringWriter();
//                PrettyPrinter pretty = new PrettyPrinter(sw);
//                pretty.dump(dictionary);
//                message = sw.toString();
//
//            } else if (definition == null) {
//                // Print all dictionary entries
//                message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));
//
//            } else {
//                // Post the word/definition pair
//                client.addDictionaryEntry(word, definition);
//                message = Messages.definedWordAs(word, definition);
//            }
//
//        } catch (IOException | ParserException ex ) {
//            error("While contacting server: " + ex);
//            return;
//        }
//
//        System.out.println(message);
//    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getHttpStatusCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getHttpStatusCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    public static void getReadMe() throws IOException{
        try (InputStream readme = Project4.class.getResourceAsStream("README.txt")) {
            BufferedReader reader = new BufferedReader((new InputStreamReader(readme)));
            String curr;
            while ((curr = reader.readLine()) != null) {
                System.out.println(curr);
            }
            reader.close();
        } catch (IOException exception) {
            throw new IOException("README file not available. " + exception.getMessage());
        }
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}