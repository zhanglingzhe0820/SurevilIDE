package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileThread extends Thread {
	String path="";
	Controller controller;
	
	public FileThread(String path,Controller controller){
		this.path=path;
		this.controller=controller;
	}

	@Override
	public void run(){
		while(!new File(path).exists()){};//判断文件是否已被clone
		controller.getProgressBar().setProgress(1);//完成进度条
		controller.getProgressThread().stop();//关闭progressBar线程
		controller.getokButton().setDisable(false);
		//读取文件内容
		StringBuilder result=new StringBuilder();
		String line="";
		File file=new File(path);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			while((line=reader.readLine())!=null){
				result.append(line);
			}
			reader.close();
			controller.getCode().setText(new String(result));
			controller.getGit().clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
