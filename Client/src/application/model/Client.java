package application.model;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements FunctionClient{
	private Socket clientSocket;
	private String messageInput="";
	private String messageOutput="";
	private PrintWriter out;
	private BufferedReader in;
	
	public String setUpSocket(){
		try {
			clientSocket=new Socket("127.0.0.1",10086);
			System.out.println("服务器已连接");
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			return "成功连接服务器";
		} catch (UnknownHostException e) {
			return "服务器IP输入错误";
		} catch (IOException e) {
			return "无法连接服务器";
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
			messageInput="cannot read the incoming message";
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
				result="cannot read the incoming message";
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
				result="cannot read the incoming message";
				e.printStackTrace();
			}
				
			return result;//TRY
	}
	
	public String save(String language,String username,String code){
		out.println("Save");
		out.println(language);
		out.println(username);
		out.println(code);
		out.flush();
		
		String result="";
		//收到结果
		try {
			result=in.readLine();	
		} catch (IOException e) {
				// TODO Auto-generated catch block
			result="cannot read the incoming message";
			e.printStackTrace();
		}
			
		return result;//TRY
	}
	
	public ArrayList<String> getVersions(String username){
		out.println("GetVersions");
		out.println(username);
		out.flush();
		
		ArrayList<String> result=new ArrayList<String>();
		//收到结果
		try {
			String temp=in.readLine();
			if(!temp.contains("/")){
				result.add(temp);
				return result;
			}
			for(String s: temp.split("/")){
				result.add(s);
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
			result.add("cannot read the incoming message");
			e.printStackTrace();
		}
			
		return result;//TRY
	}

	@Override
	public String getFile(String username, String fileName) {
		out.println("GetFile");
		out.println(username);
		out.println(fileName);
		out.flush();
		
		String result="";
		//收到结果
		try {
			result=in.readLine();	
		} catch (IOException e) {
				// TODO Auto-generated catch block
			result="cannot read the incoming message";
			e.printStackTrace();
		}
			
		return result;//TRY
	
	}

	@Override
	public String saveTemp(String username,String code) {
		out.println("SaveTemp");
		out.println(username);
		out.println(code);
		out.flush();
		
		String result="";
		//收到结果
		try {
			result=in.readLine();	
		} catch (IOException e) {
				// TODO Auto-generated catch block
			result="cannot read the incoming message";
			e.printStackTrace();
		}
			
		return result;//TRY
	}

	@Override
	public String back(String username,String versionsName) {
		out.println("Back");
		out.println(username);
		out.println(versionsName);
		out.flush();
		
		String result="";
		//收到结果
		try{
			result=in.readLine();
		}catch(IOException e){
			result="cannot read the incoming message";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void clearTemp(String username) {
		out.println("ClearTemp");
		out.println(username);
		out.flush();
	}

	@Override
	public void clearAfterTemp(String username, String versionsName) {
		out.println("RemoveAfter");
		out.println(username);
		out.println(versionsName);
		out.flush();
	}

	@Override
	public String move(String username, String versionsName) {
		out.println("Move");
		out.println(username);
		out.println(versionsName);
		out.flush();
		
		String result="";
		//收到结果
		try{
			result=in.readLine();
		}catch(IOException e){
			result="cannot read the incoming message";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String stepIn(int index, String code,String input) {
		out.println("StepIn");
		out.println(index);
		out.println(code);
		out.println(input);
		out.flush();
		
		String result="";
		//收到结果
		try{
			result=in.readLine();
		}catch(IOException e){
			result="cannot read the incoming message";
			e.printStackTrace();
		}
		return result;
	}
}
