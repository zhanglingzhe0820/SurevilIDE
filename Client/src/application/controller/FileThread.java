package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Platform;

public class FileThread extends Thread {
	String path="";
	Controller controller;
	
	public FileThread(String path,Controller controller){
		this.path=path;
		this.controller=controller;
	}

	@Override
	public void run(){
		while(!new File(path).exists()){};//�ж��ļ��Ƿ��ѱ�clone
		controller.getProgressBar().setProgress(1);//��ɽ�����
		controller.getProgressThread().stop();//�ر�progressBar�߳�
		controller.getokButton().setDisable(false);
		//��ȡ�ļ�����
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}