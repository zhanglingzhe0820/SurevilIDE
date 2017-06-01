package application.view;

public class RecordThread extends Thread{
	Controller controller;
	
	public RecordThread(Controller controller){
		this.controller=controller;
	}

	public void run(){
		for(;;){
			//与服务器连接不断传送codeText的数据
		}
	}
}
