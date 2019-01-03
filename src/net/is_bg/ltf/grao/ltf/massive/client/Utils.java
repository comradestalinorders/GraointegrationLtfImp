package net.is_bg.ltf.grao.ltf.massive.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

class Utils {
	
	public static List<String> lineStringReader(String lineSeparatedStirng) throws IOException{
		BufferedReader reader = new BufferedReader(new StringReader(lineSeparatedStirng));
		List<String> result = new ArrayList<String>();
		try{
			String s = reader.readLine();
			while(s!=null){
				result.add(s);
				s = reader.readLine();
			}
		}finally {
			if(reader!=null) reader.close();
		}
		return result;
	}
	
	public static String [] parseLine(String line, String separator) {
		return line.split(separator);
	}
}
