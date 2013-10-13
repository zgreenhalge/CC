package CC;

import java.util.ArrayList;

public class CommandParser {
	
	public String[] parseFull(String line){return line.split("\\s++");}
	public String parseCommand(String line){return parseFull(line)[0];}
	
	public String[] parseFlags(String line){
		String[] all = parseFull(line);
		ArrayList<String> temp = new ArrayList<String>();
		for(String s: all){
			char[] chars = s.toCharArray();
			if(chars[0] == '-'){
				for(char c: chars)
					temp.add(c + "");
				break;
			}//if
		}//for
		all = (String[]) temp.toArray();
		if(all.length > 0)
			return all;
		return null;
	}//parseFlags
		
}
