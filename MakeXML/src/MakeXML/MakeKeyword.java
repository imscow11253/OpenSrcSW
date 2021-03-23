package MakeXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MakeKeyword {
	private List<String> list = new ArrayList<String>(5);
	private List<String> contents = new ArrayList<String>(5);
	private List<String> contentsKKMA = new ArrayList<String>(5);
	
	MakeKeyword(){
		list.add("떡");
		list.add("라면");
		list.add("아이스크림");
		list.add("초밥");
		list.add("파스타");
	}
	
	void extractText(String dir) throws IOException {
		for(int i =0; i<5; i++) {
			org.jsoup.nodes.Document html = Jsoup.parse(new File(dir), "UTF-8");
			
			org.jsoup.select.Elements content = html.select("docs > doc");
			
			String text = content.get(i).text();
			contents.add(text);
		}
	}
	
	void makekkma() {
		for(int i =0; i<contents.size(); i++) {
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(contents.get(i), true);
			
			String txt = "";
			for(int j = 0; j<kl.size();j++) {
				txt = txt + kl.get(j).getString() + ":" + kl.get(j).getCnt() + "\\";
			}
			contentsKKMA.add(txt);
		}
	}
	
	void makeStructureOFXML() throws ParserConfigurationException, FileNotFoundException, TransformerException {
		makekkma();
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		for(int i = 0; i < list.size(); i++) {
			
			Element docID = doc.createElement("doc");
			docs.appendChild(docID);
			docID.setAttribute("id", Integer.toString(i));
			
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(list.get(i)));
			docID.appendChild(title);
			
			Element body = doc.createElement("body");
			body.appendChild(doc.createTextNode(contentsKKMA.get(i)));
			docID.appendChild(body);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("C://Users/LG/Desktop/SimpleIR/MakeXML/src/MakeXML/index.xml")));
		
		transformer.transform(source, result);
	}
}
