package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import Exceptions.WrongFileFormException;

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
		// TODO Auto-generated method stub
		try {
			String text="";
			String username="";
			System.out.println("�ͻ���������");

			out=new PrintWriter(incoming.getOutputStream());
			username=in.readLine();
			text=in.readLine()+'\n';
			
			if(username.equals("δ��¼")){
				out.println("���ȵ�¼");
				out.flush();
				out.close();
			}
			
			else{
				if(text.equals("\n")){
					file=new File("L:\\javaHomework\\UserSpace\\"+username+"\\"+getNowTime()+".txt");//�����浵��ַ
				}
				else if(text.substring(0,1).equals("+")||text.substring(0,1).equals("-")||text.substring(0,1).equals(",")||text.substring(0,1).equals(".")||text.substring(0,1).equals("<")||text.substring(0,1).equals(">")||text.substring(0,1).equals("[")||text.substring(0,1).equals("]")){
					file=new File("L:\\javaHomework\\UserSpace\\"+username+"\\"+getNowTime()+".bf");//�����浵��ַ
				}
				else if(text.substring(0,3).toLowerCase().equals("ook")){
					file=new File("L:\\javaHomework\\UserSpace\\"+username+"\\"+getNowTime()+".ook");//�����浵��ַ
				}
				else{
					file=new File("L:\\javaHomework\\UserSpace\\"+username+"\\"+getNowTime()+".txt");//�����浵��ַ
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
			// TODO Auto-generated catch block
			out.println("�������޷������ļ�");
			out.flush();
			out.close();
			e.printStackTrace();
		}
	}
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//�������ڸ�ʽ
		return (df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
}