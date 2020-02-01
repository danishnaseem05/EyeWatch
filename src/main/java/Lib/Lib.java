package Lib;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class Lib {

    SynologyAPI cloudDrive;

    private HashMap<String, String> database;
    private String pathToDatabase = "./.EyeWatch/UserSetting.csv";

    // Instance variables
    private String localDirPath;
    private String hostnameOrIP;
    private String portNumber;
    private String username;
    private String password;
    private  String remoteDirPath;
    private boolean isRunOnStartup;
    private String otp_code;

    private GUI gui;


    public Lib(){
        this.gui = new GUI(this);

        createNewDatabase();
        readDatabase();
        updateGUIFromDatabase();

        // Now we have the hostname and the port number by running the updateGUIFromDatabase, which calls
        // collectDatabaseVars method, which collects the initialized the variables collected from the database, which
        // were collected from the GUI
        cloudDrive = new SynologyAPI(hostnameOrIP, portNumber);
    }


    public static LinkedList collectLocalDirFilenames(String filePath){
        LinkedList<String> filenames = new LinkedList<>();

        File dir = new File(filePath);
        File[] listOfFiles = dir.listFiles();

        for(int i=0; i<listOfFiles.length; i++){
            if(listOfFiles[i].isFile()) {
                filenames.add(listOfFiles[i].getName());
            }
        }

        return filenames;
    }


    // takes in a list of local filenames to delete from the directory path
    public static void deleteFiles(String dirPath, LinkedList<String> filenamesToDelete){
        for(String filename: filenamesToDelete){
            String filepath = dirPath + File.separator + filename;
            File file = new File(filepath);
            file.delete();
        }
    }


    public boolean isDatabase(){
        if(new File("./.EyeWatch").exists()){
            if(new File("./.EyeWatch/UserSetting.csv").exists())
                return true;
        }
        return false;
    }


    public void createNewDatabase(){
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
        } else {
            GUI.appendLog("Database already exists");
            System.out.println("Database already exists");
        }
    }


    public HashMap<String, String> readDatabase(){
        CsvManager csvManager = new CsvManager();
        this.database = csvManager.readCsv(pathToDatabase);
        return this.database;
    }


    public void updateGUIFromDatabase(){
        collectDatabaseVars();
        gui.setLocalDirPath(localDirPath);
        gui.setHostname(hostnameOrIP);
        gui.setPortNumber(portNumber);
        gui.setUsername(username);
        gui.setPassword(password);
        gui.setRemoteDirPath(remoteDirPath);
        gui.setOtp_Code(otp_code);
        gui.setRunOnStartupCheckBox(isRunOnStartup);
    }


    public void writeToDatabase(String localDirPath, String hostnameOrIP, String portNumber, String username, String password,
                                 String remoteDirPath, String otp_code, String isRunOnStartup){
        CsvManager csvManager = new CsvManager();
        csvManager.saveSetting(localDirPath, hostnameOrIP, portNumber, username, password, remoteDirPath, otp_code, isRunOnStartup, pathToDatabase);
    }


    private void collectDatabaseVars(){
        localDirPath = database.get("localDirPath");
        hostnameOrIP = database.get("hostnameOrIP");
        portNumber = database.get("portNumber");
        username = database.get("username");
        password = database.get("password");
        remoteDirPath = database.get("remoteDirPath");
        otp_code = database.get("otp_code");
        isRunOnStartup = Boolean.parseBoolean(database.get("isRunOnStartup"));
    }


}
