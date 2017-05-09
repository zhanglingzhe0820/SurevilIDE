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
	
	public Client(Function function){
		this.function=function;
		
		try {
			clientSocket=new Socket("127.0.0.1",10086);
			System.out.println("服务器已连接");
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
	
	public String execute(String code,String messageOutput){
		//去掉输入代码中的换行符
		String[] tempStr=code.split("\n");
		code=tempStr[0];
		for(int i=1;i<tempStr.length;i++){
			code=code+tempStr[i];
		}
		
		//去掉输入代码中的换行符
		tempStr=code.split("\r");
		code=tempStr[0];
		for(int i=1;i<tempStr.length;i++){
			code=code+tempStr[i];
		}
		
		out.println("Execute");
		out.println(code);
		out.println(messageOutput);
		out.flush();
		
		//收到结果
		try {
			//读取流中的所有数据
			String line="";
			while(((line=in.readLine())!=null)&&(line.length()!=0)){
				messageInput=messageInput+line;
			}
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messageInput;//TRY
	}
	
	public String login(String username,String password){
		out.println("Login");
		out.println(username);
		out.println(password);
		out.flush();
		
		String result="";
		//收到结果
		try {
				result=in.readLine();	
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return result;//TRY
	}

	public String signUp(String username, String password) {
		out.println("SignUp");
		out.println(username);
		out.println(password);
		out.flush();
		
		String result="";
		//收到结果
		try {
				result=in.readLine();	
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return result;//TRY
	}
}
