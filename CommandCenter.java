package CC;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class CommandCenter {

	private static final boolean dev = true;
	
	protected static boolean run = true, found, WINDOWS;
	protected static String input = "", commands[];
	protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	protected static File homeDir, point;
	protected static Translogger translogger;
	protected static Booter booter;
	protected static CommandParser parser;
	protected static GregorianCalendar cal;
	protected static Time startTime;
	protected static Command custom[];
	
	
	private static final CommandCenter inst = new CommandCenter(); //static instance for accessing outer static methods 
	
	
	public static void main(String[] args) throws Throwable{
		System.out.println("Initializing variables...");
		startTime = inst.new Time();
		homeDir = new File(System.getProperty("user.home") + File.separator + "CommandCenter");
		point = homeDir;
		System.out.println("Checking directories...");
		if(!homeDir.exists())
				homeDir.mkdir();//make CommandCenter folder if none exists
		System.out.println("Determining OS...");
		String OS = System.getProperty("os.name");
		if(OS.contains("Windows"))
			WINDOWS = true;  //DOS
		else
			WINDOWS = false; //non DOS system
		System.out.println("Booting up utilities...");
		booter = new Booter();
		translogger = new Translogger(WINDOWS);
		parser = new CommandParser();
		booter.boot();  //may cause terminate program if problem with boot
		
		System.out.println();
		
		//System.out.println("              .,-:;//;:=,\n          . :H@@@MM@M#H/.,+%;,\n       ,/X+ +M@@M@MM%=,-%HMMM@X/,\n     -+@MM; $M@@MH+-,;XMMMM@MMMM@+-\n    ;@M@@M- XM@X;. -+XXXXXHHH@M@M#@/.\n  ,%MM@@MH ,@%=            .---=-=:=,.\n  =@#@@@MX .,              -%HX$$%%%+;\n =-./@M@M$                  .;@MMMM@MM:\n X@/ -$MM/                    .+MM@@@M$\n,@M@H: :@:                    . =X#@@@@-\n,@@@MMX, .                    /H- ;@M@M=\n.H@@@@M@+,                    %MM+..%#$.\n /MMMM@MMH/.                  XM@MH; =;\n  /%+%$XHH@$=              , .H@@@@MX,\n   .=--------.           -%H.,@@@@@MX,\n   .%MM@@@HHHXX$$$%+- .:$MMX =M@@MM%.\n     =XMMM@MM@MM#H;,-+HMM@M+ /MMMX=\n      =%@M@M#@$-.=$@MM@@@M; %M%=\n        ,:+$+-,/H#MMMMMMM@= =,\n              =++%%%%+/:-.\n");
		System.out.println("Welcome to your Command Center, " + System.getProperty("user.name") + ".");
		System.out.println("You are running " + System.getProperty("os.name") + " with " + System.getProperty("os.arch") + " processors.");
		System.out.println("You have " + new File(System.getProperty("user.home")).getFreeSpace()/1073741824 + " gb free");
		
		//System.getProperties().list(System.out);
		System.out.println();
		
		System.out.println("Time of log in: " + startTime.toString());
		String prompt = point.getPath() + ">>";
		System.out.println();
		System.out.print(prompt); 
		
		while(run){		//start of loop, will continue until "exit" is received
			try{input = in.readLine();}
			catch(Exception e){System.out.println("Input error");}
			
			if(!input.equals("")){
				if(dev){
					try{
						found = false;
						commands = parser.parseFull(input);
						String command = parser.parseCommand(input);
						for(Command c: Command.getCommands()){
							if(c.is(command)){
								if(c.validFlags(parser.parseFlags(input))){//return boolean, throw ProperSyntaxError
									c.execute(commands);
								}//if validFlags
							}//if c.is(input)
						}//for each command
						if(!found){//following block allows for shell use while still being developed
							/***Following block appends location in front of commands like ls***/
							commands = input.split("\\s++");
							if (commands.length == 1){
								String[] temp = new String[2];
								temp[0] = commands[0];
								temp[1] = point.getPath();
								commands = temp;
							}
							/****************************************************************/
							Process child = Runtime.getRuntime().exec(commands);
							BufferedReader childOut = new BufferedReader(new InputStreamReader(child.getInputStream()));
							BufferedReader childErr = new BufferedReader(new InputStreamReader(child.getErrorStream()));
							child.waitFor();
							while((input = childOut.readLine()) != null)
								System.out.println(input);
							ArrayList<String> errors = new ArrayList<String>();
							while((input = childErr.readLine()) != null)
								errors.add(input);
							for(String e: errors)
								System.out.println(e);
							translogger.logError(errors);
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						translogger.logError(e.getMessage());
					}
				}//if dev
			else{
				//TODO REMOVE CODE BLOCK AFTER IMPLEMENTATION OF NEW CLASSES 
				if(input.equalsIgnoreCase("tasklist")){
					Command.TASKLIST.execute(null);
				}
				else if(input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")){
					System.out.println();
					System.out.println("Command Center use time: " + startTime.diff(inst.new Time()).toString());
					System.out.println("Logging off");
					translogger.logOff();
					System.exit(0);
				}
				else if(input.equalsIgnoreCase("time"))
					System.out.println(inst.new Time().toString());
				
				else{
					commands = input.split("\\s++");
					if (commands.length == 1){
						String[] temp = new String[2];
						temp[0] = commands[0];
						temp[1] = point.getPath();
						commands = temp;
					}
					commands = translogger.translate(commands);
					try{
						Process child = Runtime.getRuntime().exec(commands);
						BufferedReader childOut = new BufferedReader(new InputStreamReader(child.getInputStream()));
						BufferedReader childErr = new BufferedReader(new InputStreamReader(child.getErrorStream()));
						child.waitFor();
						while((input = childOut.readLine()) != null)
							System.out.println(input);
						ArrayList<String> errors = new ArrayList<String>();
						while((input = childErr.readLine()) != null)
							errors.add(input);
						for(String e: errors)
							System.out.println(e);
						translogger.logError(errors);
					}
					catch (Exception e){
						System.out.println(e.getMessage());
						translogger.logError(e.getMessage());
					}
				}
				}
			}//if
			prompt = point.getPath() + ">>";
			System.out.print(prompt);
			input= "";
		
		}//outer while 
		
	}//main
	
	
	private class Time{
		public int HOUR, MINUTE, SECOND, MILLI;
		
		public Time(){
			cal = new GregorianCalendar();
			HOUR = cal.get(Calendar.HOUR);
			MINUTE = cal.get(Calendar.MINUTE);
			SECOND = cal.get(Calendar.SECOND);
			MILLI = cal.get(Calendar.MILLISECOND);
		}
		
		private Time(int H, int M, int S, int ML){
			HOUR = H;
			MINUTE = M;
			SECOND = S;
			MILLI = ML;
		}
		
		public String toString(){
			String h,m,s,mil;
			if(HOUR<10)
				h = "0" + HOUR;
			else
				h = "" + HOUR;
			if(MINUTE<10)
				m = "0" + MINUTE;
			else
				m = "" + MINUTE;
			if(SECOND<10)
				s = "0" + SECOND;
			else
				s = "" + SECOND;
			if(MILLI<100){
				mil = "0" + MILLI;
				if(MILLI<10)
					mil = "0" + mil;
			}
			else mil = "" + MILLI;
			
			return h + ":" + m + ":" + s + "." + mil;
		}
		
		public Time diff(Time in){
			int h, m, s, ml;
			if(in.HOUR>HOUR)h=in.HOUR-HOUR;
			  else h=HOUR-in.HOUR;
			if(in.MINUTE>MINUTE)m=in.MINUTE-MINUTE;
			  else m=MINUTE-in.MINUTE;
			if(in.SECOND>SECOND)s=in.SECOND-SECOND;
			  else s=SECOND-in.SECOND;
			if(in.MILLI>MILLI)ml=in.MILLI-MILLI;
			  else ml=MILLI-in.MILLI;
			return new Time(h,m,s,ml);
		}
	}
	
	public static String getTime(){
		return inst.new Time().toString();
	}
	
	public static String getDate(){
		String ret = "";
		ret += (cal.get(Calendar.MONTH)+1) + "/";
		ret += cal.get(Calendar.DAY_OF_MONTH) + "/";
		ret += cal.get(Calendar.YEAR);
		return ret;
	}
	
}//MyShell