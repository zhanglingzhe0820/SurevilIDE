package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MoveThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedReader inFile;
	private File file;

	public MoveThread(int numberOfThread,Socket incoming,BufferedReader in){
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
			
			file=new File("L:\\javaHomework\\Temp\\"+username);//存档地址
			
			if(file.list()==null||file.list().length==0){
				out.println("not_move");
				out.flush();
				out.close();
			}
			else{
				if(versionsName.equals("")){
					out.println("not_move");
					out.flush();
					out.close();
				}
				else{
					for(int i=0;i<file.list().length;i++){
						//找出后一版本并跳转
						if(file.list()[i].equals(versionsName)){
							if(i>file.list().length-1){
								out.println("not_move");
								out.flush();
								out.close();
							}
							else if(i==file.list().length-1){
								out.println("not_move_last");
								out.flush();
								out.close();
							}
							else{
								versionsName=file.list()[i+1];
								file=new File("L:\\javaHomework\\Temp\\"+username+"\\"+versionsName);
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
			// TODO Auto-generated catch block
			out.println("服务器无法创建文件");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
}
