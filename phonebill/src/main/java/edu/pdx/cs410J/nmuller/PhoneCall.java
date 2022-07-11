package edu.pdx.cs410J.nmuller;


import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;


/**
 * this class represents a <code>PhoneCall</code>
 */
public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callee;
  private String callBegin;
  private String callEnd;

  /**
   *
   * @param caller
   *        The phone number of the caller
   * @param callee
   *        The phone number of the person being called
   * @param callBegin
   *        date and time of the call start MM/DD/YYYY HH:MM
   * @param callEnd
   *        date and time of the call start MM/DD/YYYY HH:MM
   */
  public PhoneCall(String caller, String callee, String callBegin, String callEnd ) {
    this.caller = caller;
    this.callee = callee;
    this.callBegin = callBegin;
    this.callEnd = callEnd;
  }

  /**
   * Returns <code>String</code> with details of the call in
   *    plain english.
   */
  public String getPhoneCall(){
    String call = toString();
    return call;
  }

  /**
   * returns <code>String</code> of the callers phone number
   */
  @Override
  public String getCaller() {return this.caller;}

  /**
   * returns <code>String</code> of the callee phone number
   */
  @Override
  public String getCallee(){return this.callee;}

  /**
   * returns <code>String</code> of call start time: Date and Time
   */
  @Override
  public String getBeginTimeString() {
    return this.callBegin;
  }

  /**
   * returns <code>String</code> of call end time: Date and Time
   */
  @Override
  public String getEndTimeString() {
    return this.callEnd;
  }

  /**
   * Method checks for valid command line arguments and creates a new Call if valid
   *
   * @param args
   *        command line arguments
   * @return <code>call</code>
   * @throws ErrorCheck.MissingCommandLineArguments
   *         error is thrown when command line arguments are not valid.
   */
  @VisibleForTesting
  static PhoneCall createNewCall(String [] args) throws ErrorCheck.MissingCommandLineArguments {
    int len = args.length;

    if(len == 0){
      throw new ErrorCheck.MissingCommandLineArguments("command line usage is <option><args>. run the program with the argument -README for more information.");
    }

    if(len < 7) {
      throw new ErrorCheck.MissingCommandLineArguments("Missing command line arguments.");
    }
    else if(len > 10){
      throw new ErrorCheck.MissingCommandLineArguments("Too many command line arguments.");
    }

    else {
      if (!ErrorCheck.checkDate(args[len - 4]) || !ErrorCheck.checkDate(args[len - 2])) {
        throw new ErrorCheck.MissingCommandLineArguments("use MM/DD/YYYY format for date");
      }
      if(!ErrorCheck.checkTime(args[len - 3]) || !ErrorCheck.checkTime(args[len - 1])) {
        throw new ErrorCheck.MissingCommandLineArguments("use HH:MM format for time");
      }
      if (!ErrorCheck.checkPhoneNumber(args[len - 6]) || !ErrorCheck.checkPhoneNumber(args[len - 5])) {
        throw new ErrorCheck.MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }
//      if(!ErrorCheck.checkNoTimeTravel(args[len-4], args[len-3], args[len-2], args[len-1])){
//        throw new ErrorCheck.MissingCommandLineArguments("phone call arguments imply time travel exists");
//      }

      PhoneCall newCall = new PhoneCall(args[len - 6], args[len - 5], args[len - 4] + " " + args[len - 3],
              args[len - 2] + " " + args[len - 1]);
      return newCall;
    }

  }

}


