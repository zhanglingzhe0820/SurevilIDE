package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import Threads.BackThread;
import Threads.ClearAfterTempThread;
import Threads.ClearTempThread;
import Threads.ExecuteClientThread;
import Threads.GetFileClientThread;
import Threads.GetVersionsClientThread;
import Threads.LoginClientThread;
import Threads.MoveThread;
import Threads.SaveClientThread;
import Threads.SaveTempThread;
import Threads.SignUpClientThread;
import Threads.StepInThread;

public class Server {
	private ServerSocket serverSocket;
	private int count=0;
	
	public static void main(String[] args) {
		Server server=new Server();
		server.run();
	}
	
	
	public Server(){
		try {
			serverSocket=new ServerSocket(10086);
			System.out.println("服务器已建立");
		} catch (IOException e) {
			System.out.println("端口已被占用");
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				Socket incoming=serverSocket.accept();
				BufferedReader in=new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				chooseFunction(incoming,in.readLine(),in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//根据传输的第一条字符串判断请求的功能
	public void chooseFunction(Socket incoming,String info,BufferedReader in){
		switch(info){
			case "Save":{
				count++;
				(new SaveClientThread(count,incoming,in)).start();
				break;
			}
			case "Execute":{
				count++;
				(new ExecuteClientThread(count,incoming,in)).start();
				break;
			}
			case "Login":{
				count++;
				(new LoginClientThread(count,incoming,in)).start();
				break;
			}
			case "SignUp":{
				count++;
				(new SignUpClientThread(count,incoming,in)).start();
				break;
			}
			case "GetVersions":{
				count++;
				(new GetVersionsClientThread(count,incoming,in)).start();
				break;
			}
			case "GetFile":{
				count++;
				(new GetFileClientThread(count,incoming,in)).start();
				break;
			}
			case "SaveTemp":{
				count++;
				(new SaveTempThread(count,incoming,in)).start();
				break;
			}
			case "Back":{
				count++;
				(new BackThread(count,incoming,in)).start();
				break;
			}
			case "ClearTemp":{
				count++;
				(new ClearTempThread(count,incoming,in)).start();
				break;
			}
			case "RemoveAfter":{
				count++;
				(new ClearAfterTempThread(count,incoming,in)).start();
				break;
			}
			case "Move":{
				count++;
				(new MoveThread(count,incoming,in)).start();
				break;
			}
			case "StepIn":{
				count++;
				(new StepInThread(count,incoming,in)).start();
				break;
			}
		}
	}

}
