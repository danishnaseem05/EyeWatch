package Lib;


import java.awt.image.ImagingOpException;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CsvManager {


    public void writeCsv(LinkedList<String> keys, LinkedList<String> values, String filePath){


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


    public void createCsv(String filepath, String filenameNoExt){
        try{
            String full_path = filepath + File.separator + filenameNoExt + ".csv";
            System.out.println("Full Path: " + full_path);
            File myCSV = new File(full_path);
            if(myCSV.createNewFile()){
                System.out.println("File created: " + myCSV.getName());
            } else{
                System.out.println("File already exists.");
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
