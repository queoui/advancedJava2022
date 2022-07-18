package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.PhoneBillDumper;


import java.io.*;

/**
 * TextDumper class dumps phone bills to text files
 * dump() dumps it all
 * dumpAppend() appends a valid call to an existing text file
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * dumps all contents of a bill to a text file
   * @param bill
   *        Phone bill to dump to file
   * */
  @Override
  public void dump(PhoneBill bill) {

    PrintWriter out = new PrintWriter(writer);
    out.println((bill.getCustomer()));
    for (PhoneCall singleCall: bill.billOfCalls) {
      out.println("Phone call from " + singleCall.getCaller() + " to " + singleCall.getCallee()
              + " from " + singleCall.getBeginTime().toString() + " to " + singleCall.getEndTime().toString());
    }
    out.close();
  }

  /**
   * takes a phone call ans appends it to an existing phone bill
   * @param call
   *        call to add to bill
   *
   *
   * @throws IOException
   *        may throw IO error if writer not available
   */
  public void dumpAppend(PhoneCall call) throws IOException{

    PrintWriter out = new PrintWriter(writer);
    out.println(call.getPhoneCall());

    out.close();

  }
}
