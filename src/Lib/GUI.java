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
import java.util.GregorianCalendar;

public class GUI extends JFrame {
    //TODO: add otp_code functionality too

    // GUI Components
    static JTextArea log = new JTextArea();

    private JMenu jMenu = new JMenu("Edit");
    private JMenuBar jMenuBar = new JMenuBar();
    private JPopupMenu popupMenu = new JPopupMenu();

    private JTextArea descriptionTextArea  = new JTextArea("Scans the remote directory for files that exist in the local directory. If they exist" +
            " in the remote directory, then they will be deleted from the local directory; otherwise the local directory will be monitored for any" +
            " new or modified files, if so then another scan will run on the remote directory.");
    private JLabel emptySpaceLabel   = new JLabel(" ");
    private JLabel localDirLabel     = new JLabel("<html><span style='font-size:11px'> Local Directory </span></html>");
    private JLabel remoteDirLabel    = new JLabel("<html><span style='font-size:11px'> Remote Directory </span></html>");
    private JLabel hostOrIPLabel     = new JLabel("Hostname or IP address");
    private JLabel HTTPSPortNumLabel = new JLabel("HTTPS Port Number");
    private JLabel usernameLabel     = new JLabel("Username");
    private JLabel passwordLabel     = new JLabel("Password");
    private JLabel otpCodeLabel          = new JLabel("2 Step Verification Code");

    JTextField localDirTextField     = new JTextField(40);
    JTextField remoteDirTextField    = new JTextField(47);
    JTextField hostOrIPTextField     = new JTextField(12);
    JTextField HTTPSPortNumTextField = new JTextField(7);
    JTextField usernameTextField     = new JTextField(12);
    JTextField otpCodeTextField      = new JTextField(7);

    JPasswordField passwordField = new JPasswordField(12);

    JButton localDirBrowseButton = new JButton("Browse");
    JButton doneButton           = new JButton("Save Settings");

    JCheckBox runOnStartupCheckBox = new JCheckBox("Run on Startup");

    // Instance Variables
    private String hostname;
    private Integer portNumber;
    private String username;
    private String password;
    private Boolean runOnStartupCheckbox;

    public GUI(){
        super("Eye Watch");

        this.hostname = "";
        this.portNumber = null;
        this.username = "";
        this.password = "";
        this.runOnStartupCheckbox = false;

        JPanel flow2Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow3Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow4Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow5Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow6Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow7Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow8Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow9Panel  = new JPanel (new FlowLayout (FlowLayout.RIGHT));
        JPanel flow10Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow11Panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flowOtpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel gridPanel  = new JPanel(new GridLayout(11,6));

        addAction(new DefaultEditorKit.CutAction(), KeyEvent.VK_X,  "Cut");
        addAction(new DefaultEditorKit.CutAction(), KeyEvent.VK_C,  "Copy");
        addAction(new DefaultEditorKit.CutAction(), KeyEvent.VK_V,  "Paste");

        setPopup(log, localDirTextField, remoteDirTextField, hostOrIPTextField, HTTPSPortNumTextField, usernameTextField, passwordField);

        Color jFrameColor = this.getBackground();
        //System.out.println(jFrameColor);
        Border border = BorderFactory.createLineBorder(new Color(130, 130, 130));

        log.setEditable(false);
        log.setLineWrap(true);
        log.setRows(10);
        log.setForeground(Color.BLUE);
        log.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        localDirBrowseButton.setToolTipText("Open local file explorer");
        localDirTextField.setToolTipText("Enter path to local directory");
        remoteDirTextField.setToolTipText("Enter path to remote directory");
        hostOrIPTextField.setToolTipText("Enter hostname or IP address");
        HTTPSPortNumTextField.setToolTipText("Enter a HTTPS port number");
        usernameTextField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");
        runOnStartupCheckBox.setToolTipText("Start the program on startup");
        doneButton.setToolTipText("Run the program and save the settings");

        descriptionTextArea.setFont(descriptionTextArea.getFont().deriveFont(Font.BOLD, descriptionTextArea.getFont().getSize()));
        descriptionTextArea.setBackground(Color.BLACK);
        descriptionTextArea.setForeground(Color.WHITE);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setRows(3);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);


        descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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
        flow11Panel.add(doneButton);

        // otpCode
        flowOtpPanel.add(otpCodeLabel);
        flowOtpPanel.add(otpCodeTextField);
        flowOtpPanel.add(Box.createHorizontalStrut(1));

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

        jMenuBar.add(jMenu);

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

        actionListeners();

    }

    private void actionListeners(){
        // Browse button
        localDirBrowseButton.addActionListener(e -> {
            File localDirPath = OperatingSystem.chooseLocalDirectory();
            if(localDirPath != null) localDirTextField.setText(localDirPath.getAbsolutePath());
        });


    }


    public static void aboutWindow(){
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


    public static void appendLog(String text){
        log.append(text+"\n");
    }


    private void addAction(TextAction action, int key, String text){
        action.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK));
        action.putValue(AbstractAction.NAME, text);
        jMenu.add(new JMenuItem(action));
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

    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public String getHostname(){
        return this.hostname;
    }


    public void setPortNumber(Integer portNumber){
        this.portNumber = portNumber;
    }

    public Integer getPortNumber(){
        return this.portNumber;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }


    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }


    public void setRunOnStartupCheckbox(Boolean runOnStartupCheckbox){
        this.runOnStartupCheckbox = runOnStartupCheckbox;
    }

    public Boolean getRunOnStartupCheckbox(){
        return this.runOnStartupCheckbox;
    }



}
