package application;
	
import java.io.IOException;

import application.view.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		primaryStage.setTitle("Surevil");
		
		initRootLayout();
		
	}
	
	public void initRootLayout(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RootScene.fxml"));
		try {
			rootLayout=(BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(rootLayout,600,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		// TODO Auto-generated method stub
		return primaryStage;
	}
}
