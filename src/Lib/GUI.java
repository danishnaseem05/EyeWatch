package Lib;


import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    //TODO: add otp_code functionality too

    // GUI Components
    JTextArea log = new JTextArea();

    private JLabel descriptionLabel  = new JLabel("Description text goes here");
    private JLabel emptySpaceLabel   = new JLabel(" ");
    private JLabel localDirLabel     = new JLabel("<html><span style='font-size:11px'> Local Directory </span></html>");
    private JLabel remoteDirLabel    = new JLabel("<html><span style='font-size:11px'> Remote Directory </span></html>");
    private JLabel hostOrIPLabel     = new JLabel("Hostname or IP address");
    private JLabel HTTPSPortNumLabel = new JLabel("HTTPS Port Number");
    private JLabel usernameLabel     = new JLabel("Username");
    private JLabel passwordLabel     = new JLabel("Password");

    JTextField localDirTextField     = new JTextField(40);
    JTextField remoteDirTextField    = new JTextField(47);
    JTextField hostOrIPTextField     = new JTextField(12);
    JTextField HTTPSPortNumTextField = new JTextField(7);
    JTextField usernameTextField     = new JTextField(12);

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


        JPanel flow1Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow2Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow3Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow4Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow5Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow6Panel  = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow7Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow8Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow9Panel  = new JPanel (new FlowLayout (FlowLayout.LEFT));
        JPanel flow10Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JPanel flow11Panel = new JPanel (new FlowLayout (FlowLayout.LEFT));

        JPanel gridPanel  = new JPanel(new GridLayout(12,6));

        log.setEditable(false);
        log.setRows(5);

        localDirBrowseButton.setToolTipText("Open local file explorer");
        localDirTextField.setToolTipText("Enter path to local directory");
        remoteDirTextField.setToolTipText("Enter path to remote directory");
        hostOrIPTextField.setToolTipText("Enter hostname or IP address");
        HTTPSPortNumTextField.setToolTipText("Enter a HTTPS port number");
        usernameTextField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");
        runOnStartupCheckBox.setToolTipText("Start the program on startup");
        doneButton.setToolTipText("Run the program and save the settings");

        flow1Panel.add(descriptionLabel);
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

        flow10Panel.add(emptySpaceLabel);
        // run on Startup Checkbox
        flow11Panel.add(runOnStartupCheckBox);

        flow11Panel.add(Box.createHorizontalStrut(296));

        // Done Button
        flow11Panel.add(doneButton);

        gridPanel.add(flow1Panel);
        gridPanel.add(flow2Panel);
        gridPanel.add(flow3Panel);
        gridPanel.add(flow4Panel);
        gridPanel.add(flow5Panel);
        gridPanel.add(flow6Panel);
        gridPanel.add(flow7Panel);
        gridPanel.add(flow8Panel);
        gridPanel.add(flow9Panel);
        gridPanel.add(flow10Panel);
        gridPanel.add(flow11Panel);

        add(gridPanel, BorderLayout.CENTER);
        add(new JScrollPane(log), BorderLayout.SOUTH);

        setSize(555, 500);
        setResizable(false);
        setLocation(700, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("./resources/icon/Eye Watch (System Tray Icon).png");
        setIconImage(img.getImage());
        setVisible(true);

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


    //TODO: This main is only for testing, remove when the development is complete
    public static void main(String[] args){
        GUI gui = new GUI();

    }


}
