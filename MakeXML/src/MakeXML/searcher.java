package MakeXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {
	HashMap hashmap;
	HashMap<String, Double> kkmaHash;
	private List<String> contents = new ArrayList<String>(5);
	
	void loadHashMap(String dir) throws IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream(dir);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		hashmap = (HashMap)object;
	}
	
	void makekkmaHash(String query) {
		KeywordExtractor ke = new KeywordExtractor();
		
		KeywordList kl = ke.extractKeyword(query, true);
		kkmaHash = new HashMap<String, Double>(kl.size());
		
		for(int i =0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			kkmaHash.put(kwrd.getString(), (double)kwrd.getCnt());
		}		
	}
	
	void calcSim() throws IOException {
		double[] Sim = {0.0 ,0.0 ,0.0 ,0.0 ,0.0};
		
		Iterator it = kkmaHash.keySet().iterator();
		while(it.hasNext()) {
			String str = (String) it.next();
			
			if(hashmap.containsKey(str)) {
				List<Double> list = (ArrayList<Double>)hashmap.get(str);
				for(int j =0; j< list.size(); j=j+2) {
					
					int index = (int)Math.round((double)list.get(j));
					Sim[index] += list.get(j+1) * kkmaHash.get(str);
				}
			}
		}
		
		int[] arr= {0,1,2,3,4};
		for(int i =0;i<4;i++) {
			int index = i;
			for(int j =i;j<5;j++) {
				if(Sim[index] < Sim[j]) {
					index = j;
				}
			}
			double temp;
			temp = Sim[index];
			Sim[index] = Sim[i];
			Sim[i] = temp;
			int temp2;
			temp2 = arr[index];
			arr[index] = arr[i];
			arr[i] = temp2;
		}
		for(int i =0; i<5; i++) {
			org.jsoup.nodes.Document html = Jsoup.parse(new File("C:/Users/LG/Desktop/SimpleIR/MakeXML/src/MakeXML/makeCollection.xml"), "UTF-8");
			
			org.jsoup.select.Elements content = html.select("docs > doc");
			
			String text = content.get(i).text();
			contents.add(text);
		}
		for(int i =0; i<3; i++) {
			System.out.println(contents.get(arr[i]).split(" ")[0]);
		}
		
	}
}
