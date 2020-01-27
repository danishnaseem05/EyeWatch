package Lib;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class CsvReaderWriter {


    public void writeCsv(String filePath){
        HashMap<String, String> userSetting = new HashMap<>();

        userSetting.put("localDirPath", "");
        userSetting.put("hostnameOrIP", "");
        userSetting.put("portNumber", "");
        userSetting.put("username", "");
        userSetting.put("password", "");
        userSetting.put("remoteDirPath", "");
        userSetting.put("isRunOnStartup", "");

        FileWriter fileWriter = null;

        try{
            fileWriter = new FileWriter(filePath);
            for(Map.Entry<String, String> entry: userSetting.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();

                fileWriter.append(key);
                fileWriter.append(value);
                fileWriter.append("\n");

            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    public void readCsv(String filePath){
        BufferedReader reader = null;

        try{

            String line = "";
            reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();

            while((line = reader.readLine()) != null){
                //TODO: do something
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void createCsv(String filepath){

    }


}
