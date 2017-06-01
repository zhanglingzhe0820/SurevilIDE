package trial;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class trail {
	public static void main(String[] args){
		String username="789";
		PrintWriter pw;
		
		File file=new File("L:\\javaHomework\\UserSpace\\"+username);//创建存档地址
		System.out.println(file.list()[0]);
	}
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
		return (df.format(new Date()));// new Date()为获取当前系统时间
	}
}
