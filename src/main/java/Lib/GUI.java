package Lib;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import Lib.OperatingSystem.*;

public class GUI extends JFrame {

    //TODO: add fileJMenu and add save log as its menu item. Save log would
    // enable the user to save the entire text within the log text area.


    // GUI Components
    protected static JTextArea log = new JTextArea();

    private JMenu editJMenu = new JMenu("Edit");
    private JMenu helpJMenu = new JMenu("Help");

    private JMenuItem aboutJMenuItem = new JMenuItem("About");

    private JMenuBar jMenuBar    = new JMenuBar();
    private JPopupMenu popupMenu = new JPopupMenu();

    private JTextArea descriptionTextArea  = new JTextArea("Scans the Synology remote directory for files that exist in the local directory. If they exist" +
            " in the Synology remote directory, then they will be deleted from the local directory; otherwise the local directory will be monitored for any" +
            " new or modified files, if so then another scan will run on the Synology remote directory. If 2 STEP VERIFICATION is turned on for your Synology" +
            " remote server, then it is REQUIRED.");
    private JLabel emptySpaceLabel   = new JLabel(" ");
    private JLabel localDirLabel     = new JLabel("<html><span style='font-size:11px'> Local Directory </span></html>");
    private JLabel remoteDirLabel    = new JLabel("<html><span style='font-size:11px'> Remote Directory </span></html>");
    private JLabel hostOrIPLabel     = new JLabel("Hostname or IP address");
    private JLabel HTTPSPortNumLabel = new JLabel("HTTPS Port Number");
    private JLabel usernameLabel     = new JLabel("Username");
    private JLabel passwordLabel     = new JLabel("Password");
    private JLabel otpCodeLabel      = new JLabel("2 Step Verification Code (optional)");

    private JTextField localDirTextField     = new JTextField(40);
    private JTextField remoteDirTextField    = new JTextField(47);
    private JTextField hostOrIPTextField     = new JTextField(12);
    private JTextField HTTPSPortNumTextField = new JTextField(7);
    private JTextField usernameTextField     = new JTextField(12);
    private JTextField otpCodeTextField      = new JTextField(7);

    private JPasswordField passwordField = new JPasswordField(12);

    private JButton localDirBrowseButton = new JButton("Browse");
    private JButton saveSettingsButton = new JButton("Save Settings");

    private JCheckBox runOnStartupCheckBox = new JCheckBox("Run on Startup");

    private JPanel flow2Panel   = new JPanel (new FlowLayout (FlowLayout.CENTER));
    private JPanel flow3Panel   = new JPanel (new FlowLayout (FlowLayout.CENTER));
    private JPanel flow4Panel   = new JPanel (new FlowLayout (FlowLayout.LEFT));
    private JPanel flow5Panel   = new JPanel (new FlowLayout (FlowLayout.CENTER));
    private JPanel flow6Panel   = new JPanel (new FlowLayout (FlowLayout.CENTER));
    private JPanel flow7Panel   = new JPanel (new FlowLayout (FlowLayout.LEFT));
    private JPanel flow8Panel   = new JPanel (new FlowLayout (FlowLayout.LEFT));
    private JPanel flow9Panel   = new JPanel (new FlowLayout (FlowLayout.RIGHT));
    private JPanel flow10Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
    private JPanel flow11Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
    private JPanel flowOtpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    private JPanel gridPanel  = new JPanel(new GridLayout(11,6));

    private Lib lib;


    public GUI(Lib lib){
        // Set the title of this JFrame
        super("Eye Watch");

        this.lib = lib;

        addActionForEditJMenu(new DefaultEditorKit.CutAction(), KeyEvent.VK_X,  "Cut");
        addActionForEditJMenu(new DefaultEditorKit.CutAction(), KeyEvent.VK_C,  "Copy");
        addActionForEditJMenu(new DefaultEditorKit.CutAction(), KeyEvent.VK_V,  "Paste");

        setPopup(log, localDirTextField, remoteDirTextField, hostOrIPTextField, HTTPSPortNumTextField, usernameTextField, passwordField);

        helpJMenu.add(aboutJMenuItem);

        Color jFrameColor = this.getBackground();
        //System.out.println(jFrameColor);
        Border border = BorderFactory.createLineBorder(new Color(130, 130, 130));

        // Setting Log JTextArea components
        settingLogComponents(border);

        // Setting ToolTipText
        settingToolTipText();

        // Setting descriptionTextArea Components
        settingDescriptionTextAreaComponents(border);

        // managing Flow Panels - adding label text fields to them
        managingFlowPanels();

        // managing Grid Panel(s) - adding flow panels to it(them)
        managingGridPanels();

        jMenuBar.add(editJMenu);
        jMenuBar.add(helpJMenu);

        setJMenuBar(jMenuBar);

        add(descriptionTextArea, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(new JScrollPane(log), BorderLayout.SOUTH);

        setSize(555, 800);
        setResizable(false);
        setLocation(680, 150);

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        ImageIcon img = new ImageIcon("./resources/icon/Eye Watch (System Tray Icon).png");
        setIconImage(img.getImage());
        setVisible(true);

        // Listen to the button the button and if clicked, respond back with an action (or call a method in this case)
        actionListeners();

        // Operating system class
        // Instance variables
        OperatingSystem os = new OperatingSystem(this);
        os.runInBackground();

    }


    private void settingToolTipText(){
        localDirBrowseButton.setToolTipText("Open local file explorer");
        localDirTextField.setToolTipText("Enter path to local directory");
        remoteDirTextField.setToolTipText("Enter path to remote directory");
        hostOrIPTextField.setToolTipText("Enter hostname or IP address");
        HTTPSPortNumTextField.setToolTipText("Enter a HTTPS port number");
        usernameTextField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");
        runOnStartupCheckBox.setToolTipText("Start the program on startup");
        saveSettingsButton.setToolTipText("Run the program and save the settings");
    }


    private void settingLogComponents(Border border){
        log.setEditable(false);
        log.setLineWrap(true);
        log.setRows(10);
        log.setForeground(Color.BLUE);
        log.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }


    private void settingDescriptionTextAreaComponents(Border border) {
        descriptionTextArea.setFont(descriptionTextArea.getFont().deriveFont(Font.BOLD, descriptionTextArea.getFont().getSize()));
        descriptionTextArea.setBackground(Color.BLACK);
        descriptionTextArea.setForeground(Color.WHITE);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setRows(3);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }


    private void managingFlowPanels(){
        flow2Panel.add(emptySpaceLabel);
        //Local Directory
        flow3Panel.add(localDirLabel);
        flow4Panel.add(localDirTextField);
        flow4Panel.add(localDirBrowseButton);
        flow5Panel.add(emptySpaceLabel);
        // Remote Directory
        flow6Panel.add(remoteDirLabel);
        flow7Panel.add(hostOrIPLabel);
        flow7Panel.add(hostOrIPTextField);
        flow7Panel.add(Box.createHorizontalStrut(37));
        flow7Panel.add(HTTPSPortNumLabel);
        flow7Panel.add(HTTPSPortNumTextField);
        flow8Panel.add(usernameLabel);
        flow8Panel.add(Box.createHorizontalStrut(72));
        flow8Panel.add(usernameTextField);
        flow8Panel.add(Box.createHorizontalStrut(38));
        flow8Panel.add(passwordLabel);
        flow8Panel.add(passwordField);
        flow9Panel.add(remoteDirTextField);
        flow9Panel.add(Box.createHorizontalStrut(1));
        flow10Panel.add(emptySpaceLabel);
        // run on Startup Checkbox
        flow11Panel.add(runOnStartupCheckBox);
        flow11Panel.add(Box.createHorizontalStrut(296));
        // Done Button
        flow11Panel.add(saveSettingsButton);
        // otpCode
        flowOtpPanel.add(otpCodeLabel);
        flowOtpPanel.add(otpCodeTextField);
        flowOtpPanel.add(Box.createHorizontalStrut(1));
    }


    private void managingGridPanels(){
        gridPanel.add(flow2Panel);
        gridPanel.add(flow3Panel);
        gridPanel.add(flow4Panel);
        gridPanel.add(flow5Panel);
        gridPanel.add(flow6Panel);
        gridPanel.add(flow7Panel);
        gridPanel.add(flow8Panel);
        gridPanel.add(flowOtpPanel);
        gridPanel.add(flow9Panel);
        gridPanel.add(flow10Panel);
        gridPanel.add(flow11Panel);
        gridPanel.setBackground(Color.ORANGE);
    }


    private void actionListeners(){
        // Browse button
        localDirBrowseButton.addActionListener(e -> {
            File localDirPath = OperatingSystem.chooseLocalDirectory();
            if(localDirPath != null) localDirTextField.setText(localDirPath.getAbsolutePath());
        });

        // Save Settings Button
        saveSettingsButton.addActionListener(e -> {
            if(verifyEntries()){
                String otp_code = getOtp_Code();
                if(otp_code.length() == 0 || otp_code.equals("*")) otp_code = "*";

                // monitoring local directory in a new thread
                WatchLocalDirectoryStateThread watchLocalDirThread = new WatchLocalDirectoryStateThread(this, getLocalDirPath());
                watchLocalDirThread.setDaemon(true);
                watchLocalDirThread.setPriority(Thread.MIN_PRIORITY);
                watchLocalDirThread.start();

                lib.writeToDatabase(getLocalDirPath(), getHostname(), getPortNumber(), getUsername(), getPassword().toString(), getRemoteDirPath(), otp_code, getRunOnStartupCheckbox().toString());
            }
        });

        // About menu item
        aboutJMenuItem.addActionListener(e -> {
            aboutWindow();
        });

    }


    // called in GUI.java and OperatingSystem.java
    protected static void aboutWindow(){
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setBackground(Color.WHITE);
        Color aboutFrameColor = aboutFrame.getBackground();
        Border border = BorderFactory.createLineBorder(aboutFrameColor);

        // Icon image Label
        ImageIcon desktopIconImg = new ImageIcon("./resources/icon/Eye Watch (Desktop Icon).png");
        JLabel iconLabel = new JLabel(desktopIconImg);

        // The main title Label
        JLabel titleLabel = new JLabel("Eye Watch");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 32));

        GregorianCalendar gc = new GregorianCalendar();
        int year = gc.getWeekYear();

        // The copyrights text area
        JTextArea copyrightsTextArea = new JTextArea("Copyright (c) " + year + " Danish Naseem. All rights reserved.");
        copyrightsTextArea.setLineWrap(true);
        copyrightsTextArea.setWrapStyleWord(true);
        copyrightsTextArea.setBackground(aboutFrameColor);
        copyrightsTextArea.setEditable(false);
        copyrightsTextArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        copyrightsTextArea.setRows(2);

        // The ok button
        JButton okButton = new JButton("OK");

        // The titlePanel for icon image label main title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        // The grid panel for the titlePanel and the copyrights text area
        JPanel gridPanel = new JPanel(new GridLayout(3, 2));
        gridPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 30, 30, 30)));
        gridPanel.add(titlePanel);
        gridPanel.add(Box.createVerticalStrut(0));
        gridPanel.add(copyrightsTextArea);
        gridPanel.setBackground(Color.WHITE);

        // The btnPanel for the ok Button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(okButton);
        btnPanel.setBackground(Color.WHITE);

        // The grid panel for the btnPanel containing the ok button
        JPanel btnGridPanel = new JPanel(new GridLayout(1,1));
        btnGridPanel.add(btnPanel);

        // Adding the gridPanel (containing the titlePanel and the copyrights text area) and the btnGridPanel (containing the btnPanel for the ok Button)
        aboutFrame.add(gridPanel, BorderLayout.CENTER);
        aboutFrame.add(btnGridPanel, BorderLayout.SOUTH);

        aboutFrame.setSize(350, 240);
        aboutFrame.setResizable(false);
        aboutFrame.setLocation(750, 420);

        aboutFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);

        ImageIcon img = new ImageIcon("./resources/icon/Eye Watch (System Tray Icon).png");
        aboutFrame.setIconImage(img.getImage());
        aboutFrame.setVisible(true);

        okButton.addActionListener(e -> {
            aboutFrame.setVisible(false);
        });
    }


    // called in various classes those need their errors, success or just processing to be logged and displayed to the user
    protected static void appendLog(String text){
        log.append(text+"\n");
    }


    private void addActionForEditJMenu(TextAction action, int key, String text){
        action.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK));
        action.putValue(AbstractAction.NAME, text);
        editJMenu.add(new JMenuItem(action));
        popupMenu.add(new JMenuItem(action));
    }


    private void setPopup(JTextComponent... components){
        if(components == null){
            return;
        }
        for (JTextComponent tc : components) {
            tc.setComponentPopupMenu(popupMenu);
        }
    }


    private boolean verifyEntries(){
        String localDirPath = getLocalDirPath();
        String hostname = getHostname();
        String portNumber = getPortNumber();
        String username = getUsername();
        char[] password = getPassword();
        String otp_code = getOtp_Code();
        String remoteDirPath = getRemoteDirPath();

        if(localDirPath.length() == 0) {
            JOptionPane.showMessageDialog(this, "Local Directory Path is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(hostname.length() == 0) {
            JOptionPane.showMessageDialog(this, "Hostname Or IP Text Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(portNumber.length() == 0) {
            JOptionPane.showMessageDialog(this, "Port Number Text Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(username.length() == 0) {
            JOptionPane.showMessageDialog(this, "Username Text Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(password.length == 0) {
            JOptionPane.showMessageDialog(this, "Password Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(remoteDirPath.length() == 0) {
            JOptionPane.showMessageDialog(this, "Remote Directory Path is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(otp_code.length() > 0) {
            Boolean val = verifyInteger(otp_code);
            if(val == false){
                JOptionPane.showMessageDialog(this, "Entered 2 Step Verification Code is not an Integer number. Please try again.", "Value Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else return true;
        }
        else if(portNumber.length() > 0) {
            Boolean val = verifyInteger(portNumber);
            if(val == false) {
                JOptionPane.showMessageDialog(this, "Entered Port Number is not an Integer number. Please try again.", "Value Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else return true;
        }

        else return true;
    }


    private boolean verifyInteger(String intToVerify){
        try{
            Integer.parseInt(intToVerify);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }


    public void setLocalDirPath(String localDirPath){
        if(localDirPath.equals("*")) localDirTextField.setText("");
        else localDirTextField.setText(localDirPath);
    }

    public String getLocalDirPath(){
        return localDirTextField.getText();
    }


    public void setHostname(String hostname){
        if(hostname.equals("*")) hostOrIPTextField.setText("");
        else hostOrIPTextField.setText(hostname);
    }

    public String getHostname(){
        return hostOrIPTextField.getText();
    }


    public void setPortNumber(String portNumber){
        if(portNumber.equals("*")) HTTPSPortNumTextField.setText("");
        else HTTPSPortNumTextField.setText(portNumber);
    }

    public String getPortNumber(){
        return HTTPSPortNumTextField.getText();
    }


    public void setUsername(String username){
        if(username.equals("*")) usernameTextField.setText("");
        else usernameTextField.setText(username);
    }

    public String getUsername(){
        return usernameTextField.getText();
    }


    public void setPassword(String password){
        if(password.equals("*")) passwordField.setText("");
        else passwordField.setText(password);
    }

    public char[] getPassword(){
        return passwordField.getPassword();
    }


    public void setOtp_Code(String otp_code){
        if(otp_code.equals("*")) otpCodeTextField.setText("");
        else otpCodeTextField.setText(otp_code);
    }

    public String getOtp_Code(){
        return otpCodeTextField.getText();
    }


    public void setRemoteDirPath(String remoteDirPath){
        if(remoteDirPath.equals("*")) remoteDirTextField.setText("");
        else remoteDirTextField.setText(remoteDirPath);
    }

    public String getRemoteDirPath(){
        return remoteDirTextField.getText();
    }


    public void setRunOnStartupCheckBox(Boolean runOnStartupChkBox){
        runOnStartupCheckBox.setSelected(runOnStartupChkBox);
    }

    public Boolean getRunOnStartupCheckbox(){
        return runOnStartupCheckBox.isSelected();
    }


}
