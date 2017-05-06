import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread{
	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private Language language;
	private BrainFuckRunner BFRunner;
	private OokRunner OOKRunner;
	
	private enum Language{
		BF,
		OOK
	}
	
	public ClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.incoming=incoming;
		this.numberOfThread=numberOfThread;
		this.in=in;
	}
	
	//等做完文件保存格式时需要修改
	public void chooseLanguage(String fileForm) throws WrongFileFormException{
		switch(fileForm){
			case "bf":{
				language=Language.BF;
				break;
			}
			case "ook":{
				language=Language.OOK;
				break;
			}
			default:{
				throw new WrongFileFormException();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String text="";
			String messageInput="";
			System.out.println("客户端已连接");

			out=new PrintWriter(incoming.getOutputStream());
			text=in.readLine();
			messageInput=in.readLine()+'\n';
			try {
				chooseLanguage("bf");
				switch(language){
					case BF:{
						BFRunner=new BrainFuckRunner(text,messageInput);
						out.println(BFRunner.run());
						out.flush();
						out.close();
					}
					case OOK:{
						OOKRunner=new OokRunner(text,messageInput);
						OOKRunner.run();
					}
				}
			} catch (WrongFileFormException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't get incoming socket!");
			e.printStackTrace();
		}
	}
	
}
