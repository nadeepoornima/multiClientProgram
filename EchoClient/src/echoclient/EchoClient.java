/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author nadee
 */
class EchoClient implements Runnable{
         
    @Override
    public void run(){

        String sentence;
        String modifiedSentence = null;
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", 8005);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataOutputStream outToServer = null;
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader inFromServer = null;
        try {
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        sentence = "Client carries the number :" + String.valueOf(Math.random());
        try {
            outToServer.writeBytes(sentence + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            modifiedSentence = inFromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FROM SERVER: " + modifiedSentence);

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

}

         
         
}

 class runClients {
    
    public static void main(String args[]){
        
        int nClients = Integer.parseInt(args[0]);
        
        for(int i =0 ; i < nClients ; i++){
            new Thread(new EchoClient()).start();
        }
        
    }
    
}
