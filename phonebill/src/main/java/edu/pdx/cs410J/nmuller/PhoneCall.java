package edu.pdx.cs410J.nmuller;


import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

import static java.text.DateFormat.SHORT;


/**
 * this class represents a <code>PhoneCall</code>
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
  private String caller;
  private String callee;
  private Date callBegin;
  private Date callEnd;

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
  public PhoneCall(String caller, String callee, Date callBegin, Date callEnd )throws ErrorCheck.MissingCommandLineArguments {

      if(!ErrorCheck.checkPhoneNumber(caller) || !ErrorCheck.checkPhoneNumber(callee))
        throw new ErrorCheck.MissingCommandLineArguments("Not a valid phone number being parsed");

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



  //DOCUMENTATION FOR THESE NEW METHODS *********************************
  @Override
  public Date getBeginTime() {
    return this.callBegin;
  }

  @Override
  public Date getEndTime() {
    return this.callEnd;
  }



  /**
   * returns <code>String</code> of call start time: Date and Time
   */
  @Override
  public String getBeginTimeString() {
    //return getBeginTime().toString();
    DateFormat newFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    return newFormat.format(this.callBegin);
  }

  /**
   * returns <code>String</code> of call end time: Date and Time
   */
  @Override
  public String getEndTimeString() {
    //return getEndTime().toString();
    DateFormat newFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    return newFormat.format(this.callEnd);
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

    if(len < 9) {
      throw new ErrorCheck.MissingCommandLineArguments("Missing command line arguments.");
    }
    else if(len > 14){
      throw new ErrorCheck.MissingCommandLineArguments("Too many command line arguments.");
    }

    else {
      if (!ErrorCheck.checkDate(args[len - 6]) || !ErrorCheck.checkDate(args[len - 3])) {
        throw new ErrorCheck.MissingCommandLineArguments("use MM/DD/YYYY format for date");
      }
      if(!ErrorCheck.checkTime(args[len - 5] + args[len-4]) || !ErrorCheck.checkTime(args[len - 2] + args[len-1])) {
        throw new ErrorCheck.MissingCommandLineArguments("use HH:MM format for time");
      }
      if (!ErrorCheck.checkPhoneNumber(args[len - 8]) || !ErrorCheck.checkPhoneNumber(args[len - 7])) {
        throw new ErrorCheck.MissingCommandLineArguments("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
      }

      SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US);
      Date begin = new Date();
      Date end = new Date();
      try {
         begin = formatter.parse(args[len - 6] + " " + args[len - 5] + args[len - 4]);
         end = formatter.parse(args[len - 3] + " " + args[len - 2] + args[len-1]);
      }catch(Exception errParse){
        System.err.println("Unknown Date Format " + errParse);
      }
      if(!ErrorCheck.checkTimeTravel(begin, end))
        throw new ErrorCheck.MissingCommandLineArguments("Time travel has been detected, please input accurate date and time");

      PhoneCall newCall = new PhoneCall(args[len - 8], args[len - 7], begin,end);
      return newCall;
    }

  }

  @Override
  public int compareTo(PhoneCall o) {
    return this.callBegin.compareTo(o.callBegin);
  }
}


