//package edu.pdx.cs410J.nmuller;
//import edu.pdx.cs410J.ParserException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class PrettyPrinterTest {
//    @Test
//    void prettyPrinterPrintTest(@TempDir File tempDir) throws IOException, ParserException {
//        PhoneBill newbill = new PhoneBill("nick");
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
//        Date begin = new Date();
//        Date end = new Date();
//        File textFile = new File(tempDir, "apptbook.txt");
//        PrettyPrinter prettyCaller = new PrettyPrinter(new FileWriter(textFile));
//        try {
//            begin = formatter.parse("05/24/2022 12:50pm");
//            end = formatter.parse("05/24/2022 01:00pm");
//        }catch(Exception e){System.err.println("test date parser error");}
//
//        PhoneCall testCall = new PhoneCall("425-555-5555", "206-555-5555",
//                begin, end);
//        newbill.addPhoneCall(testCall);
//        prettyCaller.dump(newbill);
//
//        TextParser parser = new TextParser(new FileReader(textFile));
//        String line = parser.parse();
//
//
//        assertThat(read.toString(), containsString("425-555-5555"));
//    }
//}
