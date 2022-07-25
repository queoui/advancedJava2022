package edu.pdx.cs410J.nmuller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(Map<String, PhoneBill> dictionary) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){

     for (Map.Entry<String, PhoneBill> entry : dictionary.entrySet()) {
         if (entry.getKey() != null) {
             //ArrayList<PhoneCall> thisBill = entry.getValue().billOfCalls;
             pw.println(entry.getKey());
             for (PhoneCall call : entry.getValue().billOfCalls)
                 pw.println(call.getPhoneCall());



         }
     }
      pw.flush();
    }

  }
}
