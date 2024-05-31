These two Java classes were used to help me to learn about web sockets for a chat application project.
It helps one see how the Java API for sockets can be used in a simple way.

Add these files to a project in your IDE. One is a server and the other is a client.
Update the package names if necessary. Currently it is package org.wegh;
Set up the Run configuration for each file. 
  - For the server it is just the port #. 6000 is good.
  - For the client, use the same port number and then an optional client message for the server. 
Run the EchoServer class.
Run the EchoClient class. To start it in IntelliJ, I right clicked in the code and chose Run.

If you are not debugging, the client will run and complete almost instantly, but you should note that:
  - it first connects to the server
  - then it prints out a message from the server, including how many clients the server has processed
  - sends a default message to the server, or one you included in the run configuration
  - each class prints these messages to System.out so that you can confirm the socket connections are working
The server listens on a loop, so although the client program runs and finishes, the server immediately begins waiting for another client to connect.
To end the server without stopping it manually, edit the client run configuration to send the message: 'shutdown'.

The examples I found on the internet didn't always work consistently, 
probably because they didn't properly insure that sockets and streams were always closed. Fixes that seemed to help me were:
  - implement the new try-with-resources syntax
  - move socket code out of static methods so it is instance code that finishes and clears right away
  - use System.out.println instead of System.out.printf for code sent to a socket
Do these make a difference? I'm not sure. Maybe I just got my act together and finally coded things right.
It was confusing to me to run two programs at first. It really slowed me down and was frustrating.

Hope these two programs help anyone who uses them to learn about Java sockets!
