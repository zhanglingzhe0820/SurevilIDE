package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClearAfterTempThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private File file;

	public ClearAfterTempThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public synchronized void run() {
		try {
			String username="";
			String versionsName="";
			String result="";
			System.out.println("客户端已连接");

			username=in.readLine();
			versionsName=in.readLine();
			
			file=new File(PathHelper.getRootPath()+"Temp\\"+username);//存档地址
			int fileLength=file.list().length;
			String[] fileList=file.list();
			
			//在输入一个字符后删除所有之后的存档
			for(int i=0;i<fileLength;i++){
				if(fileList[i].equals(versionsName)){
					for(int j=i;j<fileLength;j++){
						result=result+new File(file,fileList[j]);
						new File(file,fileList[j]).delete();
					}
					break;
				}
			}
			
			PrintWriter out=new PrintWriter(incoming.getOutputStream());
			out.println(result);
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
