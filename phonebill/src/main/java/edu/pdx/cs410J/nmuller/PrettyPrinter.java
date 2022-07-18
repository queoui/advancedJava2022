package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.time.Duration;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer prettyWriter;

    public PrettyPrinter(Writer writer) {
        this.prettyWriter = writer;
    }
    @Override
    public void dump(PhoneBill bill) throws IOException {
        PrintWriter out = new PrintWriter(prettyWriter);
        out.println((bill.getCustomer()));
        for (PhoneCall singleCall: bill.billOfCalls) {
            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());

            out.println("Phone call duration of " + duration.toString() + " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                        + " beginning at "+ singleCall.getBeginTimeString()+ " and ending at "+ singleCall.getEndTimeString());
        }

        out.close();
    }
}
