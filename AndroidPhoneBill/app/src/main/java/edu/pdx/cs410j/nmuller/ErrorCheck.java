package edu.pdx.cs410j.nmuller;



import java.io.IOException;
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

    public static void checkDate(String date) throws IOException {
        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(date);
        boolean bool = matcher.matches();
        if(!bool)
            throw new IOException("Invalid Date");
    }

    /**
     * Helper function to check if a given time is in the valid form HH:MM
     *
     * @param time <code>time</code> for hours and minutes
     *
     */

    public static void checkTime(String time) throws IOException {
        String regex = "(1[012]|[1-9]):[0-5][0-9]";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(time);
        boolean bool = matcher.matches();
        if(!bool){
            throw new IOException("Invalid Time");
        }
    }

    /**
     * Helper function to check if a given phone number is in the valid form NNN-NNN-NNNN
     *
     * @param number <code>number</code> for a phone number
     *
     */

    public static void checkPhoneNumber(String number) throws IOException {
        String regex = "^(\\d{3}[-]?){2}\\d{4}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(number);
        boolean bool = matcher.matches();
        if(!bool){
            throw new IOException("Invalid Phone Number");
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

}