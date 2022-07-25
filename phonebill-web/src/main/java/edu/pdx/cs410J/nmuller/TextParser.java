package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  public Map<String, PhoneBill> parse() throws ParserException {

    Pattern pattern = Pattern.compile("(.*), (.*), (.*)");

    Map<String, PhoneBill> map = new HashMap<>();
    PhoneBill newBill = null;

    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      String customer = br.readLine();
      if (customer != null) {
        newBill = new PhoneBill(customer);
        for (String line = br.readLine(); line != null; line = br.readLine()) {
          Matcher matcher = pattern.matcher(line);
          if (!matcher.find()) {
            throw new ParserException("Unexpected text: " + line);
          }

          String word = matcher.group(1);
          String[] words = word.split(" ");
          String word2 = matcher.group(2);
          String[] words2 = word2.split(" ");
          String word3 = matcher.group(3);
          String[] words3 = word3.split(" ");


          String caller = words[3];
          String callee = words[5];
          String beginString = words[7] + " " + words2[0]  + words2[1];
          String endString = words2[3] + " " + words3[0]  + words3[1];

          //System.out.println(caller + " * " + callee + " * " + beginString + " * " + endString);

          DateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US) {
          };
          Date begin = new Date();
          Date end = new Date();
          try {
            begin = formatter.parse(beginString);
            end = formatter.parse(endString);
            PhoneCall addCall = new PhoneCall(caller, callee, begin, end);
            newBill.addPhoneCall(addCall);
          } catch (Exception e) {
            System.err.println("Error: Unable to read from given file");
          }


        }

        map.put(customer, newBill);
      }
      } catch(IOException e){
        throw new ParserException("While parsing dictionary", e);

    }
    return map;
  }



}
