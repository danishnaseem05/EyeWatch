package Test;


import Lib.*;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Lib.OperatingSystem.*;


class Testing {


    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        Testing testing = new Testing();


        //testing.http_ClientTest();
        //testing.synologyAPITest();
        //testing.csvManagerTest();
        //Lib lib = testing.libTest();
        //GUI gui = testing.guiTest(lib);
        //testing.operatingSystemTest(gui);

    }


    void http_ClientTest() throws IOException, JSONException {
        Http_Client.getRequest("https://testHost123.synology.me:5001/webapi/query.cgi?api=SYNO.API.Info&version=1&method=query&query=SYNO.API.Auth,SYNO.FileStation.List");
    }


    void operatingSystemTest(GUI gui) throws IOException, InterruptedException {
        OperatingSystem os = new OperatingSystem(gui);
        //os.runOnStartup();
        os.runInBackground();
        File path = os.chooseLocalDirectory();
        if(path == null) {
            System.out.println("No local directory selected");
            return;
        }
        else{
            String absPath = path.getAbsolutePath();
            System.out.println("Current Directory Absolute Path: " + absPath);
            WatchLocalDirectoryStateThread watchLocalDirectoryStateThread = new WatchLocalDirectoryStateThread(gui, absPath);
            watchLocalDirectoryStateThread.start();
        }
    }


    void synologyAPITest() throws IOException, JSONException {
        SynologyAPI cloud = new SynologyAPI("testHost123.synology.me", "5001");
        //System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite"));
        System.out.println(cloud.run("testHost123", "testing124", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "123456"));
    }


    void csvManagerTest(){
        System.out.println("CREATE CSV FILE:");
        CsvManager csvManager = new CsvManager();
        csvManager.createCsv("./.EyeWatch", "test");

        System.out.println("\nWRITE TO test.csv:");
        csvManager.saveSetting("./TEST/.EyeWatch", "testHost123.synology.me", "5001", "testHost123", "testing124", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "123456", "false", ".EyeWatch/test.csv");

        System.out.println("\nREAD test.csv");
        HashMap<String, String> database2 = csvManager.readCsv("./.EyeWatch/test.csv");
        for(Map.Entry<String, String> entry2: database2.entrySet()){
            System.out.println(entry2);
        }
    }


    GUI guiTest(Lib lib){
        GUI gui = new GUI(lib);
        return gui;
    }


    Lib libTest(){
        Lib lib = new Lib();
        boolean databaseBool = lib.isDatabase();
        System.out.println("Is Database? " + databaseBool);

        lib.createNewDatabase();
        lib.writeToDatabase("./TEST/.EyeWatch", "testHost123.synology.me", "5001", "testHost123", "testing124", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "794913", "false");

        System.out.println();
        HashMap<String, String> database = lib.readDatabase();
        System.out.println();
        for(Map.Entry<String, String> entry: database.entrySet()){
            System.out.println(entry);
        }
        lib.updateGUIFromDatabase();

        return lib;
    }

}