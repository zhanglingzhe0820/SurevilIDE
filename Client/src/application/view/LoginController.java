package application.view;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button login;
	
	@FXML
	private Button signUp;
	
	@FXML
	private void onLogin(ActionEvent event){
		Client request=new Client(Function.Login);
		String temp=request.login(username.getText(), password.getText());//得到返回的信息
		
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@FXML
	private void onSignUp(ActionEvent event){
		Client request=new Client(Function.SignUp);
		String temp=request.signUp(username.getText(), password.getText());//得到返回的信息
		
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
}
