package Threads.Debugger;

import Exceptions.WrongFileFormException;

public class Debugger {
	private int index;
	private String text;
	private String inputMessage;
	OokDebugger ookDebugger;
	BrainFuckDebugger bfDebugger;
	
	public Debugger(int index,String text,String inputMessage){
		this.index=index;
		this.text=text;
		this.inputMessage=inputMessage;
		ookDebugger=new OokDebugger(index,text,inputMessage);
		bfDebugger=new BrainFuckDebugger(index,text,inputMessage);
	}
	
	public String executeCode() throws WrongFileFormException{
		if(text.substring(0,3).toLowerCase().equals("ook")){
			return ookDebugger.executeCode();
		}
		else if(text.substring(0,1).equals("+")||text.substring(0,1).equals("-")||text.substring(0,1).equals(",")||text.substring(0,1).equals(".")||text.substring(0,1).equals("<")||text.substring(0,1).equals(">")||text.substring(0,1).equals("[")||text.substring(0,1).equals("]")){
			return bfDebugger.executeCode();
		}
		else{
			throw new WrongFileFormException();
		}
	}
}
