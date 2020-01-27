package Lib;


import javax.swing.*;
import java.awt.image.ImagingOpException;
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

            JOptionPane.showMessageDialog(null, "Record saved");

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Record not saved");
        }
    }


    public HashMap<String, String> readCsv(String fullFilePath){
        HashMap<String, String> database = new HashMap<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get(fullFilePath));
            for(String line: lines){
                line = line.replace("\"", "");
                String[] result = line.split(",");
                System.out.println("RESULT: " + Arrays.toString(result));
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
