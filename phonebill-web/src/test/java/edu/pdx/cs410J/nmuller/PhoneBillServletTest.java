package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static edu.pdx.cs410J.nmuller.PhoneBillServlet.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class PhoneBillServletTest {


  @Test
  void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

  @Test
  void addOneBillToDictionary() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "customer";
    PhoneBill newBill = new PhoneBill(customer);
    try {
      PhoneCall newCall = PhoneCall.createNewCall("425-239-9870", "425-741-1269",
              "05/24/2022 11:50am", "05/24/2022 11:55am");
      newBill.addPhoneCall(newCall);

    } catch (ErrorCheck.MissingCommandLineArguments e) {
      e.printStackTrace();
    }
    Map<String, PhoneBill> newMapping = Map.of("customer", newBill);


    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(newBill.toString());

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(""));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    assertThat(servlet.getParameter("customer", request), equalTo(newBill.toString()));
  }
//
//  @Test
//  void checkForError() throws IOException{
//    PhoneBillServlet servlet = new PhoneBillServlet();
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter printWriter = new PrintWriter(stringWriter, true);
//
//    when(response.getWriter()).thenReturn(printWriter);
//    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn("nick");
//    when(request.getParameter(BEGIN_DATE_PARAMETER)).thenReturn("03/24/2022 11:00PM");
//    when(request.getParameter(END_DATE_PARAMETER)).thenReturn("03/25/2022 11:00PM");
//    when(request.getParameter(CALLER_PARAMETER)).thenReturn("425-239-8954");
//    when(request.getParameter(CALLEE_PARAMETER)).thenReturn("206-985-6547");
//
//
//
//    servlet.doPost(request, response);
//    try {
//      servlet.writePhoneCallParams("nick", "03/22/2022 11:00PM", "03/25/2022 11:00PM", response);
//    } catch (ErrorCheck.MissingCommandLineArguments e) {
//      e.printStackTrace();
//    }
//
//    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass((Integer.class));
//    ArgumentCaptor<String> message = ArgumentCaptor.forClass((String.class));
//
//    verify(response).setStatus();
//    assertEquals(statusCode.capture(), 0);
//
//  }
}
