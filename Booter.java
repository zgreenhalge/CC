package CC;

import java.io.File;

public class Booter {

	File homeDir, log, links;
	
	public Booter(){
		homeDir = new File(System.getProperty("user.home") + File.separator + "CommandCenter");
		log = new File(homeDir.getPath() + File.separator + "log.txt");
		links = new File(homeDir.getPath() + File.separator + "custom.txt");
	}
	
	public void boot(){
		System.out.println("    Verifying files in " + homeDir.getAbsolutePath());
		try{
			
			if(log.exists()) //if file exists
				System.out.println("    Log found!");
			else{
				log.createNewFile();
				System.out.println("    New log file created...");
			}
			
			if(links.exists())
				System.out.println("    Links found!");
			else{
				links.createNewFile();
				System.out.println("    New links file created...");
			}
			CommandCenter.translogger.logOn();
		}//try
		catch (Exception e){
			System.out.println();
			System.out.println();
			System.out.println("Error booting utilities:");
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.out.println();
			System.out.println();
			System.out.println("Exiting..");
			System.exit(0);
		}//catch
	}//boot()
	
}//class
