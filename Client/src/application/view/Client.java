package application.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket clientSocket;
	private String code="";
	private String messageInput="";
	private String messageOutput="";
	private PrintWriter out;
	private BufferedReader in;
	private Function function;
	
	public Client(String code,String messageOutput,Function function){
		//�ͳ����������
		this.code=code;
		this.messageOutput=messageOutput;
		this.function=function;
		try {
			clientSocket=new Socket("127.0.0.1",10086);
			System.out.println("������������");
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String execute(){
		//ȥ����������еĻ��з�
		String[] tempStr=code.split("\n");
		code=tempStr[0];
		for(int i=1;i<tempStr.length;i++){
			code=code+tempStr[i];
		}
		
		//ȥ����������еĻ��з�
		tempStr=code.split("\r");
		code=tempStr[0];
		for(int i=1;i<tempStr.length;i++){
			code=code+tempStr[i];
		}
		
		out.println("Execute");
		out.println(code);
		out.println(messageOutput);
		out.flush();
		
		//�յ����
		try {
			//��ȡ���е���������
			String line="";
			while(((line=in.readLine())!=null)&&(line.length()!=0)){
				messageInput=messageInput+line;
			}
			out.flush();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messageInput;//TRY
	}
}