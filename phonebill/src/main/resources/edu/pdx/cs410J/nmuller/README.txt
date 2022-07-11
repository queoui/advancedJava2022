Nicholas Muller
Project 2

This is the README file for the phonebill application!

This application takes a series of command line arguments to log phone calls for a customer provided.
The format for the command line arguments is as follows:
    java -jar target/phonebill-2022.0.0.jar [options] <args>
    *note that options come before the arguments.*

The command line options are -print and -readme.

/// OPTIONS///
    -print: Create a new phone call for the customer provided and print the details of that phone call to the user.
    -readme: Displays the text you are now reading and nothing else.
    -textfile <file>: appends a phone number to an existing bill or creates a new bill with the given phone call
                        <file> is a valid file path

///ARGS///
*args must be in the following order*

    Customer: The name on the customers bill. Submitted within quotation marks as so "John Doe"
    Caller Number: The number of the person making the call, must be in the format NNN-NNN-NNNN
    Callee Number: The number of the person being called, must be in the format NNN-NNN-NNNN
    Call Start Time: The time that the call began, provided in the following format MM/DD/YYYY HH:MM
    Call End Time: The time that the call ended, provided in the following format MM/DD/YYYY HH:MM

If done correctly the program will either display a valid phone calls details or this message.


