package CC;

public abstract class Command {

	protected String name;
	protected String[] flags;
	
	public static Command CD = new CD(), TASKLIST = new TaskList();
	private static Command[] valid = {CD};
	
	protected Command(){}
	
	//execute must be synchronized in case of writers
	public abstract void execute(String[] args) throws Exception;
	public abstract String man();
	public abstract boolean validFlags(String[] passedFlags);
	
	public boolean is(String s){
		return name.equals(s);
	}
	
	public static Command[] getCommands(){
		return valid;
	}
}
