package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GetFileClientThread extends Thread{
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private BufferedReader inFile;
	private PrintWriter out;
	private File file;

	public GetFileClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public void run(){
		try {
			String username="";
			String fileName="";
			System.out.println("客户端已连接");

			out=new PrintWriter(incoming.getOutputStream());
			username=in.readLine();
			fileName=in.readLine();
			
			file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+fileName);//存档地址
			inFile=new BufferedReader(new FileReader(file));
			out.println(inFile.readLine());
			out.flush();
			out.close();
		}catch(Exception ex){
			out.println("版本获取失败");
			out.flush();
			out.close();
		}
	}
}
