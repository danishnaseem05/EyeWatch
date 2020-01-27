package Test;


import Lib.CsvReaderWriter;
import Lib.Http_Client;
import Lib.OperatingSystem;
import Lib.SynologyAPI;
import org.json.JSONException;

import java.io.IOException;

//TODO
class Testing {

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        Testing testing = new Testing();

        testing.csvReaderWriterTest();
        //testing.http_ClientTest();
        //testing.synologyAPITest();
        //testing.operatingSystemTest();
    }


    void http_ClientTest() throws IOException, JSONException {
        Http_Client.getRequest("https://danishnaseem05.synology.me:5001/webapi/query.cgi?api=SYNO.API.Info&version=1&method=query&query=SYNO.API.Auth,SYNO.FileStation.List");
    }


    void operatingSystemTest() throws IOException, InterruptedException {
        OperatingSystem os = new OperatingSystem();
        //os.runOnStartup();
        os.runInBackground();
        String path = os.chooseLocalDirectory().getAbsolutePath();
        System.out.println(path);
        os.watchLocalDirectoryState(path);
    }


    void synologyAPITest() throws IOException, JSONException {
        SynologyAPI cloud = new SynologyAPI("danishnaseem05.synology.me", 5001);
        //System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite"));
        System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "794913"));
    }


    void csvReaderWriterTest(){
        CsvReaderWriter csvReaderWriter = new CsvReaderWriter();

    }


}