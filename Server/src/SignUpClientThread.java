
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

public class SignUpClientThread extends Thread{

	private int numberOfThread;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private String username;
	private String password;
	private Connection connection;
	private Statement state;

	public SignUpClientThread(int numberOfThread,Socket incoming,BufferedReader in){
		this.numberOfThread=numberOfThread;
		this.incoming=incoming;
		this.in=in;
		
		//��ʼ������
		try {
			username=in.readLine();
			password=in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��ʼ�����
		
		try {
			out=new PrintWriter(incoming.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public synchronized void run(){
		try {
			//��ʼ�����ݿ����
			Class.forName("org.sqlite.JDBC");//����sqlite����
			connection=DriverManager.getConnection("jdbc:sqlite:user.db");//���ݿ�����
			state=connection.createStatement();
			
			String sql="SELECT * FROM USER WHERE USERNAME=="+"\'"+username+"\'"+";";
			ResultSet result=state.executeQuery(sql);
			if(result!=null&&result.getString("USERNAME").length()!=0){
				out.println("wrong");
			}
			else{
				out.println("success");
				sql="INSERT INTO USER(USERNAME,PASSWORD) VALUES("+"\'"+username+"\'"+","+"\'"+password+"\'"+");";
				state.executeUpdate(sql);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("success");
			try {
				String sql="INSERT INTO USER(USERNAME,PASSWORD) VALUES("+"\'"+username+"\'"+","+"\'"+password+"\'"+");";
				state.executeUpdate(sql);
				File file=new File("L:\\javaHomework\\UserSpace\\"+username);//Ϊ�û������ļ�����ռ�
				file.mkdir();
				File fileForTemp=new File("L:\\javaHomework\\Temp\\"+username);//Ϊ�û������ļ�������ʱ�ռ�
				fileForTemp.mkdir();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				out.flush();
				out.close();
				state.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
