package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callee;
  private String callBegin;
  private String callEnd;

  public PhoneCall(String caller, String callee, String callBegin, String callEnd ) {
    this.caller = caller;
    this.callee = callee;
    this.callBegin = callBegin;
    this.callEnd = callEnd;
  }

  @Override
  public String getCaller() {return this.caller;}

  @Override
  public String getCallee(){return this.callee;}

  @Override
  public String getBeginTimeString() {
    return this.callBegin;
  }

  @Override
  public String getEndTimeString() {
    return this.callEnd;
  }
}
