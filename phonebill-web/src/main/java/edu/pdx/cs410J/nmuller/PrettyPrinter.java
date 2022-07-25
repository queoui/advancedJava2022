package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class PrettyPrinter {
  private final Writer writer;

//  @VisibleForTesting
//  static String formatWordCount(int count )
//  {
//    return String.format( "Dictionary on server contains %d words", count );
//  }

//  @VisibleForTesting
//  static String formatDictionaryEntry(String word, String definition )
//  {
//    return String.format("  %s : %s", word, definition);
//  }

  @VisibleForTesting
  static String formatPhoneCallEntry(String customer, String phoneCall )
  {
    return String.format("  %s : %s", customer, phoneCall);
  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(Map<String, PhoneBill> dictionary) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {

      //pw.println(formatWordCount(dictionary.size()));

      for (Map.Entry<String, PhoneBill> entry : dictionary.entrySet()) {
        String customer = entry.getKey();
        String phoneCall = entry.getValue().toString();
        pw.println(formatPhoneCallEntry(customer, phoneCall));
      }

      pw.flush();
    }

  }
}
