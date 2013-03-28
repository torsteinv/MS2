package no.torsteinv.MS2.Multiplayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable{
	Socket clientSock;
	BufferedReader in;
	PrintWriter out;
	ServerSocket socket;
	
	public Server(int PORT){
		try{
			socket = new ServerSocket(PORT);
			
			Thread th = new Thread(this);
			th.start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void processClient(BufferedReader in, PrintWriter out) {
		try{
			while(true){
				if(in.ready())
				out.println(in.readLine());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		while(true){
			try{
				clientSock = socket.accept();
			
				in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			
				out = new PrintWriter(clientSock.getOutputStream(),true);
			
				processClient(in,out);
			
				clientSock.close();
				in.close();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
