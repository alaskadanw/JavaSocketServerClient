package org.wegh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    private static final String HOST = "localhost";
    private static final String DEFAULT_CLIENT_MESSAGE = "Hey, I'm the client!";
    private static final String CONNECTION_ISSUE_MSG = "Problem with connection. Is server running?";
    private static final String USAGE_MESSAGE = "Usage: java EchoClient <port number> <opt client message>";
    private int portNo;
    private String clientMsg;

    private EchoClient(String...  args) {
        if (args.length != 1 && args.length != 2)
            System.err.println(USAGE_MESSAGE);
        else {
            portNo = Integer.parseInt(args[0]);
            clientMsg = (args.length == 1) ? "Hey Server, I'm the client!" : args[2];
        }
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient(args);
        client.doClientWork();
    }

    private void doClientWork() {
        System.out.println("Client about to connect to server socket on port " + portNo);
        try (Socket clientSocket = new Socket(HOST, portNo);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Client about to listen for server message");
            String serverResponse = in.readLine();
            if (serverResponse != null)
                System.out.printf("Server said: %s%n", serverResponse);
            System.out.println("Finished getting server message.");

            System.out.println("Client about to send server this message: " + clientMsg);
            out.println(clientMsg); // send a message to client
            System.out.println("Client sent message to client");
        } catch (IOException e) {
            System.out.println(CONNECTION_ISSUE_MSG);
            e.printStackTrace();
        }
        System.out.println("Client has closed sockets. About to finish.");
    }
}
