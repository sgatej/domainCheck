package ro.gazduire.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadFile {
public static String path = "src/domains.txt";
public static File file = new File(path);
	
public  String getDomains(){
	BufferedReader reader;
	
	String domains = null;
	try{
		
		reader = new BufferedReader(new FileReader("src/domains.txt"));
		String line = reader.readLine();
		domains = line;
		while (line != null){
			
			line = reader.readLine();
			if (line != null)
			domains = domains + line + " ";
		}
		reader.close();
	}
	catch(IOException e){
		e.printStackTrace();
	}
	return domains;
} 

public List<String> readLines ()throws Exception{
	if(!file.exists()) {
		return new LinkedList<String>();
	}
	@SuppressWarnings("resource")
	BufferedReader reader = new BufferedReader(new FileReader(file));
	List<String> results = new LinkedList<String>();
	String line = reader.readLine();
	while(line !=null) {
		results.add(line);
		line = reader.readLine();
	
	}return results;
}

}
