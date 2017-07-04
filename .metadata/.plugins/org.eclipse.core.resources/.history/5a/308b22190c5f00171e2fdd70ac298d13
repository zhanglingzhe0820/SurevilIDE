package Threads;

public class PathHelper {

	public static String getRootPath(){
		String[] s=PathHelper.class.getResource("").getPath().split("/");
		String path="";
		path=s[0];
		for(int i=1;i<s.length-3;i++){
			path=path+s[i]+"/";
		}
		return path;
	}
	
}