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
		// TODO Auto-generated method stub
		try {
			String username="";
			String versionsName="";
			String result="";
			System.out.println("�ͻ���������");

			username=in.readLine();
			versionsName=in.readLine();
			
			file=new File("L:\\javaHomework\\Temp\\"+username);//�浵��ַ
			int fileLength=file.list().length;
			String[] fileList=file.list();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}