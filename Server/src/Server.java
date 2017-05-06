import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Server {
	private ServerSocket serverSocket;
	private Socket incoming;
	private int count=0;
	private BufferedReader in;
	private Function function;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
		server.run();
	}
	
	
	public Server(){
		try {
			serverSocket=new ServerSocket(10086);
			System.out.println("�������ѽ���");
		} catch (IOException e) {
			System.out.println("�˿��ѱ�ռ��");
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				incoming=serverSocket.accept();
				in=new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				chooseFunction(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//���ݴ���ĵ�һ���ַ����ж�����Ĺ�
	public void chooseFunction(String info){
		switch(info){
			case "New":{
				function=Function.New;
				break;
			}
			case "Save":{
				function=Function.Save;
				break;
			}
			case "Open":{
				function=Function.Open;
				break;
			}
			case "Exit":{
				function=Function.Exit;
				break;
			}
			case "Execute":{
				function=Function.Execute;
				count++;
				(new ClientThread(count,incoming,in)).start();
				break;
			}
			case "Version":{
				function=Function.Version;
				break;
			}
			case "Commit":{
				function=Function.Commit;
				break;
			}
			case "Push":{
				function=Function.Push;
			}
			case "CommitAndPush":{
				function=Function.CommitAndPush;
			}
		}
	}

}
