package Lib;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import Lib.OperatingSystem.*;

public class Lib {

    OperatingSystem os;
    SynologyAPI cloudDrive;

    HashMap<String, String> database;
    static String pathToDatabase = "./.EyeWatch/UserSetting.csv";

    // Instance variables
    private String localDirPath;
    private String hostnameOrIP;
    private Integer portNumber;
    private String username;
    private String password;
    private String remoteDirPath;
    private boolean isRunOnStartup;
    private String otp_code;

    private GUI gui;


    public Lib(String hostname, Integer portNumber){
        this.gui = new GUI(pathToDatabase);
        os = new OperatingSystem(gui);
        cloudDrive = new SynologyAPI(hostname, portNumber);
    }

    public Lib() throws IOException, InterruptedException {
        this.gui = new GUI("");

        if(isDatabase()){
            // If database is present
            // Open the database for reading and writing
            //openDatabase();
            this.database = readDatabase();
            // Don't show the GUI (unless the user clicks on settings from the system tray),
            // and starts running in the background (inside the system tray)
            this.os = new OperatingSystem(gui);
            os.runInBackground();
            // Save each of this.database key's value inside an instance variable, whose name is same as the key's
            collectDatabaseVars();
            // Then close the database
            //closeDatabase();
            // Then calls the respected methods, passing those variables as arguments
            // First method will be to start tracking the local dir
            WatchLocalDirectoryStateThread watchLocalDirectoryStateThread = new WatchLocalDirectoryStateThread(gui, localDirPath);
            watchLocalDirectoryStateThread.start();
            // When the user clicks on settings on the program running in the system tray, the program initializes a GUI, already filled with
            // the information that was collected inside the variables

        } else{
            createNewDatabase();
            //openDatabase();
            //writeToNewDatabase();
        }
    }

    public static boolean isDatabase(){
        if(new File("./.EyeWatch").exists()){
            if(new File("./.EyeWatch/UserSetting.csv").exists())
                return true;
        }
        return false;
    }


    public static void createNewDatabase(){
        CsvManager csvManager = new CsvManager();
        if(! isDatabase()){
            File dir = new File(".EyeWatch");
            System.out.println(dir.getAbsolutePath());
            if(dir.exists()) csvManager.createCsv(dir.getAbsolutePath(), "UserSetting");
            else{
                dir.mkdir();
                // Hide the created directory
                String command= "cmd /C attrib +s +h " + "\"" + dir.getAbsolutePath() + "\"";
                try{
                    Runtime.getRuntime().exec(command);
                    System.out.println("SUCCESS: Database Directory successfully hidden");
                    csvManager.createCsv(dir.getAbsolutePath(), "UserSetting");
                } catch (IOException e) {
                    System.out.println("ERROR: Database directory faced an error while trying to hide it.");
                }
            }
        } else System.out.println("ERROR: Database already been created");
    }


    public static HashMap<String, String> readDatabase(){
        CsvManager csvManager = new CsvManager();
        return csvManager.readCsv(pathToDatabase);
    }

    public static void writeToDatabase(String localDirPath, String hostnameOrIP, String portNumber, String username, String password,
                                 String remoteDirPath, String otp_code, String isRunOnStartup){
        CsvManager csvManager = new CsvManager();
        csvManager.saveSetting(localDirPath, hostnameOrIP, portNumber, username, password, remoteDirPath, otp_code, isRunOnStartup, pathToDatabase);
    }


    private void collectDatabaseVars(){
        this.localDirPath = database.get("localDirPath");
        this.hostnameOrIP = database.get("hostnameOrIP");
        this.portNumber = Integer.parseInt(database.get("portNumber"));
        this.username = database.get("username");
        this.password = database.get("password");
        this.remoteDirPath = database.get("remoteDirPath");
        this.otp_code = database.get("otp_code");
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
