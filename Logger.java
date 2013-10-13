package CC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
	
	boolean WINDOWS;
	File log = new File(CommandCenter.homeDir.getPath() + File.separator + "log.txt");
	FileWriter logger;
	BufferedWriter bw;
	PrintWriter pw;
	
	public Logger(boolean DOS) throws IOException{
		WINDOWS = DOS;
		logger = new FileWriter(log, true); //append to the end of the file, don't overwrite
		bw = new BufferedWriter(logger); //buffer the writer
		pw = new PrintWriter(bw); //ease of use class
	}

	protected synchronized void log(String[] cmdIn, String[] cmdOut){
		String write = "";
		write += CommandCenter.getDate() + " ";
		write += CommandCenter.getTime() + ": ";
		write += CommandCenter.point.getAbsolutePath() + "> ";
		for(String s: cmdIn)
			if(!s.equals(CommandCenter.point.getAbsolutePath()))
				write += s + " ";
		pw.println(write);
		if((cmdIn.length != cmdOut.length || !cmdIn[0].equals(cmdOut[0])) && !cmdOut[2].equals(cmdIn[0])){
			write = "   Command translated to: ";
			for(String s: cmdOut)
				write += s+ " ";
			pw.println(write);
		}
		pw.flush();
	}
	
	protected synchronized void log(String[] cmdIn, String[] cmdOut, String n){
		String write = "";
		write += CommandCenter.getDate() + " ";
		write += CommandCenter.getTime() + ": ";
		write += CommandCenter.point.getAbsolutePath() + "> ";
		for(String s: cmdIn)
			if(!s.equals(CommandCenter.point.getAbsolutePath()))
				write += s + " ";
		pw.println(write);
		if((cmdIn.length != cmdOut.length || !cmdIn[0].equals(cmdOut[0])) && !cmdOut[2].equals(cmdIn[0])){
			write = "   Command translated to: ";
			for(String s: cmdOut)
				write += s+ " ";
			pw.println(write);
		}
		pw.flush();
		note(n);
	}
	
	protected synchronized void log(String[] cmd){
		String write = "";
		write += CommandCenter.getDate() + " ";
		write += CommandCenter.getTime() + ": ";
		write += CommandCenter.point.getAbsolutePath() + "> ";
		for(String s: cmd)
			if(!s.equals(CommandCenter.point.getAbsolutePath()))
				write += s + " ";
		pw.println(write);
		pw.flush();
	}
	
	protected synchronized void log(String[] cmd, String n){
		String write = "";
		write += CommandCenter.getDate() + " ";
		write += CommandCenter.getTime() + ": ";
		write += CommandCenter.point.getAbsolutePath() + "> ";
		for(String s: cmd)
			if(!s.equals(CommandCenter.point.getAbsolutePath()))
				write += s + " ";
		pw.println(write);
		pw.flush();
		note(n);
	}
	
	private synchronized void note(String s){
		pw.println("  --NOTE: " + s);
		pw.flush();
	}
	
	public synchronized void logError(ArrayList<String> s){
		String write = "    Error caught: ";
		for(String st: s)
			write += st + System.getProperty("line.separator");
		pw.println(write);
		pw.flush();
	}
	
	public synchronized void logError(String s){
		String write = "    Error caught: " + s;
		pw.println(write);
		pw.flush();
	}
	
	public void logOn(){
		pw.println("");
		pw.println("------NEW SESSION: " + CommandCenter.getDate() + " " + CommandCenter.getTime() + "------");
		if(WINDOWS){
			pw.println("   Windows OS detected. All commands will be translated.");
		}
		pw.flush();
	}
	
	public void logOff(){
		pw.println("");
		pw.println("----END OF SESSION: " + CommandCenter.getDate() + " " + CommandCenter.getTime() + "----");
		pw.println("");
		pw.close();
	}
	
	protected void cleanLog(){
		//TODO read log: check sessions, if session date is more than 60 days old, move all lines up and overwrite
		//   will require tempLog.txt and readers, copy wanted files over, then copy from temp back to new file
	}
}
