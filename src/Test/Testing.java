package Test;


import Lib.*;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO
class Testing {

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        Testing testing = new Testing();

        testing.GUITest();
        testing.csvManagerTest();
        testing.http_ClientTest();
        testing.synologyAPITest();
        testing.operatingSystemTest();
    }


    void http_ClientTest() throws IOException, JSONException {
        Http_Client.getRequest("https://danishnaseem05.synology.me:5001/webapi/query.cgi?api=SYNO.API.Info&version=1&method=query&query=SYNO.API.Auth,SYNO.FileStation.List");
    }


    void operatingSystemTest() throws IOException, InterruptedException {
        OperatingSystem os = new OperatingSystem();
        //os.runOnStartup();
        os.runInBackground();
        String path = os.chooseLocalDirectory().getAbsolutePath();
        System.out.println("Current Directory Absolute Path: " + path);
        os.watchLocalDirectoryState(path);
    }


    void synologyAPITest() throws IOException, JSONException {
        SynologyAPI cloud = new SynologyAPI("danishnaseem05.synology.me", 5001);
        //System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite"));
        System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "984176"));
    }


    void csvManagerTest(){
        System.out.println("CREATE CSV FILE:");
        CsvManager csvManager = new CsvManager();
        csvManager.createCsv("./TEST", "test");

        System.out.println("\nREAD TEST.CSV:");
        HashMap<String, String> database = csvManager.readCsv("./TEST/test.csv");
        for(Map.Entry<String, String> entry: database.entrySet()){
            System.out.println(entry);
        }

        System.out.println("\nWRITE TO writeTest.csv:");
        csvManager.saveSetting(database.get("localDirPath"), database.get("hostnameOrIP"), database.get("portNumber"), database.get("username"),
                database.get("password"), database.get("remoteDirPath"), database.get("otp_code"), database.get("isRunOnStartup"), "./TEST/writeTest.csv");

        System.out.println("\nREAD writeTest.csv");
        HashMap<String, String> database2 = csvManager.readCsv("./TEST/writeTest.csv");
        for(Map.Entry<String, String> entry2: database2.entrySet()){
            System.out.println(entry2);
        }
    }


    void GUITest(){
        GUI gui = new GUI();
    }


}