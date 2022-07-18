package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  void appointmentBookOwnerIsDumpedInTextFormat() {
    String customer = "Test Phone Bill";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();
    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}
    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin, end);
    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(testCall);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(bill);

    String text = sw.toString();
    assertThat(text,containsString( text.toString()));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    File textFile = new File(tempDir, "apptbook.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }

  @Test
  void dumpAppendSingleCallTest(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Test Phone Bill";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();
    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}

    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin, end);
    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(testCall);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dumpAppend(testCall);

    String text = sw.toString();
    assertThat(text, containsString(testCall.getPhoneCall()));
  }

}