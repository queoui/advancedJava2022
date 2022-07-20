package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;

/**
 * Class Pretty Printer prints a more human-readable output for a phone bill
 */
public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer prettyWriter;

    public PrettyPrinter(Writer writer) {
        this.prettyWriter = writer;
    }

    /**
     * Override the PhoneBillDumper dump method, prints to standard out or a text file
     *
     * @param bill
     *          phone bill to dump in a pretty print fashiion
     * @throws IOException
     *          file i/o exceptions from writing to a file
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        PrintWriter out = new PrintWriter(prettyWriter);
        out.println((bill.getCustomer()));
        for (PhoneCall singleCall: bill.billOfCalls) {
            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());
            if(duration.toMinutes() / 60000 < 0){
                throw new IOException("Time travel has been detected. enter accurate time.");
            }

            out.println("Phone call duration of " + (duration.toMinutes() / 60000) +" minutes" +  " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                        + " beginning at "+ singleCall.getBeginTimeString()+ " and ending at "+ singleCall.getEndTimeString());
        }

        out.close();
    }
}
