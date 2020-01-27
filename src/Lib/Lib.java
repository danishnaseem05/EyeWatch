package Lib;


import java.io.IOException;
import java.util.HashMap;

public class Lib {

    OperatingSystem os;
    SynologyAPI cloudDrive;

    HashMap<String, String> database;
    String pathToDatabase = "./EyeWatchDatabase.(extension name)";

    // Instance variables
    private String localDirPath;
    private String hostnameOrIP;
    private Integer portNumber;
    private String username;
    private String password;
    private String remoteDirPath;
    private boolean isRunOnStartup;


    public Lib(String hostname, Integer portNumber){
        os = new OperatingSystem();
        cloudDrive = new SynologyAPI(hostname, portNumber);
    }

    public Lib() throws IOException, InterruptedException {
        if(isDatabase()){
            // If database is present
            // Open the database for reading and writing
            openDatabase();
            this.database = readDatabase();
            // Don't show the GUI (unless the user clicks on settings from the system tray),
            // and starts running in the background (inside the system tray)
            this.os = new OperatingSystem();
            os.runInBackground();
            // Save each of this.database key's value inside an instance variable, whose name is same as the key's
            collectDatabaseVars();
            // Then close the database
            closeDatabase();
            // Then calls the respected methods, passing those variables as arguments
            // First method will be to start tracking the local dir
            os.watchLocalDirectoryState(localDirPath);

            // When the user clicks on settings on the program running in the system tray, the program initializes a GUI, already filled with
            // the information that was collected inside the variables

        } else{
            createNewDatabase();
            openDatabase();
            writeToNewDatabase();
        }
    }

    public boolean isDatabase(){
        // Looks in the current directory for a folder named .EyeWatch
        // If it exists, then it goes in and looks for UserSetting.csv,
        // It that file exists, then returns true, otherwise false

        return false;
    }


    public void createNewDatabase(){
        // create a new database in the program directory named EyeWatchUserSetting.(database extension)
    }


    public void openDatabase(){
        // opens the Database for reading and writing
    }


    public HashMap<String, String> readDatabase(){
        // collect all the key and value pairs probably in a HashMap
        // and return the HashMap
        HashMap<String, String> database = new HashMap<>();

        return database;
    }

    public void writeToDatabase(HashMap<String, String> toWrite){
        // writes to each key inside the database, matching the key of toWrite, with its respected value

    }


    public void writeToNewDatabase(){
        // this method writes variables as keys, and sets their values as empty strings
        HashMap<String, String> toWrite = new HashMap<>();
    }


    public void closeDatabase(){
        // close the database
    }


    public void collectDatabaseVars(){
        this.localDirPath = database.get("localDirPath");
        this.hostnameOrIP = database.get("hostnameOrIP");
        this.portNumber = Integer.parseInt(database.get("portNumber"));
        this.username = database.get("username");
        this.password = database.get("password");
        this.remoteDirPath = database.get("remoteDirPath");
        this.isRunOnStartup = Boolean.parseBoolean(database.get("isRunOnStartup"));
    }


    // this class initializes the GUI, and runs the main program functions.

    // User local directory selection

    // User remote directory path

    // Open Synology FileStation to pick a remote directory

    // Synology API class used to get

    // Load the program on startup

    // Keep track of any changes made inside the directory

    // Add to LinkedList

    // Program runs asynchronous in the background


}
