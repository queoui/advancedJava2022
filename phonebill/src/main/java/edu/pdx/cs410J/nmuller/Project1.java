package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    else if(args.length == 1 || "-README".equals(args[1])){
//
//    }

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
    if(!checkDate(args[4]) || !checkDate(args[6])){
      System.out.println("use MM/DD/YYYY format for date");
      System.exit(1);
    }

    if(!checkTime(args[5]) || !checkTime(args[7])){
      System.out.println("use HH:MM format for time");
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

    if("-path".equals(args[1])){
      System.out.format("%s\t%s\t%s\n%s\t%s", newBill.getCustomer(), call.getCaller(), call.getCallee(),
              call.getBeginTimeString(), call.getEndTimeString());
    }
    System.exit(1);
  }

  public static boolean checkDate(String date){
    String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(date);
    boolean bool = matcher.matches();
    if(bool)
      return true;
    else
      return false;
  }

  public static boolean checkTime(String time) {
    String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(time);
    boolean bool = matcher.matches();
    if (bool)
      return true;
    else
      return false;
  }
}