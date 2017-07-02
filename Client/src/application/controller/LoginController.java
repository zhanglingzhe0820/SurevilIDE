package application.controller;

import java.io.IOException;
import application.Main;
import application.model.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
		String temp=request.login(username.getText(), DigestMD5.getMD5Result(passwordText.getText()));//得到返回的信息
		
		//显示弹出框
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
					e.printStackTrace();
				}
		
		}
		
	}
	
	@FXML
	private void onLogout(ActionEvent event){
		controller.setLoginStatus("未登录");
	}
	
	@FXML
	private void onSignUp(ActionEvent event){
		controller.setClient();
		this.request=controller.request;
		String temp=request.signUp(username.getText(), DigestMD5.getMD5Result(passwordText.getText()));//得到返回的信息
		
		//显示弹出框
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
				e.printStackTrace();
			}
		}
	}
	
	//关闭保存临时文件的线程
	@Override
	protected void finalize(){
		recordThread.interrupt();
	}

	//实现Controller和loginContoller之间的通信
	public void init(Controller controller) {
		this.controller=controller;
	}
	
	//删去临时文件不需要用到的一部分
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
