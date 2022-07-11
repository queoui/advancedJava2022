package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public PhoneBill parse() throws ParserException {

    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      String line;
      String customer = br.readLine();

      if (customer == null) {
        throw new ParserException("Missing customer");
      }
      if("".equals(customer))
        throw new ParserException("the file is empty");

      PhoneBill newBill = new PhoneBill(customer);

      while((line = br.readLine()) != null){
        String [] words = line.split(" ");
        if("".equals(line))
          continue;
        PhoneCall addCall = new PhoneCall(words[3], words[5], words[7] + " " + words[8]
                                            ,words[10] + " " + words[11]);
        newBill.addPhoneCall(addCall);
      }
      br.close();

      return newBill;

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }
}
