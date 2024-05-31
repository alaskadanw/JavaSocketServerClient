package org.wegh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final String USAGE_MESSAGE = "Usage: java EchoServer <port number>";
    private static final String CLIENT_MESSAGE = "Hey, I've served %d clients!";
    private int clientCounter = 0;
    private int portNumber = 0;

    private EchoServer(int port) {
        portNumber = port;
    }

    public static void main(String[] args) {
        if (args.length != 1)
            System.err.println(USAGE_MESSAGE);
        else {
            int port = Integer.parseInt(args[0]);
            EchoServer server = new EchoServer(port);
            server.doServerWork();
        }
    }

    private void doServerWork() {
        boolean isListening = true;
        while (isListening) {
            System.out.printf("waiting to accept client on port:%d%n", portNumber);
            try (ServerSocket serverSocket = new ServerSocket(portNumber);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("Accepted client");

                clientOutput.println("Hey, I've served %d clients! " + ++clientCounter); // send a message to client
                System.out.println("Server sent message to client");

                String receivedClientLine = clientInput.readLine(); // get a client message
                System.out.printf("Client said this: %s%n", receivedClientLine);
                if (isShutdownCommand(receivedClientLine))
                    isListening = false;
                System.out.println("Server about to close the latest client's sockets.");
                System.out.println("Server will loop back for new clients: " + isListening);
            } catch (IOException exception) {
                System.out.printf("Exception caught when listening for a connection on port %d\n%s%n", portNumber, exception.getMessage());
            }
        }
        System.out.println("Server has closed sockets. About to finish.");
    }
    private static boolean isShutdownCommand(String inputLine) {
        return inputLine != null && inputLine.equals("shutdown");
    }
}