package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;

import java.io.IOException;



/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 extends ErrorCheck {


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
      //check filepath/file existence

      //create new phone call
      try {
        PhoneCall validCall = createNewCall(args);

        //print phone call if needed
        if (args.length == 8 && "-print".equals(args[0])) {
          String callDetails = validCall.getPhoneCall();
          System.out.println(callDetails);
        }
        else if(args.length == 10){
          for(int check = 0; check != 2; ++check){
            if("-print".equals(args[check])) {
              String callDetails = validCall.getPhoneCall();
              System.out.println(callDetails);
            }
          }
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

    //check when args length is 8
    else if(args.length == 8) {

      if (!checkDate(args[4]) || !checkDate(args[6]) ||
              !checkTime(args[5]) || !checkTime(args[7])) {
        throw new MissingCommandLineArguments("use MM/DD/YYYY format for date\nuse HH:MM format for time");
      } else if (!checkPhoneNumber(args[2]) || !checkPhoneNumber(args[3])) {
        throw new MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }

      PhoneCall call = new PhoneCall(args[2], args[3], args[4] + " " + args[5],
              args[6] + " " + args[7]);
      return call;
    }

    //check when args length is 9
    else if(args.length == 9){
      if (!checkDate(args[5]) || !checkDate(args[7]) ||
              !checkTime(args[6]) || !checkTime(args[8])) {
        throw new MissingCommandLineArguments("use MM/DD/YYYY format for date\nuse HH:MM format for time");

      }else if (!checkPhoneNumber(args[3]) || !checkPhoneNumber(args[4])) {
        throw new MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }
        PhoneCall call = new PhoneCall(args[3], args[4], args[5] + " " + args[6],
                args[7] + " " + args[8]);
        return call;
      }

    //check when args length is 10
    else if(args.length == 10){
      if (!checkDate(args[6]) || !checkDate(args[8]) ||
              !checkTime(args[7]) || !checkTime(args[9])) {
        throw new MissingCommandLineArguments("use MM/DD/YYYY format for date\nuse HH:MM format for time");

      }else if (!checkPhoneNumber(args[4]) || !checkPhoneNumber(args[5])) {
        throw new MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }
      PhoneCall call = new PhoneCall(args[4], args[5], args[6] + " " + args[7],
              args[8] + " " + args[9]);
      return call;
    }

    throw new MissingCommandLineArguments("Too few or too many command line arguments, use the argument -readme for more information.");

  }



}