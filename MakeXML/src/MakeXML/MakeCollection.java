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
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class MakeCollection {
	private List<String> list = new ArrayList<String>(5);
	private String path;
	private List<String> titles = new ArrayList<String>(5);
	private List<String> contents = new ArrayList<String>(5);
	
	MakeCollection(){
		list.add("떡");
		list.add("라면");
		list.add("아이스크림");
		list.add("초밥");
		list.add("파스타");
	}
	
	void extractText(String dir) {
		for(int i =0; i < 5; i++) {
			path = dir + "/" + list.get(i) + ".html";
			
			try {
				org.jsoup.nodes.Document html = Jsoup.parse(new File(path), "UTF-8");
				
				org.jsoup.nodes.Element element = html.selectFirst("title");
				org.jsoup.select.Elements content = html.select("#content");
				
				String text = element.text();
				String text2 = content.text();
				
				titles.add(text);
				contents.add(text2);
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	void makeStructureOFXML() throws ParserConfigurationException, FileNotFoundException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		for(int i = 0; i < titles.size(); i++) {
			
			Element docID = doc.createElement("doc");
			docs.appendChild(docID);
			docID.setAttribute("id", Integer.toString(i));
			
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(titles.get(i)));
			docID.appendChild(title);
			
			Element body = doc.createElement("body");
			body.appendChild(doc.createTextNode(contents.get(i)));
			docID.appendChild(body);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("C://Users/LG/Desktop/SimpleIR/MakeXML/src/MakeXML/makeCollection.xml")));
		
		transformer.transform(source, result);
	}
}
