package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
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
}
