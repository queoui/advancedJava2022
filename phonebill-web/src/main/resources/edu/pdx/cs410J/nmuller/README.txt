****************
Nicholas Muller
Project 4
****************

***********************************************************************************************************************
                                               READ ME
***********************************************************************************************************************

##############
## OVERVIEW ##
##############
This is the README file for the phone bill application!
This application collects and stores phone calls into a web server. If a phone bill does not exist
it will create a new phone bill on the web server and store the given first phone call in the newly created phone bill.
If the customer already has a phone bill append a new phone call to their already existing one. The purpose is for all
phone calls on a given account to be visible in the customers phone bill.

##################
## COMMAND LINE ##
##################

This application takes a series of command line arguments to log phone calls for a customer provided.
The format for the command line arguments is as follows:
    java -jar target/phonebill-2022.0.0.jar [options] <args>
    *note that options come before the arguments.*

        ##########################
        ## COMMAND LINE OPTIONS ##
        ##########################

        The command line options are -print, -readme, and -textfile <filepath>
        OPTIONS
            -print: Create a new phone call for the customer provided and print the details of that phone call to the user.
            -readme: Displays the text you are now reading and nothing else.
            -search: searches a given customers bill for phone calls within a specified range
            -host  : host on for the local machine
            -port  : port for the local web server.

        ############################
        ## COMMAND LINE ARGUMENTS ##
        ############################

            /// ** args must be in the following order ** ///

            1. Customer: The name on the customers bill. Submitted within quotation marks as so "John Doe"
            2. Caller Number: The number of the person making the call, must be in the format NNN-NNN-NNNN
            3. Callee Number: The number of the person being called, must be in the format NNN-NNN-NNNN
            4. all Start Time: The time that the call began, provided in the following format MM/DD/YYYY HH:MM
            5. Call End Time: The time that the call ended, provided in the following format MM/DD/YYYY HH:MM



