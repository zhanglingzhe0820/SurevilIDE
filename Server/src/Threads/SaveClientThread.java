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
			System.out.println("�ͻ���������");

			out=new PrintWriter(incoming.getOutputStream());
			language=in.readLine();
			username=in.readLine();
			text=in.readLine()+'\n';
			
			if(username.equals("δ��¼")){
				out.println("���ȵ�¼");
				out.flush();
				out.close();
			}
			
			else{
				if(language.equals("BrainFuck")){
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".bf");//����bf�浵��ַ
				}
				else if(language.equals("Ook")){
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".ook");//����ook�浵��ַ
				}
				else{
					file=new File(PathHelper.getRootPath()+"UserSpace\\"+username+"\\"+getNowTime()+".txt");//����txt�浵��ַ
				}
				file.createNewFile();
				outFile=new PrintWriter(file);
				outFile.println(text);
				outFile.flush();
				outFile.close();
				out.println("����ɹ�");
				out.flush();
				out.close();
			}

		} catch (IOException e) {
			out.println("�������޷������ļ�");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//�������ڸ�ʽ
		return (df.format(new Date()));//��ȡ��ǰϵͳʱ��
	}
}
