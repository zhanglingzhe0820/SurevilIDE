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
