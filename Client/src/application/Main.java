package application;
	
import java.io.IOException;

import application.controller.Controller;
import application.controller.PathHelper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		//显示main界面
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RootScene.fxml"));
		try {
 			rootLayout=(BorderPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(rootLayout,800,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		((Controller) loader.getController()).loadStatus();
		
		//为语言选择栏添加语言种类
		((Controller) loader.getController()).getLanguageBox().getItems().add("BrainFuck");
		((Controller) loader.getController()).getLanguageBox().getItems().add("Ook");
		
		//设定Button图片来源
		ImageInput image1=new ImageInput();
		ImageInput image2=new ImageInput();
		ImageInput image3=new ImageInput();
		
		//登陆图片
		image1.setSource(new Image("file:"+Main.class.getResource("view/resources/images/login.png").getPath()));
		((Controller) loader.getController()).getLogButton().setEffect(image1);
		
		//回退图片
		image2.setSource(new Image("file:"+Main.class.getResource("view/resources/images/back.png").getPath()));
		((Controller) loader.getController()).getBackButton().setEffect(image2);
				
		//重做图片
		image3.setSource(new Image("file:"+Main.class.getResource("view/resources/images/move.png").getPath()));
		((Controller) loader.getController()).getMoveButton().setEffect(image3);
		
		//当关闭IDE时自动清除服务器端的临时文件
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event){
				((Controller) loader.getController()).clearTemp();
				((Controller) loader.getController()).saveStatus();
				System.exit(0);
			}
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return primaryStage;
	}
}
