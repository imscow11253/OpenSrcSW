package MakeXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

public class genSnippet {
	String[] list = new String[5];
	
	genSnippet(String dir) throws FileNotFoundException {
		FileOutputStream fileStream = new FileOutputStream(dir);
	
		String str = fileStream.toString();		
		System.out.println(str);
		
//		for(int i =0; i< 5;i++) {
//			list[i] = 
//		}
	}
}
