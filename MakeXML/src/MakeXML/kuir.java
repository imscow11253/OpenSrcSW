package MakeXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException{
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
		else if (args[0].compareTo("-i") == 0) {
			Indexer file = new Indexer();
			
			file.extractText(args[1]);
			file.split();
			file.makeHashmap();
			file.postFile();
			file.printFile();
		}
		else if (args[0].compareTo("-s") == 0) {
			searcher sear = new searcher();
			
			sear.loadHashMap(args[1]);
			sear.makekkmaHash(args[3]);
			sear.calcSim();
		}
	}

}
