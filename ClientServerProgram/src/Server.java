/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nadee
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;

public class Server {
	public static void main(String args[]) throws IOException{
		System.out.println("###############main started ############");
		if(args.length<1){
			System.err.println("#######Usage: java EchoServer <port number>");
			System.exit(1);
		}
		int portNumber = Integer.parseInt(args[0]);
                //thread pool creation using ExecutorService class 
		ExecutorService executor = null;
                //server socket implement and port number should give as an argument
		try(ServerSocket serverSocket = new ServerSocket(portNumber);){
			executor = Executors.newFixedThreadPool(5);
			System.out.println("#####Waiting for clients########");
                        //infinite loop for accept the client requests
                        //here come the client connection and wait as queue 
                        //each client create thred, when finish the threadpool, it wait until accepted client shutdown
			while (true){
				Socket clientSocket = serverSocket.accept();
				Runnable worker = new RequestHandler(clientSocket);
				executor.execute(worker);
			}
		}catch (IOException e){
			System.out.println("Exception caught when trying to listen on port"+ portNumber + "or listening for a connection");
			System.out.println(e.getMessage());
		}finally {
			if (executor != null){
				
			}
		}	
	}

}
