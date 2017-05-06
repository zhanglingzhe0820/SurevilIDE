package application.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private TextArea codeText;
	@FXML
	private TextField gitText;
	@FXML
	private TextArea inputText;
	@FXML
	private TextArea outputText;
	
	@FXML
	private void excute(ActionEvent event){
		Client request=new Client(codeText.getText(),inputText.getText(),Function.Execute);
		outputText.setText(request.execute());
	}
}
