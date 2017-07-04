package Threads;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginClientThread extends Thread{

	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private Connection connection;
	private Statement state;
	private String username;
	private String password;
	
	//��ʼ����½�߳�
	public LoginClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.numberOfThread=numberOfThread;
		this.incoming=incoming;
		this.in=in;
		
		//��ʼ�������
		try {
			out=new PrintWriter(incoming.getOutputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//�õ�������û���������
		try {
			username=in.readLine();
			password=in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public synchronized void run(){
		try {
			//��ʼ�����ݿ�
			Class.forName("org.sqlite.JDBC");//����sqlite����
			connection=DriverManager.getConnection("jdbc:sqlite:user.db");//���ݿ�����
			state=connection.createStatement();
			
			String sql="SELECT * FROM USER WHERE USERNAME=="+"\'"+username+"\'"+" AND PASSWORD=="+"\'"+password+"\'"+";";
			ResultSet result=state.executeQuery(sql);
			if(result!=null&&result.getString("USERNAME").length()!=0){
				if(!new File(PathHelper.getRootPath()+"Temp\\"+username).exists()){
					new File(PathHelper.getRootPath()+"Temp\\"+username).mkdir();
				}
				out.println("success");
			}
			else{
				out.println("wrong");
			}
			
		} catch (SQLException e) {
			out.println("wrong");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.flush();
			out.close();
			try {
				state.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
