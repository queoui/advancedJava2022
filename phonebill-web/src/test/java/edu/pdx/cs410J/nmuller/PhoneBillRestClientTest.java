package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {

  @Test
  void getAllDictionaryEntriesPerformsHttpGetWithNoParameters() throws ParserException, IOException {
    PhoneBill newBill = new PhoneBill("customer");
    try {
      PhoneCall newCall = PhoneCall.createNewCall("425-239-9870", "425-741-1269",
              "05/24/2022 11:50am", "05/24/2022 11:55am");
      newBill.addPhoneCall(newCall);
    } catch (ErrorCheck.MissingCommandLineArguments e) {
      e.printStackTrace();
      Map<String, PhoneBill> dictionary = Map.of("customer", newBill);

      HttpRequestHelper http = mock(HttpRequestHelper.class);
      when(http.get(eq(Map.of()))).thenReturn((HttpRequestHelper.Response) dictionary);

      PhoneBillRestClient client = new PhoneBillRestClient(http);

      assertThat(client.getCustomerBill("customer"), equalTo(dictionary));
    }
  }

  @Test
  void putNewPhoneCallGivenCustomerName() throws ParserException, IOException {
    PhoneBill newBill = new PhoneBill("customer");
    try {
      PhoneCall newCall = PhoneCall.createNewCall("425-239-9870", "425-741-1269",
              "05/24/2022 11:50am", "05/24/2022 11:55am");
      newBill.addPhoneCall(newCall);
    } catch (ErrorCheck.MissingCommandLineArguments e) {
      e.printStackTrace();
      Map<String, PhoneBill> dictionary = Map.of("customer", newBill);

      HttpRequestHelper http = mock(HttpRequestHelper.class);
      when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(dictionary));

      PhoneBillRestClient client = new PhoneBillRestClient(http);

      assertThat(client.getCustomerBill("customer"), equalTo(dictionary));
    }
  }

    private HttpRequestHelper.Response dictionaryAsText (Map < String, PhoneBill > dictionary){
      StringWriter writer = new StringWriter();
      new TextDumper(writer).dump(dictionary);
      return new HttpRequestHelper.Response(writer.toString());
    }
  }

