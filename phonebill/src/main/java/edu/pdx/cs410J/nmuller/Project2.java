package edu.pdx.cs410J.nmuller;
import edu.pdx.cs410J.ParserException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


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
      //check filepath/file existence

      //create new phone call
      try {
        PhoneCall validCall = PhoneCall.createNewCall(args);

        //print phone call if needed
        for(int i = 0; i < 3; ++i){
          if ("-print".equalsIgnoreCase(args[i])){
            String callDetails = validCall.getPhoneCall();
            System.out.println(callDetails);
          }
        }

        //check for -textfile & file
        for(int i = 0; i < 3; ++i){
          if ("-textfile".equalsIgnoreCase(args[i])) {
            String givenPath = args[i + 1];
            ErrorCheck.checkValidPathFile(givenPath);
              try {
                Reader confirmedPath = new FileReader(givenPath);
                TextParser fileReader = new  TextParser(confirmedPath);
                try {
                  PhoneBill newBill = fileReader.parse();
                  if(!(args[(args.length) -7].equals(newBill.getCustomer()))){
                    throw new ParserException("given customer does not match the bill.");
                  }
                }catch(ParserException e){
                  System.err.println("error reading from file");
                }
              }catch(FileNotFoundException e){
                //create new file here;
                //file does not exist but path is valid.
                //create a new file in the file path.
                //create new phone bill with the one phone call
                //textDump to newly created file.
              }
            }
          }

//      create phone bill and phone call to bill
        try {
          if (args.length == 8) {
            String customer = args[1];

            PhoneBill newBill = new PhoneBill(customer);
            newBill.addPhoneCall(validCall);
            newBill.getPhoneCalls();
          }
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }


      } catch (ErrorCheck.MissingCommandLineArguments e) {
        System.err.println(e.getMessage());
      }


    }

  }
}

