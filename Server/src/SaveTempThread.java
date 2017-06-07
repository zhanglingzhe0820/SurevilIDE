import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveTempThread extends Thread {
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private PrintWriter outFile;
	private File file;

	public SaveTempThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String text="";
			String username="";
			System.out.println("客户端已连接");

			out=new PrintWriter(incoming.getOutputStream());
			username=in.readLine();
			text=in.readLine()+'\n';
			
			file=new File("L:\\javaHomework\\Temp\\"+username+"\\"+(new Date()).getTime()+".txt");//存档地址
			file.createNewFile();
			outFile=new PrintWriter(file);
			outFile.println(text);
			outFile.flush();
			outFile.close();
			out.println("保存成功");
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("服务器无法创建文件");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
}
