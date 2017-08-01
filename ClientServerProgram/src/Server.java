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
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Server {
    
    static Queue<Socket> Nadee = new ArrayDeque<Socket>();
	public static void main(String args[]) throws IOException, InterruptedException{
            
            
            
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
                    int poolSize =3;
                    Scanner input = new Scanner(System.in);
                    int maxPoolSize = 0;
                    System.out.println("Enter your max size:");
                    maxPoolSize = input.nextInt();
                    executor = Executors.newFixedThreadPool(maxPoolSize);
			System.out.println("#####Waiting for clients########");
                        //infinite loop for accept the client requests
                        //here come the client connection and wait as queue 
                        //each client create thred, when finish the threadpool, it wait until accepted client shutdown
                        int acceptCount =0;
			while (true){
				Socket clientSocket = serverSocket.accept();
                                acceptCount = acceptCount + 1;
				Runnable worker = new RequestHandler(clientSocket);
				executor.execute(worker);
                                System.out.println("###accept count:"+acceptCount);
                                if(acceptCount>maxPoolSize){
                                    //Socket clientSocket = serverSocket.accept();
                                    //Runnable worker = new RequestHandler(clientSocket);
                                    System.out.println("******:");
                                    Nadee.add(clientSocket);
                                    for(int i=0; i<Nadee.size();i++){
                                        System.out.println("#:"+Nadee.size());
                                        System.out.println("####:"+Nadee.element());
//                                        if(){
//                                            Nadee.offer(clientSocket);
//                                            executor.execute(worker);
                                            acceptCount = acceptCount-1;
//                                            System.out.println(Nadee.peek());
//                                            
//                                        }
                                    }
                                    
                                }
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
