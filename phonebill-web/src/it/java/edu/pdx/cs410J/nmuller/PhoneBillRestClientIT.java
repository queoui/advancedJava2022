package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@TestMethodOrder(MethodName.class)
class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllDictionaryEntries() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    Map<String, PhoneBill> dictionary = client.getPhoneBillEntries();
    assertThat(dictionary.size(), equalTo(0));
  }

  @Test
  void test2RecievePhoneBill() throws IOException, ParserException {



    PhoneBillRestClient client = newPhoneBillRestClient();
    String customer = "customer";
    String caller = "425-741-1269";
    String callee = "425-239-9870";
    String beginTime = "05/24/2022 11:50am";
    String endTime = "05/24/2022 12:00pm";
    PhoneCall newCall = null;
    PhoneBill newBill = new PhoneBill(customer);
    try {
      newCall = PhoneCall.createNewCall(caller, callee, beginTime, endTime);

      newBill.addPhoneCall(newCall);
    } catch (ErrorCheck.MissingCommandLineArguments e) {
      e.printStackTrace();
    }

    client.addPhoneCallEntry(customer, caller, callee, beginTime, endTime);
    String response =  client.getCustomerBill(customer).toString();

    assertThat(response, equalTo(client.getCustomerBill(customer).toString()));
  }

  @Test
  void test4EmptyWordThrowsException() {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String emptyString = "";

    RestException ex =
      assertThrows(RestException.class, () -> client.getCustomerBill(emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter("customer")));
  }

}
