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
		
		File file=new File("L:\\javaHomework\\UserSpace\\"+username);//�����浵��ַ
		System.out.println(file.list()[0]);
	}
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//�������ڸ�ʽ
		return (df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
}
