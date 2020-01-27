package Lib;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;
import static javax.swing.JOptionPane.showConfirmDialog;

public class OperatingSystem {

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
            System.out.println("SystemTray is not supported");
            return;
        }
        Image image = Toolkit.getDefaultToolkit().getImage("./resources/icon/Eye Watch (System Tray Icon).png");

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image, "Eye Watch", popup);
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem settingsItem = new MenuItem("Settings");
        //TODO: settingsItem opens the program with already defined settings
        //settingsItem.addActionListener(e -> );
        MenuItem aboutItem = new MenuItem("About");
        //TODO: aboutItem opens a window displaying the program name, version number, and copyrights.
        //aboutItem.addActionListener(e -> );
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(1));
        popup.add(settingsItem);
        popup.add(aboutItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }


    public void watchLocalDirectoryState(String path) throws IOException, InterruptedException {
        Path dir = Paths.get(path);
        WatchService watcher = dir.getFileSystem().newWatchService();
        dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

        WatchKey watchKey;
        while(true) {
            watchKey = watcher.poll(10, TimeUnit.MINUTES);
            if(watchKey != null){
                //TODO: Instead of printing the event.context(), make the event call the program(which adds the filenames
                // to the LinkedList, and access the remote directory to look for those files). If the files exist,
                // delete those files from the local directory, otherwise just return and wait for another event call
                // from here.
                watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
            }
            watchKey.reset();
        }
    }


    public File chooseLocalDirectory(){
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
        }
        return folderPath;
    }


    //TODO: This main is only for testing, remove when the development is complete
    public static void main (String [] args) throws IOException, InterruptedException {
        OperatingSystem os = new OperatingSystem();
        //os.runOnStartup();
        os.runInBackground();
        String path = os.chooseLocalDirectory().getAbsolutePath();
        System.out.println(path);
        os.watchLocalDirectoryState(path);
    }

}
