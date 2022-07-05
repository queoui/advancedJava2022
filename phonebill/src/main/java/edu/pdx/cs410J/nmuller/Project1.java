package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {


  /**
   * Main function for the Program1 Project
   * @param args
   *        command line arguments
   */
  public static void main(String[] args) {

    boolean readme = true;
    try {
      readme = checkReadMe(args);
    }catch(IOException e){
      System.err.println(e.getMessage());
    }

    if(!readme) {
      //error check the command line arguments and create phone call
      try {
        PhoneCall validCall = createNewCall(args);

        //print phone call if needed
        if ("-print".equals(args[0])) {
          String callDetails = validCall.getPhoneCall();
          System.out.println(callDetails);
        }

//      create phone bill and phone call to bill
        try {
          if (args.length == 8) {
            String customer = args[1];

            PhoneBill newBill = new PhoneBill(customer);
            newBill.addPhoneCall(validCall);
            newBill.getPhoneCalls();
          }
        }catch(Exception e){
          System.err.println(e.getMessage());
        }


      } catch (MissingCommandLineArguments e) {
        System.err.println(e.getMessage());
      }


    }

  }

  /**
   * Method checks for valid command line arguments and creates a new Call if valid
   *
   * @param args
   *        command line arguments
   * @return <code>call</code>
   * @throws MissingCommandLineArguments
   *         error is thrown when command line arguments are not valid.
   */
  @VisibleForTesting
  static PhoneCall createNewCall(String [] args) throws MissingCommandLineArguments {
    if(args.length == 0) {
      throw new MissingCommandLineArguments("Missing command line arguments");
    }
    else if(args.length != 8){
      throw new MissingCommandLineArguments("Not enough or too many command line arguments. Use -readme for help");
    }
      else if(!checkDate(args[4]) || !checkDate(args[6]) ||
              !checkTime(args[5]) || !checkTime(args[7])) {
        throw new MissingCommandLineArguments("use MM/DD/YYYY format for date\nuse HH:MM format for time");
      }

      else if(!checkPhoneNumber(args[2]) || !checkPhoneNumber(args[3])) {
        throw new MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }

    PhoneCall call = new PhoneCall(args[2], args[3], args[4] + " " + args[5],
            args[6] + " " + args[7]);

  return call;
  }

  /**
   * Helper function to check if a given date is in the valid form MM/DD/YYYY
   *
   * @param date
   *        <code>date</code> for month/day/year
   * @return <code>bool</code>
   */
  @VisibleForTesting
  public static boolean checkDate(String date){
    String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(date);
    boolean bool = matcher.matches();
    return bool;
  }

  /**
   * Helper function to check if a given time is in the valid form HH:MM
   *
   * @param time
   *        <code>time</code> for hours and minutes
   * @return <code>>bool</code>
   */
  @VisibleForTesting
  public static boolean checkTime(String time) {
    String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(time);
    boolean bool = matcher.matches();
    return bool;
  }

  /**
   * Helper function to check if a given phone number is in the valid form NNN-NNN-NNNN
   *
   * @param number
   *        <code>number</code> for a phone number
   * @return <code>bool</code>
   */
  @VisibleForTesting
  public static boolean checkPhoneNumber(String number) {
    String regex = "^(\\d{3}[-]?){2}\\d{4}$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(number);
    boolean bool = matcher.matches();
    return bool;
  }

  /**
   *Method that prints to standard out the README.txt file saved in the src/resources folder
   *
   * @param args
   *        command line arguments as string
   * @return <code>bool</code>
   * @throws IOException
   *        makes sure it reads from the file successfully
   */
  @VisibleForTesting
  public static boolean checkReadMe(String [] args) throws IOException {
      for (String check : args){
        if(check.equalsIgnoreCase("-readme")) {

          String readMeFile = "src/main/resources/edu/pdx/cs410J/nmuller/README.txt";
          BufferedReader reader = new BufferedReader(new FileReader(readMeFile));
          String curr;
          while((curr = reader.readLine()) != null){
            System.out.println(curr);
          }
          reader.close();
          return true;
        }
      }
    return false;
  }

  /**
   * Exception class for missing command line arguments.
   */
  static class MissingCommandLineArguments extends Exception {
    public MissingCommandLineArguments(String missing_command_line_arguments) {
     super(missing_command_line_arguments);
    }

  }

}