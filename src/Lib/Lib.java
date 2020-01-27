package Lib;



public class Lib {

    OperatingSystem os;
    SynologyAPI cloudDrive;

    public Lib(String hostname, Integer portNumber){
        os = new OperatingSystem();
        cloudDrive = new SynologyAPI(hostname, portNumber);
    }
    


    // this class initializes the GUI, and runs the main program functions.

    // User local directory selection

    // User remote directory selection

    // Open Synology FileStation to pick a remote directory

    // Synology API class used to get

    // Load the program on startup

    // Keep track of any changes made inside the directory

    // Add to LinkedList

    // Program runs asynchronous in the background


}
