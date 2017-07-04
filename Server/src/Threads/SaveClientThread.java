package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveClientThread extends Thread{
	
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private PrintWriter outFile;
	private File file;

	public SaveClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public synchronized void run() {
		try {
			String language="";
			String text="";
			String username="";
			System.out.println("客户端已连接");

			out=new PrintWriter(incoming.getOutputStream());
			language=in.readLine();
			username=in.readLine();
			text=in.readLine()+'\n';
			
			if(username.equals("未登录")){
				out.println("请先登录");
				out.flush();
				out.close();
			}
			
			else{
				if(language.equals("BrainFuck")){
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".bf");//创建bf存档地址
				}
				else if(language.equals("Ook")){
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".ook");//创建ook存档地址
				}
				else{
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".txt");//创建txt存档地址
				}
				file.createNewFile();
				outFile=new PrintWriter(file);
				outFile.println(text);
				outFile.flush();
				outFile.close();
				out.println("保存成功");
				out.flush();
				out.close();
			}

		} catch (IOException e) {
			out.println("服务器无法创建文件");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
		return (df.format(new Date()));//获取当前系统时间
	}
}
