package Lib;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.SSLException;
import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.LinkedList;


public class SynologyAPI {

    /* Error Codes:
    Synology API codes:
        400: Invalid Username or Password
        403: 2 Step Verification Required
        404: Provided 2 Step Verification is invalid
        408: Requested directory or file path does not exist

    Custom Codes:
        500: Connection Error: Either connection to the internet was lost or possibly an invalid hostname or HTTPS port number was entered
        502: Unknown User Error
        503: HTTP port used
     */

    private final String hostname;
    private final Integer portNumber;
    private String sid;


    public SynologyAPI(String hostname, Integer portNumber){
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.sid = "";
    }


    // STEP-1
    private JSONObject getAPI_Info() throws IOException, JSONException {
        String url = "https://" + hostname + ":" + portNumber +
                "/webapi/query.cgi?api=SYNO.API.Info&version=1&method=query&query=SYNO.API.Auth,SYNO.FileStation.List";

        JSONObject response = Http_Client.getRequest(url);
        return response;
    }


    // STEP-2a
    // If User provided the Two-Step-Verification code, then run the following
    private JSONObject authenticateWith2StepVerification(String username, String password, String otp_code) throws IOException, JSONException {
        String url = "https://" + hostname + ":" + portNumber + "/webapi/auth.cgi?api=SYNO.API.Auth&version=3&method=login&account=" +
                username + "&passwd=" + password + "&session=FileStation&format=cookie&otp_code=" + otp_code;

        JSONObject response = Http_Client.getRequest(url);

        return response;
    }


    // STEP-2b
    // If User didn't provide with the Two-Step-Verification code, then run the following, but
    // if we receive error code 403, we prompt the user that 2-Step-Verification-Code is required and return null
    private JSONObject authenticateWithout2StepVerification(String username, String password) throws IOException, JSONException {
        String url = "https://" + hostname + ":" + portNumber + "/webapi/auth.cgi?api=SYNO.API.Auth&version=3&method=login&account=" +
                username + "&passwd=" + password + "&session=FileStation&format=cookie";

        JSONObject response = Http_Client.getRequest(url);

        return response;
    }


    // STEP-3
    private JSONObject accessRemoteDirectory(String remotePath) throws IOException, JSONException {
        String url = "https://" + hostname + ":" + portNumber +
                "/webapi/entry.cgi?api=SYNO.FileStation.List&version=1&method=list&additional=real_path%2Csize%2Cperm%2Ctype&folder_path=%2F" +
                remotePath;
        String newUrl = url.replaceAll(" ", "%20");

        JSONObject response = Http_Client.getRequest(newUrl);
        return response;
    }


    // STEP-4 (Optional)
    public JSONObject logout() throws IOException, JSONException {
        String url = "https://"+ hostname + ":" + portNumber +
                "/webapi/auth.cgi?api=SYNO.API.Auth&version=1&method=logout&session=FileStation";
        JSONObject reponse = Http_Client.getRequest(url);
        return reponse;
    }


    private void displayErrors(Integer errCode){
        // CHECK: Are username and password are entered correctly
        if(errCode.equals(400)){
            System.out.println("Authentication Error: Invalid Username or Password.");
            // Show the popup
            String err_msg = "Invalid Username or Password.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Is 2 step verification code required
        else if(errCode.equals(403)){
            System.out.println("Authentication Error: 2 Step Verification Required.");
            // Show the popup
            String err_msg = "2 Step Verification code is required.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Is entered 2 Step Verification code correct
        else if(errCode.equals(404)){
            System.out.println("Authentication Error: Provided 2 Step Verification is invalid.");
            // Show the popup
            String err_msg = "Provided 2 Step Verification code is invalid.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Does requested directory or file path exist
        else if(errCode.equals(408)){
            System.out.println("System Error: Invalid directory or file path entered.");
            // Show the popup
            String err_msg = "Invalid directory or file path entered.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "System Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Is entered hostname or HTTPS port number valid
        else if(errCode.equals(500)){
            System.out.println("Connection Error: Please make sure you are connected to the internet and have entered a valid hostname and HTTPS port number.");
            // Show the popup
            String err_msg = "Please make sure you are connected to the internet and have entered a valid hostname and HTTPS port number.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Is there a user caused error
        else if(errCode.equals(502)){
            System.out.println("Unknown Error: Please recheck your entered information and Try Again.");
            // Show the popup
            String err_msg = "Please recheck your entered information and Try Again.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Unknown Error", JOptionPane.ERROR_MESSAGE);
        }
        // CHECK: Is port number a HTTP port number
        else if(errCode.equals(503)){
            System.out.println("Connection Error: HTTP port not supported.");
            // Show the popup
            String err_msg = "HTTP port not supported.";
            JOptionPane.showMessageDialog(new JFrame(), err_msg, "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private LinkedList<String> parseRemoteFilesJSON(JSONObject remoteFilesJSON) throws JSONException {
        LinkedList<String> filenames = new LinkedList<>();
        // Get the value of "data", which will be a dictionary
        JSONObject data = remoteFilesJSON.getJSONObject("data");
        // Then from that dictionary get value of "files", which will be an array
        JSONArray files = data.getJSONArray("files");
        // Then parse through each index of that array, which will be a dictionary
        for(int i = 0; i < files.length(); i++){
            // Then from that dictionary, get the value of "name", which will be the filename
            JSONObject currentFile = (JSONObject) files.get(i);
            String filename = (String) currentFile.get("name");
            // Then add that filename to the LinkedList and then keep on iterating through all the other files and adding them to the LinkedList
            filenames.add(filename);
        }
        // return the LinkedList
        return filenames;
    }


    // This function will return a list of remote directory filenames
    public LinkedList run(String username, String password, String remotePath, String otp_code) throws IOException, JSONException {
        LinkedList<String> filenames;

        // STEP-1 checking if hostname is valid
        System.out.println("Verifying hostname and HTTPS port number");
        try{
            JSONObject step1 = getAPI_Info();

            Boolean step1Success = step1.getBoolean("success");

            if(step1Success.equals(true)){
                // STEP-2 checking if username and password are valid
                System.out.println();
                System.out.println("Authenticating username and password");
                JSONObject step2 = authenticateWith2StepVerification(username, password, otp_code);
                filenames = runHelper(step2, remotePath);

            }
            else{
                displayErrors(502);
                return null;
            }

            return filenames;

        } catch (IOException e){
            if(e instanceof UnknownHostException || e instanceof ConnectException){
                displayErrors(500);
            } else if (e instanceof SSLException){
                displayErrors(503);
            }
            return null;
        }
    }


    public LinkedList run(String username, String password, String remotePath) throws IOException, JSONException {
        LinkedList filenames;

        // STEP-1 checking if hostname is valid
        System.out.println("Verifying hostname and HTTPS port number");
        try{
            JSONObject step1 = getAPI_Info();
            Boolean step1Success = step1.getBoolean("success");

            if(step1Success.equals(true)){
                // STEP-2 checking if username and password are valid
                System.out.println();
                System.out.println("Authenticating username and password");
                JSONObject step2 = authenticateWithout2StepVerification(username, password);
                filenames = runHelper(step2, remotePath);
            }
            else{
                displayErrors(502);
                return null;
            }
            return filenames;
        } catch (IOException e){
            if(e instanceof UnknownHostException || e instanceof ConnectException){
                displayErrors(500);
            } else if (e instanceof SSLException){
                displayErrors(503);
            }
            return null;
        }
    }


    private LinkedList runHelper(JSONObject authentication, String remotePath) throws JSONException, IOException {
        Boolean step2Success = authentication.getBoolean("success");

        LinkedList filenames;
        if(step2Success.equals(true)){
            JSONObject data = authentication.getJSONObject("data");
            String sid = data.getString("sid");
            this.sid = sid;
            System.out.println();
            System.out.println("Successfully logged in. Session ID Saved.");

            // STEP-3 checking if remote directory path is valid
            System.out.println();
            System.out.println("Connection established");
            System.out.println("Accessing the remote directory.");
            JSONObject remoteFilesJSON = accessRemoteDirectory(remotePath);
            Boolean remoteFilesJSONSuccess = remoteFilesJSON.getBoolean("success");
            if(remoteFilesJSONSuccess.equals(true)){
                System.out.println("Getting the filenames from the remote directory.");
                filenames = parseRemoteFilesJSON(remoteFilesJSON);
            }
            else{
                System.out.println();
                System.out.println("Remote Directory connection failed.");
                JSONObject error = remoteFilesJSON.getJSONObject("error");
                Integer code = error.getInt("code");
                displayErrors(code);
                return null;
            }
        }
        else{
            System.out.println();
            System.out.println("Authentication Failed");
            JSONObject error = authentication.getJSONObject("error");
            Integer code = error.getInt("code");
            displayErrors(code);
            return null;
        }
        return filenames;
    }


    //TODO: This main is only for testing, remove when the development is complete
    public static void main(String args[]) throws IOException, JSONException {
        SynologyAPI cloud = new SynologyAPI("danishnaseem05.synology.me", 5001);
        //System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite"));
        System.out.println(cloud.run("danishnaseem05", "DanNass6", "home/Drive/Videos/Other/NVIDIA/GeForce NOW/Fortnite", "186884"));
    }

}