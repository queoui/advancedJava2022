package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperParserTest {

  @Test
  void emptyMapCanBeDumpedAndParsed() throws ParserException {
    Map<String, PhoneBill> map = Collections.emptyMap();
    Map<String, PhoneBill> read = dumpAndParse(map);
    assertThat(read, equalTo(map));
  }

  private Map<String, PhoneBill> dumpAndParse(Map<String, PhoneBill> map) throws ParserException {
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(map);

    String text = sw.toString();

    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  @Test
  void dumpedTextCanBeParsed() throws ParserException {
    PhoneBill newBill = new PhoneBill("customer");
    try {
      PhoneCall newCall = PhoneCall.createNewCall("425-239-9870", "425-741-1269",
              "05/24/2022 11:50am", "05/24/2022 11:55am");
      newBill.addPhoneCall(newCall);
    } catch (ErrorCheck.MissingCommandLineArguments e) {
      e.printStackTrace();
    }

    Map<String, PhoneBill> map = Map.of("customer", newBill);
    Map<String, PhoneBill> read = dumpAndParse(map);

    assertThat(read.toString(), equalTo(map.toString()));
  }
}
