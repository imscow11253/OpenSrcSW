package MakeXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;

public class Indexer {
	
	private List<String> contents = new ArrayList<String>(5);
	private List<String[]> keys = new ArrayList<String[]>(5);
	private List<String> all = new ArrayList<String>();
	private HashMap<String, List> map = new HashMap();
	
	void extractText(String dir) throws IOException {
		
		org.jsoup.nodes.Document html = Jsoup.parse(new File(dir), "UTF-8");
		
		org.jsoup.select.Elements content = html.select("docs > doc");
		
		for(int i =0; i<5; i++) {			
			String text = content.get(i).text();
			contents.add(text);
		}
	}
	
	void split() {
		for(int i = 0; i < 5; i++) {
			keys.add(contents.get(i).split("\\\\"));
		}
		for (int i = 0 ; i < 5; i++) {
			for(int j =0; j < keys.get(i).length; j++) {
				
				String[] str = keys.get(i)[j].split(":");
				String key;
				
				if (j == 0) {
					String[] temp = str[0].split(" ");
					key = temp[0];
				}
				else {
					key = str[0];
				}
				
				all.add(key);
				all.add(str[1]);
			}
		}
	}
	
	void makeHashmap() {
		
		String key;
		
		for(int i =0; i < 5; i++) {
			for(int j =0; j < keys.get(i).length; j++) {
				String[] str = keys.get(i)[j].split(":");
				
				if (j == 0) {
					String[] temp = str[0].split(" ");
					key = temp[0];
				}
				else {
					key = str[0];
				}
				
				if(map.containsKey(key)) {
					
					int num = Collections.frequency(all, key);
					
					double tem = Double.parseDouble(str[1]) * Math.log10(5 / num); 
					double temp = Double.parseDouble(String.format("%.2f", tem));
					
					map.get(key).add((double)i);
					map.get(key).add(temp);
				}
				else {
					List<Double> list = new ArrayList<Double>();
					
					int num = Collections.frequency(all, key);
					double tem = (Double.parseDouble(str[1])) * (Math.log10(5 / num)); 
					double temp = Double.parseDouble(String.format("%.2f", tem));
					
					list.add((double)i);
					list.add(temp);
					map.put(key, list);
				}
			}
		}		
	}
	
	void postFile() throws IOException {
		
		FileOutputStream fileStream = new FileOutputStream("C://Users/LG/Desktop/SimpleIR/MakeXML/src/MakeXML/index.post");
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		objectOutputStream.writeObject(map);
		
		objectOutputStream.close();
		
	}

	
	void printFile() throws IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream("C://Users/LG/Desktop/SimpleIR/MakeXML/src/MakeXML/index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap = (HashMap)object;
		Iterator<String> it = hashMap.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			List value = (ArrayList)hashMap.get(key);
			System.out.println(key + " -> " + value);
		}
	}
}
