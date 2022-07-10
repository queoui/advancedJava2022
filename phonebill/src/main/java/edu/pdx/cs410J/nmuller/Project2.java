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

        //-textfile file
        for(int i = 0; i < 3; ++i){
          if ("-textfile".equalsIgnoreCase(args[i])) {

            //check for a valid path
            String givenPath = args[i + 1];
            ErrorCheck.checkValidPathFile(givenPath);

            //file already exists
              try {
                Reader confirmedPath = new FileReader(givenPath);
                TextParser fileReader = new  TextParser(confirmedPath);
                try {
                  PhoneBill newBill = fileReader.parse();
                  if(!(args[(args.length) -7].equalsIgnoreCase(newBill.getCustomer()))){
                    throw new ParserException("given customer does not match the bill.");
                  }
                  newBill.addPhoneCall(validCall);
                  try {
                    Writer tempWriter = new FileWriter(givenPath, true);
                    TextDumper newDump = new TextDumper(tempWriter);
                    newDump.dumpAppend(validCall, givenPath);
                  }catch(IOException error1){
                    System.err.println("Something went wrong writing to file");
                  }
                }catch(ParserException e){
                  System.err.println("error reading from file: " + e.getMessage());
                }


                //file does not already exist
              }catch(FileNotFoundException e){
                PhoneBill newBill = new PhoneBill(args[args.length - 7]);
                newBill.addPhoneCall(validCall);
                try {
                  Writer tempWriter = new FileWriter(givenPath);
                  TextDumper newDump = new TextDumper(tempWriter);
                  newDump.dump(newBill);
                }catch(IOException error1){
                  System.err.println("Something went wrong creating new text file");
                }
              }
            }
          }

        //NEEDS TO BE REFACTORED OR NOT USED AT ALL
//      create phone bill and phone call to bill
//        try {
//          if (args.length == 8) {
//            String customer = args[1];
//
//            PhoneBill newBill = new PhoneBill(customer);
//            newBill.addPhoneCall(validCall);
//            newBill.getPhoneCalls();
//          }
//        } catch (Exception e) {
//          System.err.println(e.getMessage());
//        }


      } catch (ErrorCheck.MissingCommandLineArguments e) {
        System.err.println(e.getMessage());
      }


    }

  }
}

