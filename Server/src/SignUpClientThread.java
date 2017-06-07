
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
		
		//初始化输入
		try {
			username=in.readLine();
			password=in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//初始化输出
		
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
			//初始化数据库相关
			Class.forName("org.sqlite.JDBC");//加载sqlite驱动
			connection=DriverManager.getConnection("jdbc:sqlite:user.db");//数据库连接
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
				File file=new File("L:\\javaHomework\\UserSpace\\"+username);//为用户分配文件保存空间
				file.mkdir();
				File fileForTemp=new File("L:\\javaHomework\\Temp\\"+username);//为用户分配文件保存临时空间
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
