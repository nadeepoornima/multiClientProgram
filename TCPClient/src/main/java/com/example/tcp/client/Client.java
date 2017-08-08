package com.example.tcp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Client {
    public static void main(String args[]) throws IOException {
        System.out.println("++start client main+++");
        String hostname = "localhost";
        int portNumber = 8005;
        try (Socket showSocket = new Socket(hostname, portNumber); PrintWriter out = new PrintWriter(showSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(showSocket.getInputStream())); BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("+++show :" + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("+++Don't konw about host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("++Could't get I/O for the connection to:" + hostname);
            System.exit(1);
        }
    }
}