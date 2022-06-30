package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {


  public static void main(String[] args) {

    //error check the command line arguments and create phone call
    try {
      PhoneCall validCall = createNewCall(args);
      if ("-print".equals(args[0])) {
        validCall.getPhoneCall();
      }
    }catch(MissingCommandLineArguments e) {
      System.err.println(e.getMessage());
    }

//    PhoneBill newBill = new PhoneBill(args[1]);

  }

  @VisibleForTesting
  static PhoneCall createNewCall(String [] args) throws MissingCommandLineArguments {
    if(args.length == 0) {
      throw new MissingCommandLineArguments("Missing command line arguments");
    }

    if(args.length != 8){
      throw new MissingCommandLineArguments("Not enough or too many command line arguments. Provide in order\n" +
              "-print or -README\n" +
              "-\"customer name\"\n" +
              "-caller phone number\n" +
              "-callee phone number\n" +
              "-phone call start date and time\n"+
              "-phone call end date and time\n");

    }
      if(!checkDate(args[4]) || !checkDate(args[6]) ||
              !checkTime(args[5]) || !checkTime(args[7])) {
        throw new MissingCommandLineArguments("use MM/DD/YYYY format for date\nuse HH:MM format for time");
      }

      if(!checkPhoneNumber(args[2]) || !checkPhoneNumber(args[3])) {
        throw new MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }

    PhoneCall call = new PhoneCall(args[2], args[3], args[4] + " " + args[5],
            args[6] + " " + args[7]);

  return call;
  }

  @VisibleForTesting
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

  @VisibleForTesting
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

  @VisibleForTesting
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

  static class MissingCommandLineArguments extends Exception {
    public MissingCommandLineArguments(String missing_command_line_arguments) {
      super(missing_command_line_arguments);
    }

  }

}