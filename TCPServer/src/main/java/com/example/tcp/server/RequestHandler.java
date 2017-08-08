package com.example.tcp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private final Socket client;
    ServerSocket serverSocket = null;

    public RequestHandler(Socket client) {
        this.client = client;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));) {
            System.out.println("+++Thread started with name: " + Thread.currentThread().getName());
            String userInput;
            while ((userInput = in.readLine()) != null) {
                userInput = userInput.replaceAll("[^A-Za-z0-9 ]", "");
                System.out.println("+++Received message from : " + Thread.currentThread().getName() + " : " + userInput);
                writer.write("++ your entered : " + userInput);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("+++I/O Exception: " + e);
        } catch (Exception ex) {
            System.out.println("+++Exception in Thread Run. Exception : " + ex);
        }
    }
}
