package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController{
	
	private Controller controller;
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField passwordText;
	
	@FXML
	private Button login;
	
	@FXML
	private Button signUp;
	
	@FXML
	private Button logout;
	
	private Client request;
	private RecordThread recordThread;
	
	@FXML
	private void onLogin(ActionEvent event){
		controller.setClient();
		this.request=controller.request;
		String temp=request.login(username.getText(), passwordText.getText());//�õ����ص���Ϣ
		
		//��ʾ������
		if(temp.equals("wrong")){
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/WrongScene.fxml"));
			try {
				BorderPane loginLayout=(BorderPane) loader.load();
				Scene scene=new Scene(loginLayout,200,120);
				Stage newStage=new Stage();
				newStage.setScene(scene);
				newStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LoginSuccessfully.fxml"));
			try {
				BorderPane loginLayout=(BorderPane) loader.load();
				Scene scene=new Scene(loginLayout,200,120);
				Stage newStage=new Stage();
				newStage.setScene(scene);
				newStage.show();
				controller.setLoginStatus(username.getText());
				recordThread=new RecordThread(controller);
				recordThread.start();
				controller.getCode().setOnKeyPressed(new codeEventHandler());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		
	}
	
	@FXML
	private void onLogout(ActionEvent event){
		controller.setLoginStatus("δ��¼");
	}
	
	@FXML
	private void onSignUp(ActionEvent event){
		controller.setClient();
		this.request=controller.request;
		String temp=request.signUp(username.getText(), passwordText.getText());//�õ����ص���Ϣ
		
		//��ʾ������
		if(temp.equals("wrong")){
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AlreadyHasUsername.fxml"));
			try {
				BorderPane loginLayout=(BorderPane) loader.load();
				Scene scene=new Scene(loginLayout,200,120);
				Stage newStage=new Stage();
				newStage.setScene(scene);
				newStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/SignUpSuccessfully.fxml"));
			try {
				BorderPane loginLayout=(BorderPane) loader.load();
				Scene scene=new Scene(loginLayout,200,120);
				Stage newStage=new Stage();
				newStage.setScene(scene);
				newStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//�رձ�����ʱ�ļ����߳�
	@Override
	protected void finalize(){
		recordThread.interrupt();
	}

	//ʵ��Controller��loginContoller֮���ͨ��
	public void init(Controller controller) {
		// TODO Auto-generated method stub
		this.controller=controller;
	}
	
	//ɾȥ��ʱ�ļ�����Ҫ�õ���һ����
	private class codeEventHandler implements EventHandler<KeyEvent> {  
		private Client request;
		@Override
		public void handle(KeyEvent event) {
			if(!controller.getVersionsName().equals("")){
				controller.setClient();
				this.request=controller.request;
				request.clearAfterTemp(controller.getLoginStatus(),controller.getVersionsName());
				controller.setVersionsName("");
			}
		}  
	}

}