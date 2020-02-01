# README

The intention of this software for me is solely to delete the Fortnite video files from my PC after they have successfully been uploaded to the Synology DiskStation. As NVIDIA GEFORCE NOW saves the gameplay captures on my local hard drive as I'm playing the game, which causes low disk space on my (C:) Drive, therefore after uploading these video files to my Synology DiskStation using the app called Synology Drive, this software would delete those local video files. I'll try my best not to hard code this software, so it can be of more general use.

- For more info refer to the following:
  - `Documentation.docx` for the full visual documentation
  - `Documentation.md` for the markdown version of it, without the images.

- To RUN the program refer to the JAR file:
  - `Target/EyeWatch.jar`

- For TESTING refer to:
  - `src/test/java/Test/Testing.java`

<hr>

### TODO

- Change the name of the entire program from EyeWatch to EyePurgeWatch

- Call the SynologyAPI&#39;s  run method from the Lib.java constructor and save the Filenames in a LinkedList.

- Make a method in Lib.java that compares two Linked Lists (in this case they&#39;ll be the remote files linked list and local files linked list). This will have O(n^2) complexity, as it will compare each item from each linked list, hence running in a nested loop. While running in a loop, it will append each item that is present in both the linked lists to a linked list, which is going to be returned at the end of the function call. That list will later be passed on to the delete method to delete those files.

- Fix either GUI.java or OperatingSystem.java so that the database doesn&#39;t save setting until the monitoring has started correctly.

- Fix the port number error in the SynologyAPI.java

- Figure out the bug that is causing the GUI to not display the https port number error if the typed https port number is not an integer.

- Make the WatchLocalDirectoryStateThread class in OperatingSystem.java more efficient so it doesn&#39;t take up the CPU when running in Idle. Create idle method to achieve this.

- Write the constructor for Lib.java to connect to everything and finally make the entire program run.

