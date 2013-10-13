package CC;

import java.io.IOException;



public class Translogger extends Logger {

	////////////////////////////////////////////
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//~~~~~THIS CLASS HAS BEN DEPRECATED~~~~~~//
	//~~ALL COMMAND SUBCLASSES SHOULD HANDLE~~//
	//~~~~~~~TRANSLATION FUNCTIONALITY~~~~~~~~//
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	////////////////////////////////////////////
	
	/*
	 * This class has been retained only to provide functionality while commands are being properly implemented
	 */
	
	Command[] valid;
	
	public Translogger(boolean DOS) throws IOException{
		super(DOS);
		cleanLog();
	}
	
	public String[] translate(String[] commands, boolean keep){
		String[] ret = commands;
		if(WINDOWS){
			//TODO properly translate/check if translate is needed
			ret = addCmd(commands, keep);
		}
		log(commands, ret);
		return ret;
	}
	
	public String[] translate(String[] commands){
		String[] ret = commands;
		if(WINDOWS){
			//TODO properly translate/check if translate is needed
			ret = addCmd(commands, false);
		}
		log(commands, ret);
		return ret;
	}
	
	private String[] addCmd(String[] commands, boolean keep){
		String[] ret = new String[commands.length+2];
		ret[0] = "cmd";
		if(keep)
			ret[1] = "/K";
		else ret[1] = "/C";
		for(int i=0; i<commands.length; i++)
			ret[i+2] = commands[i];
		return ret;
	}
	
	
}
