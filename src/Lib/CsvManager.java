package Lib;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvManager {

    //TODO: add a private helper method to close the current .csv file, if open; before attempting to append data to it.


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
            return;
            //JOptionPane.showMessageDialog(null, "Settings saved.");

        } catch(Exception e){
            GUI.appendLog("Error: Settings cannot be saved. Check the filepath and Try Again.");
            System.out.println("Error: Settings cannot be saved. Check the filepath and Try Again.");
            return;
            //JOptionPane.showMessageDialog(null, "Error: Settings cannot be saved.");
        }
    }


    public HashMap<String, String> readCsv(String fullFilePath){
        HashMap<String, String> database = new HashMap<>();
        GUI.appendLog("Attempting to read from the database.");
        System.out.println("Attempting to read from the database.");
        try{
            List<String> lines = Files.readAllLines(Paths.get(fullFilePath));
            for(String line: lines){
                line = line.replace("\"", "");
                String[] result = line.split(",");
                database.put(result[0], result[1]);
            }
            GUI.appendLog("Successfully read from the database");
            System.out.println("Successfully read from the database");
        } catch(Exception e){
            System.out.println("Error: Could not read from the CSV file. Check the filepath and Try Again.");
        }
        return database;
    }


    public void createCsv(String filepath, String filenameNoExt){
        try{
            String full_path = filepath + File.separator + filenameNoExt + ".csv";
            //GUI.appendLog("CSV Absolute file Path: " + full_path);
            System.out.println("CSV Absolute file Path: " + full_path);
            File myCSV = new File(full_path);
            if(myCSV.createNewFile()){
                //GUI.appendLog("CSV File created: " + myCSV.getName());
                System.out.println("CSV File created: " + myCSV.getName());
                writeToNewCSVFile(full_path);
            } else{
                //GUI.appendLog("CSV File already exists.");
                System.out.println("CSV File already exists.");
            }
        } catch (IOException e){
            //GUI.appendLog("Error: CSV file could not be created.");
            System.out.println("Error: CSV file could not be created. Check the filepath and Try Again.");
        }

    }


    private void writeToNewCSVFile(String fullFilePath){
        try{
            FileWriter myFileWriter = new FileWriter(fullFilePath, false);
            BufferedWriter myBuffWriter = new BufferedWriter(myFileWriter);
            PrintWriter myPrintWriter = new PrintWriter(myBuffWriter);

            myPrintWriter.println("localDirPath," + "*" + "\n" +
                    "hostnameOrIP," + "*" + "\n" +
                    "portNumber," + "*" + "\n" +
                    "username," + "*" + "\n" +
                    "password," + "*" + "\n" +
                    "remoteDirPath," + "*" + "\n" +
                    "otp_code," + "*" + "\n" +
                    "isRunOnStartup," + "*");

            myPrintWriter.flush();
            myPrintWriter.close();

            //GUI.appendLog("Success: Settings Saved.");
            System.out.println("Success: Wrote to new CSV file.");
            //JOptionPane.showMessageDialog(null, "Settings saved.");

        } catch(Exception e){
            //GUI.appendLog("Error: Settings cannot be saved.");
            System.out.println("Error: Could not write to new CSV file. Check the filepath and Try Again.");
            //JOptionPane.showMessageDialog(null, "Error: Settings cannot be saved.");
        }
    }


}
