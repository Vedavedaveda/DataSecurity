Follow these steps to run the client/server application:
1. Run the ApplicationServer
2. Run the Client
3. In the console, log in with one of the following credentials
    - username: **user1**, password: **password**
    - username: **user2**, password: **password**
4. Enter one of the following commands:
     - **print** to "print" a user-specified file on a user-selected printer
     - **queue** to see which files are queued on a specific printer
     - **topQueue** to alter the queue of a specific printer such that the file in a particular spot is moved to the top
     - **status** to see the number of queued jobs on a specific printer
     - **readConfig** to see the configurations of a specific printer
     - **setConfig** to alter the configurations of a specific printer
     - **login** to log into an account (if a user is already logged in, then a successful login will end the current session and start a new one)
     - **logout** to log out of the account associated with the current session
     - **start** to start the print server
     - **stop** to stop the print server (printing operations will no longer be possible)
     - **restart** to restart the print server (can only be used when print server is running)
     - **exit** to stop the application
