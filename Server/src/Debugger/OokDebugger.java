package Debugger;

import Runner.BrainFuckRunner;

public class OokDebugger{
	private int index;
	private String text;
	private String messageInput;
	
	public OokDebugger(int index,String text, String messageInput){
		this.index=index;
		this.text=text;
		this.messageInput=messageInput;
	}
	
	public String ookToBf(String code){
		StringBuilder bfStr=new StringBuilder();
		code=code.replaceAll(" ", "");
		for(int i=0;i<code.length()/8-1;i++){
			switch(new String(code.substring(0+8*i, 8+8*i).toCharArray())){
				case "Ook.Ook?":{
					bfStr.append(">");
					break;
				}
				case "Ook?Ook.":{
					bfStr.append("<");
					break;
				}
				case "Ook.Ook.":{
					bfStr.append("+");
					break;
				}
				case "Ook!Ook!":{
					bfStr.append("-");
					break;
				}
				case "Ook!Ook.":{
					bfStr.append(".");
					break;
				}
				case "Ook.Ook!":{
					bfStr.append(",");
					break;
				}
				case "Ook!Ook?":{
					bfStr.append("[");
					break;
				}
				case "Ook?Ook!":{
					bfStr.append("]");
					break;
				}
				default:{
					return "ook wrong input";
				}
			}
		}
		return new String(bfStr);
	}
	
	public String executeCode(){
		if(this.ookToBf(text).equals("ook wrong input")){
			return "ook wrong input";
		}
		BrainFuckDebugger bfDebugger=new BrainFuckDebugger(index,this.ookToBf(text),messageInput);
		return bfDebugger.executeCode();
	}

}
