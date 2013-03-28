package no.torsteinv.MS2.Multiplayer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import no.torsteinv.MS2.Main.PostExcecution.Excecuter;


public class ServerClient implements Runnable{
	public static int PORT;
	public static String HOST;
	
	private HashMap<String, Boolean> Messages = new HashMap<String, Boolean>();
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	public ServerClient(String Host,int Port){
		PORT = Port;
		HOST = Host;
		makeContact();
	}
	public void makeContact(){
		try {
			socket = new Socket(HOST, PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			
			Thread th = new Thread(this);
			th.start();
			
			out.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	private void ProccessSignals(BufferedReader in, PrintWriter out) {
			Scanner InputScanner = new Scanner(in);
			Post(out);
			Recv(InputScanner);
	}
	private void Recv(Scanner inputScanner) {
		while(inputScanner.hasNext()){
			try {
				String str = "";
				Excecuter.excecuteCommand((str = inputScanner.nextLine()),str.contains(" %OLDSYSTEM%"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void Post(PrintWriter out) {
		for(String cmd : Messages.keySet()){
			out.println(cmd + " %OLDSYSTEM%");
		}
		Messages.clear();
	}
	@Override
	public void run() {
		while(true){
			ProccessSignals(in,out);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void Post(String cmd,boolean old){
		Messages.put(cmd, old);
	}
}
