package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GetVersionsClientThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;

	public GetVersionsClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public synchronized void run(){
		try {
			String username="";
			String result="";
			System.out.println("�ͻ���������");

			out=new PrintWriter(incoming.getOutputStream());
			username=in.readLine();
			File file=new File("L:\\javaHomework\\UserSpace\\"+username);//�浵�ļ���
			if(file.list()==null||file.list().length==0){
				out.println("None");
				out.flush();
				out.close();
			}
			else{
				for(String s: file.list()){
				result=result+"/"+s;
				}
				out.println(result);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("�������޷�ȡ���ļ���");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
}
