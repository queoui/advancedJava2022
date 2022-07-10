package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;


import java.io.*;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(PhoneBill bill) {

    PrintWriter out = new PrintWriter(writer);
    out.println((bill.getCustomer()));
    for (PhoneCall singleCall: bill.billOfCalls)
      out.println(singleCall);
    out.close();
  }

  public void dumpAppend(PhoneCall call) throws IOException{

    PrintWriter out = new PrintWriter(writer);
    out.println("\n" + call.getPhoneCall());

    out.close();

  }
}
