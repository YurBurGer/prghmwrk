package task14;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PageAnalysis extends Thread{
	List<Pair> pairs;
	Map<String,Integer> map;
	public PageAnalysis(List<Pair> p, Map<String,Integer> m) {
		super();
		this.pairs=p;
		this.map=m;
	}
	public void run(){
		try {
//			for(Pair el:pairs){
				URL hp = new URL(pairs.get(0).getLink());
				Document page=Jsoup.parse(hp, 1000);
				Elements table=page.select("table[class=infobox vcard]");
//				res1=res1.select("tr");
				Elements res1=table.get(0).select("table").get(0).select("tbody").get(0).select("table");
//				for(Element el:res1){
				System.out.println(res1.get(1).html());	
					System.out.println("sdfgsdfsdf");
//				}				
//			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
