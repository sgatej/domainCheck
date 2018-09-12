package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
public static void main(String[] args){
	BufferedReader reader;
	try{
		reader = new BufferedReader(new FileReader("/Users/sashag/Work_stuff/Workspace/domainCheck/src/test/resources/domains.txt"));
		String line = reader.readLine();
		while (line != null){
			System.out.println(line);
			line = reader.readLine();
		}
		reader.close();
	}
	catch(IOException e){
		e.printStackTrace();
	}
	
} 
}
