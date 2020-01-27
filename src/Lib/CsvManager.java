package Lib;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvManager {


    public void saveSetting(String localDirPath, String hostnameOrIP, String portNumber, String username, String password,
                            String remoteDirPath, String otp_code, String isRunOnStartup, String fullFilePath){

        try{
            FileWriter myFileWriter = new FileWriter(fullFilePath, false);
            BufferedWriter myBuffWriter = new BufferedWriter(myFileWriter);
            PrintWriter myPrintWriter = new PrintWriter(myBuffWriter);

            myPrintWriter.println("localDirPath," + localDirPath + "\n" +
                                "hostnameOrIP," + hostnameOrIP + "\n" +
                                "portNumber," + portNumber + "\n" +
                                "username," + username + "\n" +
                                "password," + password + "\n" +
                                "remoteDirPath," + remoteDirPath + "\n" +
                                "otp_code," + otp_code + "\n" +
                                "isRunOnStartup," + isRunOnStartup);

            myPrintWriter.flush();
            myPrintWriter.close();

            GUI.appendLog("Success: Settings Saved.");
            System.out.println("Success: Settings Saved.");
            JOptionPane.showMessageDialog(null, "Settings saved.");

        } catch(Exception e){
            GUI.appendLog("Success: Settings cannot be saved.");
            System.out.println("Success: Settings cannot be saved.");
            JOptionPane.showMessageDialog(null, "Error: Settings cannot be saved.");
        }
    }


    public HashMap<String, String> readCsv(String fullFilePath){
        HashMap<String, String> database = new HashMap<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get(fullFilePath));
            for(String line: lines){
                line = line.replace("\"", "");
                String[] result = line.split(",");
                database.put(result[0], result[1]);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return database;
    }


    public void createCsv(String filepath, String filenameNoExt){
        try{
            String full_path = filepath + File.separator + filenameNoExt + ".csv";
            GUI.appendLog("CSV Absolute file Path: " + full_path);
            System.out.println("CSV Absolute file Path: " + full_path);
            File myCSV = new File(full_path);
            if(myCSV.createNewFile()){
                GUI.appendLog("CSV File created: " + myCSV.getName());
                System.out.println("CSV File created: " + myCSV.getName());
            } else{
                GUI.appendLog("CSV File already exists.");
                System.out.println("CSV File already exists.");
            }
        } catch (IOException e){
            GUI.appendLog("Error: CSV file could not be created.");
            System.out.println("Error: CSV file could not be created.");
        }

    }


}
