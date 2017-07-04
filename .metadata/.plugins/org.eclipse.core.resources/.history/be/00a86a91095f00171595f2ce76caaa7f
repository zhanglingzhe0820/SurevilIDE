package Threads;
import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClearTempThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private File file;

	public ClearTempThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public synchronized void run() {
		try {
			String username="";
			System.out.println("客户端已连接");

			username=in.readLine();
			
			file=new File("L:\\javaHomework\\Temp\\"+username);//存档地址
			
			//清空temp文件夹
			if(!(file.list()==null||file.list().length==0)){
				for(String s:file.list()){
					new File(file,s).delete();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
