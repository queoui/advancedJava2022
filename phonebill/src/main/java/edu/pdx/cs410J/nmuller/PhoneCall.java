package edu.pdx.cs410J.nmuller;


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



}
