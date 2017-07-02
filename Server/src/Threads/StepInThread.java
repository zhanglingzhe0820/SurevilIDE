package Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import Exceptions.WrongFileFormException;
import Threads.Runner.Runner;

public class StepInThread extends Thread{
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private Runner codeRunner;

	public StepInThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	

	@Override
	public void run() {
		try {
			String text="";
			String messageInput="";
			System.out.println("客户端已连接");

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
				out.println("Wrong code language type");
				out.flush();
				out.close();
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("Can't get incoming socket!");
			e.printStackTrace();
		}
	}
	
}