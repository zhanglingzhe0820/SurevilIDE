package Threads.Runner;

import Exceptions.WrongFileFormException;

public class Runner {
	private String text;
	private String inputMessage;
	OokRunner ookRunner;
	BrainFuckRunner bfRunner;
	
	public Runner(String text,String inputMessage){
		this.text=text;
		this.inputMessage=inputMessage;
		ookRunner=new OokRunner(text,inputMessage);
		bfRunner=new BrainFuckRunner(text,inputMessage);
	}
	
	public String executeCode() throws WrongFileFormException{
		if(text.substring(0,3).toLowerCase().equals("ook")){
			return ookRunner.executeCode();
		}
		else if(text.substring(0,1).equals("+")||text.substring(0,1).equals("-")||text.substring(0,1).equals(",")||text.substring(0,1).equals(".")||text.substring(0,1).equals("<")||text.substring(0,1).equals(">")||text.substring(0,1).equals("[")||text.substring(0,1).equals("]")){
			return bfRunner.executeCode();
		}
		else{
			throw new WrongFileFormException();
		}
	}
}
