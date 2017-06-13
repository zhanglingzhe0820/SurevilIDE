package application.controller;

import java.util.Date;

import application.model.Client;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

public class RecordThread extends Thread{
	Controller controller;
	private Client request;
	private long time1=0;//��һ���û�����
	private long time2=0;//�ڶ����û�����
	
	public RecordThread(Controller controller){
		this.controller=controller;
	}

	public void run(){
		//����ԭʼ�汾
		request=new Client();
		controller.setOutputText(request.setUpSocket());//��������״̬
		request.saveTemp(controller.getLoginStatus(),controller.getCodeText());
		//�����¼�
		controller.getCode().setOnKeyTyped(new codeSaveHandler());
		while(true){
			if(controller.getLoginStatus().equals("δ��¼")){
				controller.getCode().setOnKeyTyped(new stopSaveHandler());;
			}
		}
	}
	
	//ֹͣ����
	private class stopSaveHandler implements EventHandler<KeyEvent> {  

		@Override
		public void handle(KeyEvent event) {
		}  
	} 
	
	//������ʱ�ļ�
	private class codeSaveHandler implements EventHandler<KeyEvent> {  

		@Override
		public void handle(KeyEvent event) {
			if(time1==0&&time2==0){
				time1=(new Date()).getTime();
			}
			else if(time1>time2){
				time2=(new Date()).getTime();
				if(time2-time1>=500){
					request=new Client();
					controller.setOutputText(request.setUpSocket());//��������״̬
					
					request.saveTemp(controller.getLoginStatus(),controller.getCodeText());
				}
				time1=time2;
			}
			else{
				time1=(new Date()).getTime();
			}
		}  
	} 
}