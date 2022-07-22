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
    Map<String, String> dictionary = Map.of("One", "1", "Two", "2");

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(dictionary));

    PhoneBillRestClient client = new PhoneBillRestClient(http);

    assertThat(client.getAllDictionaryEntries(), equalTo(dictionary));
  }

  @Test
  void putNewPhoneCallGivenCustomerName() throws ParserException, IOException {
    Map<String, String> dictionary = Map.of("customer", "Nick", "caller", "425-239-9870", "callee", "425-741-1269",
                                              "beginTime", "05/24/2022 11:50am", "endTime","05/24/2022 11:50am" );

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(dictionary));

    PhoneBillRestClient client = new PhoneBillRestClient(http);

    assertThat(client.getAllDictionaryEntries(), equalTo(dictionary));
  }

  private HttpRequestHelper.Response dictionaryAsText(Map<String, String> dictionary) {
    StringWriter writer = new StringWriter();
    new TextDumper(writer).dump(dictionary);
    return new HttpRequestHelper.Response(writer.toString());
  }
}
