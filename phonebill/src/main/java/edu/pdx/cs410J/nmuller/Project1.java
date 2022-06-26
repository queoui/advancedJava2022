package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return true;
  }

  public static void main(String[] args) {

    if(args.length == 0) {
      System.err.println("Missing command line arguments");
    }
    if(args.length != 8){
        System.out.println("Not enough or too many command line arguments. Provide in order\n" +
             "-print or -README\n" +
             "-\"customer name\"\n" +
             "-caller phone number\n" +
              "-callee phone number\n" +
             "-phone call start date and time\n"+
             "-phone call end date and time\n");
      System.exit(1);
    }
    for (String arg : args) {
      System.out.print(arg + "\t");
    }
    System.out.println("\n");

    // Refer to one of Dave's classes so that we can be sure it is on the classpath
    PhoneCall call = new PhoneCall(args[2], args[3], args[4]+ " " + args[5],
                                    args[6] + " " +args[7]);

    PhoneBill newBill = new PhoneBill(args[1]);


    System.out.format("%s\t%s\t%s\n%s\t%s", newBill.getCustomer(), call.getCaller(), call.getCallee(),
            call.getBeginTimeString(), call.getEndTimeString());

    System.exit(1);
  }

}