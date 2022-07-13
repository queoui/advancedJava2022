package edu.pdx.cs410J.nmuller;

import com.google.common.annotations.VisibleForTesting;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ErrorCheck {
    /**
     * Helper function to check if a given date is in the valid form MM/DD/YYYY
     *
     * @param date <code>date</code> for month/day/year
     * @return <code>bool</code>
     */
    @VisibleForTesting
    public static boolean checkDate(String date) {
        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(date);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * Helper function to check if a given time is in the valid form HH:MM
     *
     * @param time <code>time</code> for hours and minutes
     * @return <code>>bool</code>
     */
    @VisibleForTesting
    public static boolean checkTime(String time) {
        String regex = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(time);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * Helper function to check if a given phone number is in the valid form NNN-NNN-NNNN
     *
     * @param number <code>number</code> for a phone number
     * @return <code>bool</code>
     */
    @VisibleForTesting
    public static boolean checkPhoneNumber(String number) {
        String regex = "^(\\d{3}[-]?){2}\\d{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(number);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * Method that prints to standard out the README.txt file saved in the src/resources folder
     *
     * @param args command line arguments as string
     * @return <code>bool</code>
     * @throws IOException makes sure it reads from the file successfully
     */
    @VisibleForTesting
    public static boolean checkReadMe(String[] args) throws IOException {
        boolean check = false;
        if ((args.length == 1) && (args[0].equalsIgnoreCase("-readme")))
            check = true;
        else {
            for (int i = 0; i < ((args.length) - 7); ++i) {
                if (args[i].equalsIgnoreCase("-readme"))
                    check = true;
            }
        }
        if (check) {
            try (InputStream readme = Project2.class.getResourceAsStream("README.txt")) {
                BufferedReader reader = new BufferedReader((new InputStreamReader(readme)));
                String curr;
                while ((curr = reader.readLine()) != null) {
                    System.out.println(curr);
                }
                reader.close();
            } catch (IOException exception) {
                throw new IOException("README file not available. " + exception.getMessage());
            }


        }
        return check;
    }

    /**
     * checks if passed file path is valid
     * @param path
     *         String directory path
     *
     * @throws InvalidPathException
     *          not a valid path
     * @throws NullPointerException
     *          referencing a null pointer
     */
    @VisibleForTesting
    public static void checkValidPathFile(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            System.err.println("Not a valid file path");
        }
    }

    /**
     * checks for options that are not available in the command line
     * @param args
     *      command line arguments
     * @throws MissingCommandLineArguments
     *          command line issues, unknown exceptions
     */
    @VisibleForTesting
    public static void checkUnknownOption(String[] args) throws MissingCommandLineArguments {
        boolean check = true;
        if ((args.length < 7)) {
            throw new MissingCommandLineArguments("missing customer and phone call information ");
        }
        if (!checkTime(args[args.length - 1])) {
            throw new MissingCommandLineArguments("extraneous argument on the command line");
        } else {
            for (int i = 0; i < ((args.length) - 7); ++i) {
                if (!args[i].equalsIgnoreCase("-print") && !args[i].equalsIgnoreCase("-textfile")) {
                    if (i != 0) {
                        if ((args[i - 1].equalsIgnoreCase("-textfile")))
                            continue;
                    }
                    throw new MissingCommandLineArguments("unknown option used");
                }
            }
        }
    }

    /**
     * Exception class for missing command line arguments.
     */
    static class MissingCommandLineArguments extends Exception {
        public MissingCommandLineArguments(String missing_command_line_arguments) {
            super(missing_command_line_arguments);
        }

    }

}