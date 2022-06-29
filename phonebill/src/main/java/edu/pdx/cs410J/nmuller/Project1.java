package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return true;
  }

  public static void main(String[] args) {

    //error check the command line arguments
    cmdLineCheck(args);

    // Refer to one of Dave's classes so that we can be sure it is on the classpath
    PhoneCall call = new PhoneCall(args[2], args[3], args[4]+ " " + args[5],
                                    args[6] + " " +args[7]);

    PhoneBill newBill = new PhoneBill(args[1]);

//    for(String cmd :args){
//      System.out.println(cmd +"\t");
//    }

    if("-print".equals(args[0])){
      System.out.format("%s\t%s\t%s\n%s\t%s", newBill.getCustomer(), call.getCaller(), call.getCallee(),
              call.getBeginTimeString(), call.getEndTimeString());
    }
    System.exit(1);
  }


  public static void cmdLineCheck(String [] args){
    if(args.length == 0) {
      err.println("Missing command line arguments");
      System.exit(1);
    }

    else if(args.length == 1 || "-README".equals(args[1])){
      System.out.println("This is the phone bill application");
      System.exit(1);
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
    try{
      if(!checkDate(args[4]) || !checkDate(args[6]) ||
              !checkTime(args[5]) || !checkTime(args[7])) {
        throw new IllegalArgumentException();
      }
    }catch(IllegalArgumentException e){
      err.println("use MM/DD/YYYY format for date\nuse HH:MM format for time");
      System.exit(1);
    }
    try{
      if(!checkPhoneNumber(args[2]) || !checkPhoneNumber(args[3])) {
        throw new IllegalArgumentException();
      }
    }catch(IllegalArgumentException e){
      err.println("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      System.exit(1);
    }

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

  public static boolean checkPhoneNumber(String number) {
    String regex = "^(\\d{3}[-]?){2}\\d{4}$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(number);
    boolean bool = matcher.matches();
    if (bool)
      return true;
    else
      return false;
  }

}