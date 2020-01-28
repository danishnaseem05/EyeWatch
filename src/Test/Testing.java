package Test;


import Lib.*;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO
class Testing {


    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        Testing testing = new Testing();

        //testing.csvManagerTest();
        //testing.http_ClientTest();
        //testing.synologyAPITest();
        //GUI gui = testing.GUITest();
        //testing.operatingSystemTest(gui);
        testing.libStaticMethodsTest();
    }


    void http_ClientTest() throws IOException, JSONException {
        Http_Client.getRequest("https://danishnaseem05.synology.me:5001/webapi/query.cgi?api=SYNO.API.Info&version=1&method=query&query=SYNO.API.Auth,SYNO.FileStation.List");
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
            os.watchLocalDirectoryState(absPath);
        }
    }


    void synologyAPITest() throws IOException, JSONException {
        SynologyAPI cloud = new SynologyAPI("danishnaseem05.synology.me", 5001);
        //System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite"));
        System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "984176"));
    }


    void csvManagerTest(){
        System.out.println("CREATE CSV FILE:");
        CsvManager csvManager = new CsvManager();
        csvManager.createCsv("./TEST/.EyeWatch", "test");

        System.out.println("\nWRITE TO test.csv:");
        csvManager.saveSetting("./TEST/.EyeWatch", "danishnaseem05.synology.me", "5001", "danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "794913", "false", "./TEST/.EyeWatch/test.csv");

        System.out.println("\nREAD test.csv");
        HashMap<String, String> database2 = csvManager.readCsv("./TEST/.EyeWatch/test.csv");
        for(Map.Entry<String, String> entry2: database2.entrySet()){
            System.out.println(entry2);
        }
    }


    GUI GUITest(){
        GUI gui = new GUI();
        return gui;
    }


    void libStaticMethodsTest(){
        boolean databaseBool = Lib.isDatabase();
        System.out.println("Is Database? " + databaseBool);

        Lib.createNewDatabase();
        Lib.writeToDatabase("./TEST/.EyeWatch", "danishnaseem05.synology.me", "5001", "danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "794913", "false");

        System.out.println();
        HashMap<String, String> database = Lib.readDatabase();
        System.out.println();
        for(Map.Entry<String, String> entry: database.entrySet()){
            System.out.println(entry);
        }
    }

}