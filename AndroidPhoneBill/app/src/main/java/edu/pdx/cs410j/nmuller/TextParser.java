package edu.pdx.cs410j.nmuller;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Public class for parsing a phone bill text file
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  /**
   * constructor for textParser class
   * @param reader
   *        constructor reader
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   *
   * @return <code>PhoneBill</code>
   *          returns a parsed phone bill
   * @throws ParserException
   *        Parser Error with malformed text file
   */
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
        throw new ParserException("bill has no customer name");

      PhoneBill newBill = new PhoneBill(customer);

      while((line = br.readLine()) != null){
        String [] words = line.split(" ");
        if("".equals(line))
          continue;

        DateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US) {
        };
        Date begin = new Date();
        Date end = new Date();
        try {
          begin = formatter.parse(words[7] + " " + words[8]  + words[9]);

          end = formatter.parse(words[11] + " " + words[12] +  words[13]);
          PhoneCall addCall = new PhoneCall(words[3], words[5], begin, end);
          newBill.addPhoneCall(addCall);
        }catch(Exception e){System.err.println("Error: Unable to read from given file");}


      }
      br.close();

      return newBill;

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }
}
