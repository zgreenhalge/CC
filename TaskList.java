package CC;


public class TaskList extends Command {

	String name = "TaskList";
	String[] flags = null;
	
	@Override
	public synchronized void execute(String[] passedFlags) throws Exception {

		if(passedFlags != null) throw new NoFlagsException("tasklist", this);
		
		if(CommandCenter.WINDOWS){
			String commands[] = {"cmd", "/C", "start", "\"Running Tasks\"", "cmd", "/K", "tasklist.exe"};
			String pass = "";
			CommandCenter.translogger.log(commands, "COMMAND RUN---> " + name);
			for(String s: commands)
				pass += s + " ";
			System.out.println(pass);
			new ProcessBuilder(commands).start();
		}
		else{
			//TODO UNIX implementation
			//exec("top"); <--need to find proper syntax
		}
	}

	@Override
	public String man() {
		return "Starts a simple task manager in a new window. Takes no flags or arguments.";
	}

}
