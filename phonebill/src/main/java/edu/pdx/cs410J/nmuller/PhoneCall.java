package edu.pdx.cs410J.nmuller;


import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;


/**
 * Phone call class extends AbstractPhoneCall
 * implements Comparable<phoneCall>
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

  /**
   * the one making the call
   */
  private String caller;
  /**
   * the one being called
   */
  private String callee;
  /**
   * Date and time the phone call began
   */
  private Date callBegin;
  /**
   * date and time the call ended
   */
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
   * @throws ErrorCheck.MissingCommandLineArguments
   *      MissingCommandLineArguments
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
   * @return call
   *        string version of phone call
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
   *
   * @return
   *      this call date begin time
   */
  @Override
  public Date getBeginTime() {
    return this.callBegin;
  }

  /**
   *
   * @return
   *      this call date end time
   */
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
      throw new ErrorCheck.MissingCommandLineArguments("usage: java -jar target/phonebill-2022.0.0.jar <options> <arguments>." +
                                                                                    "\nfor more details on options and arguments do" +
                                                                                      "\njava -jar target/phonebill-2022.0.0.jar -readme");
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

  /**
   *
   * @param o
   *      phone call to compare this phone call to
   * @return
   *      int, negative for this call first, 0 for same, positive for o call first
   */
  @Override
  public int compareTo(PhoneCall o) {
    if(this.callBegin.compareTo(o.callBegin) == 0){
      return this.caller.compareTo(this.callee);
    }
    else{
      return this.callBegin.compareTo(o.callBegin);
    }
  }
}


