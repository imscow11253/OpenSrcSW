package MakeXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException{
		// TODO Auto-generated method stub
		
		if (args[0].compareTo("-c") == 0) {
			MakeCollection file = new MakeCollection();
			
			file.extractText(args[1]);
			file.makeStructureOFXML();
		}
		else if (args[0].compareTo("-k") == 0){
			MakeKeyword file = new MakeKeyword();
			
			file.extractText(args[1]);
			file.makeStructureOFXML();
		}
	}

}
