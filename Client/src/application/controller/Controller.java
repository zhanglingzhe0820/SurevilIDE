package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import application.Main;
import application.model.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	@FXML
	private Menu versionMenu;
	
	public Client request;
	
	private Stage stage;
	private ProgressBar progressBar;
	private Button okButton;
	private String finalPath;
	private ProgressThread progressThread;
	private ArrayList<String> versions;
	private ArrayList<MenuItem> versionItems;
	private String versionsName="";
	
	//���������ͼ��ʹ�����
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
	//�����������
	
	//����������������ӣ�������״̬
	public void setClient(){
		request=new Client();
		outputText.setText(request.setUpSocket());
	}
	
	@FXML
	private void newText(ActionEvent event){
		clearTemp();
		codeText.clear();
		gitText.clear();
		inputText.clear();
		outputText.clear();
	}
	
	@FXML
	private void excute(ActionEvent event){
		setClient();
		outputText.clear();
		outputText.setText(request.execute(codeText.getText(),inputText.getText()));
	}
	
	@FXML
	private void exit(ActionEvent event){
		clearTemp();
		System.exit(0);
	}
	
	@FXML
	private void focusOnCommit(ActionEvent event){
		gitText.requestFocus();
	}
	
	//������¼����
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
			
			//ʵ��Controller��loginContoller֮���ͨ��
			loginController=loader.getController();
			loginController.init(this);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBackClicked(ActionEvent event){
		if(versionsName.equals("")){
			setClient();
			request.saveTemp(this.getLoginStatus(),this.getCodeText());
		}
		
		setClient();
		String tempText=request.back(loginStatus.getText(),versionsName);
		if(!tempText.equals("")){
			String[] group=tempText.split("/");
			versionsName=group[1];
			codeText.setText(group[0]);
		}
		
		
	}
	
	@FXML
	private void onMoveClicked(ActionEvent event){
		setClient();
		String tempText=request.move(loginStatus.getText(),versionsName);
		if(!tempText.equals("not_move")&&!tempText.equals("not_move_last")){
			String[] group=tempText.split("/");
			versionsName=group[1];
			codeText.setText(group[0]);
		}
		else if(tempText.equals("not_move_last")){
			versionsName="";
		}
	}
	
	@FXML
	private void onCloneClicked(ActionEvent event){
		try {//https://github.com/zhanglingzhe0820/BFtrial.git/123.bf
			//���ļ���github clone����
			StringBuilder gitPath=new StringBuilder();
			gitPath.append("https://github.com/");
			gitPath.append(gitText.getText().split("/")[3]+"/");
			gitPath.append(gitText.getText().split("/")[4]);
			Process process=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe git clone "+gitPath);
			showProgressBar(5000);//��ʾ���ȱ�
			String[] s=this.getClass().getResource("").getPath().split("/");
			String path="";
			path=s[0];
			for(int i=1;i<s.length-3;i++){
				path=path+s[i]+"/";
			}
			path=path+gitText.getText().split("/")[4].split("\\.")[0]+"/"+gitText.getText().split("/")[5];
			
			//���ж��ļ��߳�
			String finalPath=path;
			new FileThread(finalPath,this).start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onCommitClicked(ActionEvent event){
		
		try {
			File file=new File(finalPath);
			PrintWriter writer=new PrintWriter(file);
			writer.println(codeText.getText());
			writer.flush();
			writer.close();
			
			Process process1=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe add * -f ");
			Process process2=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe git commit -m "+gitText.getText());
		} catch (FileNotFoundException e) {
			outputText.setText("������ļ�·�����޷�commit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void onCommitAndPushClicked(ActionEvent event){
		
		try {
			File file=new File(finalPath);
			PrintWriter writer=new PrintWriter(file);
			writer.println(codeText.getText());
			writer.flush();
			writer.close();
			
			Process process1=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe git add * -f ");
			Process process2=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe git commit -m "+gitText.getText());
			Process process3=Runtime.getRuntime().exec("cmd /c L:/Git/git-cmd.exe git push origin master");
		} catch (FileNotFoundException e) {
			outputText.setText("������ļ�·�����޷�commit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Button getokButton(){
		return okButton;
	}
	
	public TextField getGit(){
		return gitText;
	}
	
	public ProgressBar getProgressBar(){
		return progressBar;
	}
	
	public ProgressThread getProgressThread(){
		return progressThread;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public TextArea getCode(){
		return codeText;
	}
	
	public void setVersionsName(String name){
		versionsName=name;
	}
	
	public String getVersionsName(){
		return versionsName;
	}
	
	public String getCodeText(){
		return codeText.getText();
	}
	
	public String getLoginStatus(){
		return loginStatus.getText();
	}
	
	public void setOutputText(String status){
		outputText.clear();
		outputText.setText(status);
	}
	
	public void setLoginStatus(String username){
		setClient();
		versions=request.getVersions(username);
		loginStatus.setText(username);
		
		//��ȡ��ʷ�汾��Ϣ
		readVersions();
	}
	
	//�����ϴε�״̬
	public void loadStatus(){
		try {
			//���ش�����Ϣ
			String line="";
			BufferedReader codeStatusReader=new BufferedReader(new FileReader(new File("L:\\javaHomework\\Client\\Status\\CodeStatus.txt")));
			if((line=codeStatusReader.readLine())!=null){
				codeText.setText(line);
			}
			codeStatusReader.close();
			
			//���ص�¼��Ϣ
			BufferedReader loginStatusReader=new BufferedReader(new FileReader(new File("L:\\javaHomework\\Client\\Status\\LoginStatus.txt")));
			if((line=loginStatusReader.readLine())!=null){
				loginStatus.setText(line);
			}
			loginStatusReader.close();
			
			//��ʼ������
			if(!loginStatus.getText().equals("δ��¼")){
				//��ʼ��������ʱ�ļ�
				RecordThread recordThread=new RecordThread(this);
				recordThread.start();
				codeText.setOnKeyPressed(new codeEventHandler());
				
				//��ʼ���汾����ϵͳ
				readVersions();
			}
		} catch (FileNotFoundException e) {
			outputText.setText("�޷����عر�ʱ״̬");
		} catch (IOException e) {
			outputText.setText("�ļ������������");
		}
		
	}
	
	//�ر�ʱ����IDE��ǰ��״̬
	public void saveStatus(){
		try {
			//���������Ϣ
			PrintWriter codeStatusWriter=new PrintWriter(new File("L:\\javaHomework\\Client\\Status\\CodeStatus.txt"));
			codeStatusWriter.print(codeText.getText());
			codeStatusWriter.flush();
			codeStatusWriter.close();
			
			//�����¼��Ϣ
			PrintWriter loginStatusWriter=new PrintWriter(new File("L:\\javaHomework\\Client\\Status\\LoginStatus.txt"));
			loginStatusWriter.print(loginStatus.getText());
			loginStatusWriter.flush();
			loginStatusWriter.close();
		} catch (FileNotFoundException e) {
			outputText.clear();
			outputText.setText("�޷����浱ǰ״̬");
		}
	}
	
	//�ر�ʱ�����ʱ�ļ�
	public void clearTemp(){
		setClient();
		request.clearTemp(loginStatus.getText());
	}
	
	public void save(){
		setClient();
		String result=request.save(loginStatus.getText(),codeText.getText());
		outputText.setText(result);//ʹ���������Ϊ�����ж��ش��������أ������ӿ��Ը��������㷵��ֵ�޸ĵĶ�����
		
		//��ȡ��ʷ�汾��Ϣ
		readVersions();
	}
	
	//��ȡ��ʷ�汾��Ϣ
	private void readVersions(){
		setClient();
		versionItems=new ArrayList<MenuItem>();
		versions=request.getVersions(loginStatus.getText());
		if(versions.get(0).equals("cannot read the incoming message")){
			outputText.setText("cannot read the incoming message");
		}
		else if(versions.get(0).equals("None")){
			versionItems.add(new MenuItem("None"));
		}
		else{
			versions.remove(0);
			for(String s: versions){
				MenuItem newItem=new MenuItem(s);
				newItem.setOnAction(new itemEventHandler());
				versionItems.add(newItem);
			}
		}
		versionMenu.getItems().clear();
		versionMenu.getItems().addAll(versionItems);
		versionItems=new ArrayList<MenuItem>();
	}
	
	//�ر�stage
	private class progressEventHandler implements EventHandler<ActionEvent> {  
		  
	    @Override  
	    public void handle(ActionEvent event) {  
	        stage.close();
	    }  
	} 
	
	//ȡ���汾������
	private class itemEventHandler implements EventHandler<ActionEvent> {  
		  
	    @Override  
	    public void handle(ActionEvent event) {  
	        String fileName=((MenuItem)event.getSource()).getText();//�õ��汾��
	        setClient();
			String result=request.getFile(loginStatus.getText(),fileName);
			codeText.setText(result);
	    }  
	} 
	
	//ɾȥ��ʱ�ļ�����Ҫ�õ���һ����
	private class codeEventHandler implements EventHandler<KeyEvent> {  
		@Override
		public void handle(KeyEvent event) {
			if(!versionsName.equals("")){
				setClient();
				request.clearAfterTemp(loginStatus.getText(),versionsName);
				versionsName="";
			}
		}  
	}
		
	private void showProgressBar(int time) throws InterruptedException, IOException{
		stage=new Stage();
		Platform.setImplicitExit(false);
	    BorderPane progressPane = new BorderPane();
	    Scene scene = new Scene(progressPane, 200, 120, Color.DIMGRAY);
	    
	    VBox vbox=new VBox();
	    vbox.setSpacing(10);
	    vbox.setAlignment(Pos.CENTER);
	    progressBar = new ProgressBar(0);
	    okButton=new Button("OK");
	    okButton.setDisable(true);
	    okButton.setOnAction(new progressEventHandler());
	    vbox.getChildren().addAll(progressBar,okButton);
	    progressPane.setCenter(vbox);
	        
	    //������������ƶ�
	    progressBar.setProgress(0);
	    progressThread=new ProgressThread(time);
	    progressThread.start();
	    stage.setScene(scene);
	    stage.show();
	}
	
	class ProgressThread extends Thread{
		int time=2000;
		
		public ProgressThread(int time){
			this.time=time;
		}
		@Override
		public void run(){
			for (int i = 0; i < 100; i++) {
	   		 	try {
	   		 		progressBar.setProgress(i/100.0);
					Thread.sleep(time/100);
				} catch (InterruptedException e) {}
	        }
		}
	}
}