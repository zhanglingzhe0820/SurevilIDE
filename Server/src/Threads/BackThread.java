package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BackThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedReader inFile;
	private File file;

	public BackThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public void run(){
		try {
			String versionsName="";
			String username="";
			String text="";
			System.out.println("客户端已连接");

			out=new PrintWriter(incoming.getOutputStream());
			username=in.readLine();
			versionsName=in.readLine();
			
			file=new File(PathHelper.getRootPath()+"Temp\\"+username);//存档地址
			
			if(file.list()==null||file.list().length==0){
				out.println(text);
				out.flush();
				out.close();
			}
			else{
				if(versionsName.equals("")){
					versionsName=file.list()[file.list().length-1];
					file=new File(PathHelper.getRootPath()+"Temp\\"+username+"\\"+versionsName);
					inFile=new BufferedReader(new FileReader(file));
					text=inFile.readLine();
					inFile.close();
					out.println(text+"/"+versionsName);
					out.flush();
					out.close();
				}
				else{
					for(int i=0;i<file.list().length;i++){
						//找出前一版本并跳转
						if(file.list()[i].equals(versionsName)){
							if(i==0){
								out.println(text);
								out.flush();
								out.close();
							}
							else{
								versionsName=file.list()[i-1];
								file=new File(PathHelper.getRootPath()+"Temp\\"+username+"\\"+versionsName);
								inFile=new BufferedReader(new FileReader(file));
								text=inFile.readLine();
								inFile.close();
								out.println(text+"/"+versionsName);
								out.flush();
								out.close();
							}
						break;
						}
					}
				}
			}
			

		} catch (IOException e) {
			out.println("服务器无法创建文件");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
}
