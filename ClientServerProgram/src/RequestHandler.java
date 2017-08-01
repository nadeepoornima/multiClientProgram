/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nadee
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler implements Runnable {
	private final Socket client;
	ServerSocket serverSocket = null;
	public RequestHandler(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
            //input and output streams for communicate server
		try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));){
			System.out.println("##############Thread started with name:"+Thread.currentThread().getName());
			String userInput;
			while((userInput = in.readLine()) != null){
				userInput = userInput.replaceAll("[^A-Za-z0-9 ]", "");
				System.out.println("##########Received message from "+ Thread.currentThread().getName() + ":" + userInput);
				writer.write("#######you entered :"+ userInput);
				writer.newLine();
				writer.flush();
                              //  wait(100000);
//                                System.out.println(notify());
			}
		}catch(IOException e){
			System.out.println("###I/O exception###"+e);
		}catch(Exception ex){
			System.out.println("####Exception in Thread Run. Exception###:" +ex);
		}

	}

}

