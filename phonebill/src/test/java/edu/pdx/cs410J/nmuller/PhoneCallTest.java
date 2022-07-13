package edu.pdx.cs410J.nmuller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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
    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            "05/24/2022" + " " + "12:50", "05/24/2022" + " " + "1:00");
    assertThat(testCall.getBeginTimeString(), containsString("05/24/2022 12:50"));
  }

  /**
   * tests getEndTime method
   */
  @Test
  void getEndTimeStringWorks() {
    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            "05/24/2022" + " " + "12:50", "05/24/2022" + " " + "1:00");
    assertThat(testCall.getEndTimeString(), containsString("05/24/2022 1:00"));
  }

  /**
   * tests getCaller method
   */
  @Test
  void getCallerWorks() {
    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            "05/24/2022" + " " + "12:50", "05/24/2022" + " " + "1:00");
    assertThat(testCall.getCaller(), containsString("425-555-5555"));
  }


  /**
   * tests getCallee method
   */
  @Test
  void getCalleeWorks() {
    PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
            "05/24/2022" + " " + "12:50", "05/24/2022" + " " + "1:00");
    assertThat(testCall.getCallee(), containsString("206-555-5555"));
  }

}