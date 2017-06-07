package application.view;

import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

public class RecordThread extends Thread{
	Controller controller;
	private Client request;
	private long time1=0;//第一次敲击键盘
	private long time2=0;//第二次敲击键盘
	
	public RecordThread(Controller controller){
		this.controller=controller;
	}

	public void run(){
		//创建原始版本
		request=new Client();
		controller.setOutputText(request.setUpSocket());//返回连接状态
		request.saveTemp(controller.getLoginStatus(),controller.getCodeText());
		//增加事件
		controller.getCode().setOnKeyTyped(new codeSaveHandler());
	}
	
	//保存临时文件
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
					controller.setOutputText(request.setUpSocket());//返回连接状态
					
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
