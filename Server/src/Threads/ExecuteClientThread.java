package Threads;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Exceptions.WrongFileFormException;
import Runner.Runner;

public class ExecuteClientThread extends Thread{
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private Runner codeRunner;

	public ExecuteClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String text="";
			String messageInput="";
			System.out.println("�ͻ���������");

			out=new PrintWriter(incoming.getOutputStream());
			text=in.readLine();
			messageInput=in.readLine()+'\n';
			try {
				if(!(text==null||text.length()==0)){
					codeRunner=new Runner(text,messageInput);
					out.println(codeRunner.executeCode());
					out.flush();
					out.close();
				}
				else{
					out.println("Wrong code language type");
					out.flush();
					out.close();
				}
			} catch (WrongFileFormException e) {
				// TODO Auto-generated catch block
				out.println("Wrong code language type");
				out.flush();
				out.close();
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't get incoming socket!");
			e.printStackTrace();
		}
	}
	
}