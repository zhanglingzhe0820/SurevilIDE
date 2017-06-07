package application.view;

import java.util.ArrayList;

public interface FunctionClient {
	String setUpSocket();
	String execute(String code,String messageOutput);
	String login(String username,String password);
	String signUp(String username, String password);
	String save(String username,String code);
	ArrayList<String> getVersions(String username);
	String getFile(String username,String fileName);
	String saveTemp(String username, String code);
	String back(String username,String versionsName);
	String move(String username,String versionsName);
	void clearTemp(String username);
	void clearAfterTemp(String username, String versionsName);
}

/*New,
	Save,
	Open,
	Exit,
	Execute,
	Version,
	Commit,
	Push,
	CommitAndPush,
	Login,
	SignUp*/
