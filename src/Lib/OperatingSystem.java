package Lib;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;

import static Lib.GUI.appendLog;
import static java.nio.file.StandardWatchEventKinds.*;
import static javax.swing.JOptionPane.showConfirmDialog;

public class OperatingSystem {

    private GUI gui;

    public OperatingSystem(GUI gui){
        this.gui = gui;
    }


    public void runOnStartup(){
        String user = getUser();
        String pathToStartup = "C:\\Users\\" + user +
                "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        //TODO: add this java program to pathToStartup

    }


    private String getUser(){
        String command = "cmd /C echo %username%";
        String output = "";
        try{
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader((
                    new InputStreamReader(process.getInputStream())));
            String line;
            while ((line = reader.readLine()) != null){
                output += line;
            }
            reader.close();
        } catch (Exception e) {
            output = "Error: call to cmd to get the Windows User Failed";
            e.printStackTrace();
        }
        return output;
    }


    // Call this method after user clicks on Done
    public void runInBackground(){
        if (!SystemTray.isSupported()) {
            appendLog("Error: System Tray not supported. Program cannot run in the background");
            System.out.println("Error: System Tray not supported. Program cannot run in the background");
            return;
        }
        Image image = Toolkit.getDefaultToolkit().getImage("./resources/icon/Eye Watch (System Tray Icon).png");

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image, "Eye Watch", popup);
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem settingsItem = new MenuItem("Settings");
        settingsItem.addActionListener(e -> gui.setVisible(true));
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(e -> gui.aboutWindow());
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(1));
        popup.add(settingsItem);
        popup.add(aboutItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            appendLog("AWTException: TrayIcon could not be added");
            System.out.println("TrayIcon could not be added.");
        }
    }


    public static class WatchLocalDirectoryStateThread extends Thread{
        GUI gui;
        String localDirPath;

        //running OperatingSystem class's watchLocalDirectoryState method in a new Thread
        public WatchLocalDirectoryStateThread(GUI gui, String localDirPath){
            this.gui = gui;
            this.localDirPath = localDirPath;
        }
        public void run(){
            try {
                watchLocalDirectoryState(localDirPath);
                appendLog("Started monitoring: " + localDirPath);
                System.out.println("Started monitoring: " + localDirPath);
            } catch (IOException ex) {
                appendLog("System Error: Invalid local directory path entered.");
                System.out.println("System Error: Invalid local directory path entered.");
                JOptionPane.showMessageDialog(gui, "Invalid local directory path entered.", "System Error", JOptionPane.ERROR_MESSAGE);
                //ex.printStackTrace();
            }
        }


        public void watchLocalDirectoryState(String path) throws IOException{
            Path dir = Paths.get(path);
            WatchService watcher = dir.getFileSystem().newWatchService();
            WatchKey watchKey = dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

            while(true) {
                watchKey.pollEvents().forEach(event -> {

                    String e = dir.resolve((Path) event.context()).toString();
                    WatchEvent.Kind<?> k = event.kind();

                    if(k.toString().equals("ENTRY_CREATE")){
                        appendLog(e + " has been CREATED");
                        System.out.println(e + " has been CREATED");
                    }
                    else if(k.toString().equals("ENTRY_MODIFY")) {
                        appendLog(e + " has been MODIFIED");
                        System.out.println(e + " has been MODIFIED");
                    }


                    //TODO: make the event call a method from Lib.java class (which adds the filenames
                    // from the local directory to a LinkedList, and accesses the remote directory
                    // to look for those files). If the files exist, delete those files from the local directory,
                    // otherwise just return. That method from Lib.java will be in a loop (with condition: until the local directory is empty)
                    // and keep calling itself (the same Lib.java method) after sleep(10 minutes).

                });
            }
        }

    }


    public static File chooseLocalDirectory(){
        File folderPath = new File("");

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Eye Watch");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            folderPath = chooser.getSelectedFile();
        }
        else {
            int option = showConfirmDialog(chooser, "Are you sure you want to cancel?");
            if(option == 1 || option == 2) return chooseLocalDirectory();
            else folderPath = null;
        }
        return folderPath;
    }

}
