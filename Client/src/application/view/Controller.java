package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller{
	
	@FXML
	private LoginController loginController;
	@FXML
	private TextArea codeText;
	@FXML
	private TextField gitText;
	@FXML
	private TextArea inputText;
	@FXML
	private TextArea outputText;
	@FXML
	private Button loginButton;
	@FXML
	private Button backButton;
	@FXML
	private Button moveButton;
	@FXML
	private Label loginStatus;	 
	
	//鼠标进入各种图标使其变亮
	@FXML
	private void loginButtonEntered(MouseEvent event){
		loginButton.setOpacity(1);
	}
	
	@FXML
	private void loginButtonExited(MouseEvent event){
		loginButton.setOpacity(0.5);
	}
	
	@FXML
	private void backButtonEntered(MouseEvent event){
		backButton.setOpacity(1);
	}
	
	@FXML
	private void backButtonExited(MouseEvent event){
		backButton.setOpacity(0.5);
	}
	
	@FXML
	private void moveButtonEntered(MouseEvent event){
		moveButton.setOpacity(1);
	}
	
	@FXML
	private void moveButtonExited(MouseEvent event){
		moveButton.setOpacity(0.5);
	}
	//美化界面结束
	
	@FXML
	private void excute(ActionEvent event){
		Client request=new Client(Function.Execute);
		outputText.setText(request.execute(codeText.getText(),inputText.getText()));
	}
	
	@FXML
	private void exit(ActionEvent event){
		System.exit(0);
	}
	
	@FXML
	private void focusOnCommit(ActionEvent event){
		gitText.requestFocus();
	}
	
	//弹出登录界面
	@FXML
	private void login(ActionEvent event){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/LoginScene.fxml"));
		try {
			BorderPane loginLayout=(BorderPane) loader.load();
			Scene scene=new Scene(loginLayout,300,180);
			Stage newStage=new Stage();
			newStage.setScene(scene);
			newStage.show();
			
			//实现Controller和loginContoller之间的通信
			loginController=loader.getController();
			loginController.init(this);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLoginStatus(String username){
		loginStatus.setText(username);
	}

}
