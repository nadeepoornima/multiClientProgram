package com.example.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Queue;
import java.util.ArrayDeque;

public class Server {
    static Queue<Socket> ClientWaitingQueue = new ArrayDeque<Socket>();

    public static void main(String args[]) throws IOException, InterruptedException {
        System.out.println("++++++ Start of Main ++++++++");
        int portNumber = 8005;
        int maxPoolSize;
        int clientAcceptCont = 0;
        ExecutorService executor = null;
        try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter ThreadPool size:");
            maxPoolSize = input.nextInt();
            executor = Executors.newFixedThreadPool(maxPoolSize);
            System.out.println("+++++ waiting for multiple clients +++++");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientAcceptCont = clientAcceptCont + 1;
                Runnable worker = new RequestHandler(clientSocket);
                executor.execute(worker);
                System.out.println("+++++ Accepted number of clients :" + clientAcceptCont);
                if (clientAcceptCont > maxPoolSize) {
                    System.out.println("++++++ Need to add additinal client to waiting queue++++++++");
                    ClientWaitingQueue.add(clientSocket);
                    for (int i = 0; i < ClientWaitingQueue.size(); i++) {
                        System.out.println("+++ elements +++ :" + ClientWaitingQueue.element());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port" + portNumber + "or listening for a conection");
            System.out.println(e.getMessage());
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }
}