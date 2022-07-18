package edu.pdx.cs410J.nmuller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

  /**
   * tests getBeginTime method
   */
  @Test
  void getBeginTimeStringWorks() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();

    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}

    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin, end);
    assertThat(testCall.getBeginTimeString(), containsString("Tue May 24 12:50:00 PDT 2022"));
  }

  /**
   * tests getEndTime method
   */
  @Test
  void getEndTimeStringWorks() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();
    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}

    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin,end);
    assertThat(testCall.getEndTimeString(), containsString("Tue May 24 13:00:00 PDT 2022"));
  }

  /**
   * tests getCaller method
   */
  @Test
  void getCallerWorks() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();
    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}

    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin, end);
    assertThat(testCall.getCaller(), containsString("425-555-5555"));
  }


  /**
   * tests getCallee method
   */
  @Test
  void getCalleeWorks() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
    Date begin = new Date();
    Date end = new Date();
    try {
      begin = formatter.parse("05/24/2022 12:50pm");
      end = formatter.parse("05/24/2022 01:00pm");
    }catch(Exception e){System.err.println("test date parser error");}

    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            begin, end);
    assertThat(testCall.getCallee(), containsString("206-555-5555"));
  }

}