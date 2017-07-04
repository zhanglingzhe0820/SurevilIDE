package Threads.Debugger;

import Exceptions.NotEqualLoopException;
import Exceptions.WrongInputException;

public class BrainFuckDebugger{
	
	private int index;
	private String text;
	private byte[] space;
	private int pointer;
	private int textPointer;
	private String messageInput;
	private String messageOutput="";
	private int InputCount=0;//��¼��Ҫ��ȡ�ڼ��������ַ�

	//��ʼ��
	public BrainFuckDebugger(int index,String text,String messageInput){
		//����������пո���ȥ���ո�
		if(text.contains(" ")){
			String[] tempStr=text.split(" ");
			String resultText="";
			for(int i=0;i<tempStr.length;i++){
				resultText=resultText+tempStr[i];
			}
			text=resultText;
		}
		
		this.index=index;
		this.text=text;
		this.messageInput=messageInput;
		space=new byte[256];
		pointer=0;
		textPointer=0;
		
		for(int i=0;i<256;i++){
			space[i]=0;
		}
	}
	
	//��ʼ����
	public String executeCode(){
		int loopStartCount=0;
		int loopEndCount=0;
		for(char c:text.toCharArray()){
			if(c=='['){
				loopStartCount++;
			}
			else if(c==']'){
				loopEndCount++;
			}
		}
		if(loopStartCount!=loopEndCount){
			return "Not Equal Loop";
		}
		while(textPointer<text.length()){
			try {
				chooseFunction(text.charAt(textPointer));
				textPointer++;
			} catch (WrongInputException e) {
				return "WRONG INPUT";
			}catch(NotEqualLoopException e){
				return "Not Equal Loop";
			}
		}
		return messageOutput;
		
	}
	
	//�����ַ���ѡ������
	public void chooseFunction(char c) throws WrongInputException, NotEqualLoopException{
		switch(c){
			case '>':{
				pointerPlus();
				break;
			}
			case '<':{
				pointerDec();
				break;
			}
			case '+':{
				valuePlus();
				break;
			}
			case '-':{
				valueDec();
				break;
			}
			case '.':{
				valueOutput();
				break;
			}
			case ',':{
				valueInput();
				break;
			}
			case '[':{
				loopStart();
				break;
			}
			case ']':{
				loopEnd();
				break;
			}
			default:{
				throw new WrongInputException();
			}
		}
	}

	private void loopEnd() throws NotEqualLoopException {
		if(space[pointer]!=0){
			textPointer=searchForStartLoop();
		}
	}

	private void loopStart() throws NotEqualLoopException {
		if(space[pointer]==0){
			textPointer=searchForEndLoop();
		}
	}

	private void valueInput() {
		if(InputCount<messageInput.length()){
			space[pointer]=(byte) messageInput.charAt(InputCount);
			InputCount++;
		}
	}

	private void valueOutput() {
		messageOutput=messageOutput+((char) space[pointer]);
	}

	private void valueDec() {
		space[pointer]--;
	}

	private void valuePlus() {
			space[pointer]++;
	}

	private void pointerDec() {
		if(pointer==0){
			pointer=29999;
		}
		else{
			pointer--;
		}
	}

	private void pointerPlus() {
		if(pointer==29999){
			pointer=0;
		}
		else{
			pointer++;
		}
	}
	
	private int searchForStartLoop() throws NotEqualLoopException{
		int loopCounter=1;
		int tempTextPointer=textPointer;
		
		do{
			tempTextPointer--;
			if(text.charAt(tempTextPointer)==']'){
				loopCounter++;
			}
			if(text.charAt(tempTextPointer)=='['){
				loopCounter--;
			}
			
			//������ǰ��ѭ�������Ƿ��Ӧ
			if(tempTextPointer<0){
				throw new NotEqualLoopException();
			}
			if(tempTextPointer>=text.length()){
				throw new NotEqualLoopException();
			}
			
		}while(loopCounter!=0);
		
		return tempTextPointer;
	}
	
	private int searchForEndLoop() throws NotEqualLoopException{
		int loopCounter=1;
		int tempTextPointer=textPointer;
		
		do{
			tempTextPointer++;
			if(text.charAt(tempTextPointer)=='['){
				loopCounter++;
			}
			if(text.charAt(tempTextPointer)==']'){
				loopCounter--;
			}
			
			//������ǰ��ѭ�������Ƿ��Ӧ
			if(tempTextPointer<0){
				throw new NotEqualLoopException();
			}
			if(tempTextPointer>=text.length()){
				throw new NotEqualLoopException();
			}
		}while(loopCounter!=0);
		
		return tempTextPointer;
	}
}