package CC;

public abstract class Command {

	private String name;
	private String[] flags;
	
	private Command(String n, String[] f){
		name = n;
		flags = f;
	}
	
	public boolean is(String s){
		return name.equals(s);
	}
	
	
	public abstract void execute(String[] passedFlags);
	public abstract String man();
	
	//TODO load list of valid commands (i.e. all Command subclasses, use final static instances)
	public static Command[] loadValid(){
		return null;
	}
}
