package CC;

public class CD extends Command {

	String name = "cd";
	String[] flags = null;
	
	@Override
	public void execute(String[] passedFlags) throws Exception {
		
		if(passedFlags != null) throw new NoFlagsException("cd [.. : path]", this);

	}

	@Override
	public String man() {
		
		return "";
	}

}
