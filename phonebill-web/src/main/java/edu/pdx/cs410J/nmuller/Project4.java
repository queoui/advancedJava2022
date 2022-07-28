package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;


import java.io.*;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args){
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
                            try {
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i + 1] + args[i + 2]);
                                beginTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                                System.err.println("**" + e);
                            }

                        } else if (endTime == null) {

                            //try block out of bounds error
                            try {
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i + 1] + args[i + 2]);
                                endTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                                System.err.println("**" + e);
                            }
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }

                    //get the data between the date times and pretty print to stdout
                    PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

                    try {
                        //PRETTY PRINTER
                        Writer tempWriter = new OutputStreamWriter(System.out);
                        PrettyPrinter prettyPrinter = new PrettyPrinter(tempWriter);
                        try {
                            Map<String, PhoneBill> prettyMap = (client.getCustomerParams(customer, beginTime, endTime));
                            if (prettyMap.values().size() == 0) {
                                System.out.println("No matches for that date/time range");
                            }
                            prettyPrinter.dump(prettyMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserException e) {
                            e.printStackTrace();
                        }
                    } catch (NullPointerException e) {
                        System.err.println("Invalid customer, does not exist");
                    }


                    // DISPLAY ALL IN A RANGE //


                } else if ((args.length == 5)) {
                    for (int i = optionsTotal; i < args.length; ++i) {
                        if (customer == null) {
                            customer = args[i];
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }

                    PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

                    try {
                        //PRETTY PRINTER
                        Writer tempWriter = new OutputStreamWriter(System.out);
                        PrettyPrinter prettyPrinter = new PrettyPrinter(tempWriter);
                        try {
                            Map<String, PhoneBill> prettyMap = (client.getCustomerBill(customer));
                            prettyPrinter.dump(prettyMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserException e) {
                            e.printStackTrace();
                        }
                    } catch (NullPointerException e) {
                        System.err.println("Invalid customer, does not exist");
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
                            } catch (IllegalArgumentException e) {
                                System.err.println("**" + e);
                            }

                        } else if (callee == null) {
                            try {
                                ErrorCheck.checkPhoneNumber(args[i]);
                                callee = args[i];
                            } catch (IllegalArgumentException e) {
                                System.err.println("**" + e);
                            }

                        } else if (beginTime == null) {

                            //check out of bounds error
                            try {
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i + 1] + args[i + 2]);
                                beginTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                                System.err.println("**" + e);
                            }

                        } else if (endTime == null) {

                            //try block out of bounds error
                            try {
                                ErrorCheck.checkDate(args[i]);
                                ErrorCheck.checkTime(args[i + 1] + args[i + 2]);
                                endTime = args[i] + " " + args[i + 1] + args[i + 2];
                                i += 2;
                            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                                System.err.println("**" + e);
                            }
                        } else {
                            usage("Extraneous command line argument: " + args[i]);
                        }
                    }
                    try {

                        newCall = PhoneCall.createNewCall(caller, callee, beginTime, endTime);
                        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
                        client.addPhoneCallEntry(customer, caller, callee, beginTime, endTime);
                    } catch (ErrorCheck.MissingCommandLineArguments | IOException e) {
                        System.err.println("Unable to add phone call to bill" + e);
                    }
                }


                if (print) {
                    //print the new phone call data to stdout
                    if (newCall != null) {
                        System.out.println(Messages.displayPhoneCallAs(customer, newCall.toString()));
                    }
                }

            }

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
            System.err.println("README file not available. " + exception.getMessage());
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
        err.println("usage: java Project4 [options] <args>");
        err.println("  args are in this order:");
        err.println();
        err.println("  customer             Owner of the phone bill");
        err.println("  callerNumber         Owner's phone number");
        err.println("  callerNumber         Phone call recipient number");
        err.println("  begin                Call begin time MM/DD/YYYY HH:MM am/pm");
        err.println("  end                  Call end time MM/DD/YYYY HH:MM am/pm");
        err.println();
        err.println("   options are (any order): ");
        err.println("   -host hostname      Host computer on which the server runs");
        err.println("   -port portnumber    Port on which the server is listening");
        err.println("   -search             Phone calls should be searched for");
        err.println("   -print              Prints description of the new phone call");
        err.println("   -README             Print README and exits");
        err.println();
        err.println("Phone bill application keeps track of phone calls for various customers.");
        err.println("Phone bills are stored on a server for future use");
        err.println("Host and Port are mandatory options.");
        err.println("If -search and customer name provided, displays all calls for that customer");
        err.println("If -search, customer, and begin/end times provided, will display within the calls\n" +
                        "within the given parameters");
        err.println("If all arguments are provided a new call will be added to the customers bill, if\n" +
                "the bill does not exist a new one will be created");
        err.println("Add -print to the add phone call method above to display the newly added phone call");
        err.println();
    }
}