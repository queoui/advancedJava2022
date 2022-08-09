package edu.pdx.cs410j.nmuller;

import static java.lang.System.out;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;

/**
 * Class Pretty Printer prints a more human-readable output for a phone bill
 */
public class PrettyPrinter extends AppCompatActivity implements PhoneBillDumper<PhoneBill> {

    /**

     *        constructor for pretty printer
     */
    public PrettyPrinter(){}

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
        ((TextView) findViewById(R.id.textView6)).setText(bill.getCustomer());
        for (PhoneCall singleCall: bill.billOfCalls) {
            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());
            if(duration.toMinutes() / 60000 < 0){
                throw new IOException("Time travel has been detected. enter accurate time.");
            }
            String printCall = "Phone call duration of " + (duration.toMinutes() / 60000) +" minutes" +  " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                    + " beginning at "+ singleCall.getBeginTimeString()+ " and ending at "+ singleCall.getEndTimeString();
            ((TextView) findViewById(R.id.textView6)).setText(printCall);
        }
    }
}
