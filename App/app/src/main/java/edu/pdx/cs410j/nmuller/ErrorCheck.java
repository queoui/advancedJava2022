package edu.pdx.cs410j.nmuller;



import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class for general command line error checking
 */
public class ErrorCheck {
    /**
     * Helper function to check if a given date is in the valid form MM/DD/YYYY
     *
     * @param date <code>date</code> for month/day/year
     *
     */

    public static void checkDate(String date) {
        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(date);
        boolean bool = matcher.matches();
        if(!bool)
            throw new IllegalArgumentException("use MM/DD/YYYY format for date");
    }

    /**
     * Helper function to check if a given time is in the valid form HH:MM
     *
     * @param time <code>time</code> for hours and minutes
     *
     */

    public static void checkTime(String time) {
        String regex = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(time);
        boolean bool = matcher.matches();
        if(!bool){
            throw new IllegalArgumentException("\"use HH:MM format for time\"");
        }
    }

    /**
     * Helper function to check if a given phone number is in the valid form NNN-NNN-NNNN
     *
     * @param number <code>number</code> for a phone number
     *
     */

    public static void checkPhoneNumber(String number) {
        String regex = "^(\\d{3}[-]?){2}\\d{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(number);
        boolean bool = matcher.matches();
        if(!bool){
            throw new IllegalArgumentException("use NNN-NNN-NNNN where N is 0-9 for phone numbers");
        }
    }


    /**
     *
     * @param beginTime
     *          begin of the phonecall in Date class default format
     * @param endTime
     *          end of the phonecall in Date class default format
     * @return
     *      boolean. true if no time travel issues.
     */
    public static boolean checkTimeTravel(Date beginTime, Date endTime){
        return beginTime.before(endTime);
    }

    /**
     * Exception class for missing command line arguments.
     */
    static class MissingCommandLineArguments extends Exception {

        /**
         *
         * @param missing_command_line_arguments
         *          exception handling
         */
        public MissingCommandLineArguments(String missing_command_line_arguments) {
            super(missing_command_line_arguments);
        }

    }

}