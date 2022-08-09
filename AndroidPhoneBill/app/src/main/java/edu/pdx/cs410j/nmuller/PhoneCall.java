package edu.pdx.cs410j.nmuller;



import android.widget.Toast;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * Phone call class extends AbstractPhoneCall
 * implements Comparable PhoneCalls
 */
public class PhoneCall extends AbstractPhoneCall {

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
   */
  public PhoneCall(String caller, String callee, Date callBegin, Date callEnd ) {

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
   * @param caller
   * @param callee
   * @param callBegin
   * @param callEnd
   *        command line arguments
   * @return <code>call</code>

   *         error is thrown when command line arguments are not valid.
   */
  static PhoneCall createNewCall(String caller, String callee, String callBegin, String callEnd) throws IOException{

      SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US);
      Date begin = new Date();
      Date end = new Date();
      try {
         begin = formatter.parse(callBegin);
         end = formatter.parse(callEnd);
      }catch(Exception errParse){
        System.err.println("Unknown Date Format " + errParse);
      }

      if(!ErrorCheck.checkTimeTravel(Objects.requireNonNull(begin), end))
        throw new IOException("Time travel has been detected, please input accurate date and time");

    return new PhoneCall(caller, callee, begin,end);
    }


  /**
   *
   * @param o
   *      phone call to compare this phone call to
   * @return
   *      int, negative for this call first, 0 for same, positive for o call first
   */

  public int compareTo(Date o) {
    if(this.callBegin.compareTo(o) == 0){
      return this.caller.compareTo(this.callee);
    }
    else{
      return this.callBegin.compareTo(o);
    }
  }
}


