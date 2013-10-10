package CC;

import java.io.IOException;



public class Translogger extends Logger {

	Command[] valid;
	
	public Translogger(String os) throws IOException{
		super(os);
		cleanLog();
	}
	
	public String[] translate(String[] commands){
		String[] ret = commands;
		if(OS.equals("windows"))
			ret = addCmd(commands);
		log(commands, ret);
		return ret;
	}
	
	private String[] addCmd(String[] commands){
		String[] ret = new String[commands.length+2];
		ret[0] = "cmd";
		ret[1] = "/C";
		for(int i=0; i<commands.length; i++)
			ret[i+2] = commands[i];
		return ret;
	}
	
	
}
