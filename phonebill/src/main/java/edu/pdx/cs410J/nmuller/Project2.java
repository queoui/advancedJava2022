package edu.pdx.cs410J.nmuller;
import edu.pdx.cs410J.ParserException;

import java.io.*;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {


  /**
   * Main function for the Program1 Project
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    boolean readme = true;
    try {
      readme = ErrorCheck.checkReadMe(args);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }


    if (!readme) {
      //check for unknown -options
      try{ErrorCheck.checkUnknownOption(args);}catch(Exception e){System.err.println(e.getMessage());}

      //create new phone call
      try {
        PhoneCall validCall = PhoneCall.createNewCall(args);

        //print phone call if needed
        for (int i = 0; i < (args.length) - 9; ++i) {
          if ("-print".equalsIgnoreCase(args[i])) {
            if (i != 0 && "-textfile".equalsIgnoreCase(args[i - 1])) {
              break;
            }
            String callDetails = validCall.getPhoneCall();
            System.out.println(callDetails);
          }
        }
        //-textfile file
        for (int i = 0; i < (args.length) - 9; ++i) {
          if ("-textfile".equalsIgnoreCase(args[i])) {

              //check for a valid path
              String givenPath = args[i + 1];

              //check for valid path
              ErrorCheck.checkValidPathFile(givenPath);


            //file exists
            try {
              Reader confirmedPath = new FileReader(givenPath);
              TextParser fileReader = new TextParser(confirmedPath);
              try {
                PhoneBill newBill = fileReader.parse();
                if (!(args[(args.length) - 9].equalsIgnoreCase(newBill.getCustomer()))) {
                  throw new ParserException("given customer does not match the bill.");
                }
                newBill.addPhoneCall(validCall);
                //append a phone call to the bill
                try {
                  Writer tempWriter = new FileWriter(givenPath, true);
                  TextDumper newDump = new TextDumper(tempWriter);
                  newDump.dumpAppend(validCall);
                } catch (IOException error1) {
                  System.err.println("Something went wrong writing to file");
                }
              } catch (ParserException e) {
                System.err.println("error reading from file: " + e.getMessage());
              }

              //else
              //file does not exist
            } catch (FileNotFoundException e) {
              PhoneBill newBill = new PhoneBill(args[args.length - 9]);
              newBill.addPhoneCall(validCall);
              try {
                Writer tempWriter = new FileWriter(givenPath);
                TextDumper newDump = new TextDumper(tempWriter);
                newDump.dump(newBill);
              } catch (IOException error1) {
                System.err.println("Something went wrong creating new text file" + error1.getMessage());
              }
            }
            break;
          }
        }


      } catch (ErrorCheck.MissingCommandLineArguments e) {
        System.err.println(e.getMessage());
      }


    }

  }

}

