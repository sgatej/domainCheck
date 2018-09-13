package com.eurodns.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadFile {
public static String file = "src/domains.txt";

	
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

public static List<String> readLines (File file)throws Exception{
	if(!file.exists()) {
		return new LinkedList<String>();
	}
	BufferedReader reader = new BufferedReader(new FileReader(file));
	List<String> results = new LinkedList<String>();
	String line = reader.readLine();
	while(line !=null) {
		results.add(line);
		line = reader.readLine();
	}return results;
}

}
