package edu.pdx.cs410J.nmuller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;
import java.util.Map;

public class PrettyPrinter {
  private final Writer writer;


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  /**
   * Override the PhoneBillDumper dump method, prints to standard out or a text file
   *
   * @param bill
   *          phone bill to dump in a pretty print fashiion
   * @throws IOException
   *          file i/o exceptions from writing to a file
   */

  public void dump(Map<String, PhoneBill> bill) throws IOException {
    Duration duration = null;
    PrintWriter pw = new PrintWriter(this.writer);
    for (Map.Entry<String, PhoneBill> entry : bill.entrySet()) {
      if(entry.getKey() != null) {
        pw.println(entry.getKey());
        for (PhoneCall call : entry.getValue().billOfCalls){
          duration = Duration.ofMinutes(call.getEndTime().getTime() - call.getBeginTime().getTime());
           if(duration.toMinutes() / 60000 < 0)
                throw new IOException("Time travel has been detected. enter accurate time.");
          pw.println("Phone call duration of " + (duration.toMinutes() / 60000) +" minutes" +  " between " + call.getCaller() + " and " + call.getCallee()
                  + " beginning at "+ call.getBeginTimeString()+ " and ending at "+ call.getEndTimeString());

      }
    }
    }

    pw.flush();
  }
}
