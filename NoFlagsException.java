package CC;

public class NoFlagsException extends Exception {
	
	public NoFlagsException(String syntax, Command c){
		super("The command " + c.name + " does not take flags. \n Proper syntax is:\n    " + syntax);
	}
}
