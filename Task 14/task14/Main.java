package task14;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args){
		try {
			String baseurl="http://ru.m.wikipedia.org";
			ArrayList<Pair> adrs=new ArrayList<>();
			Map<String, Integer> year=Collections.synchronizedMap(new TreeMap<String,Integer>());
			URL hp = new URL("http://ru.m.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D0%BE%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B8_%D1%81_%D0%BD%D0%B0%D1%81%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5%D0%BC_%D0%B1%D0%BE%D0%BB%D0%B5%D0%B5_100_%D1%82%D1%8B%D1%81%D1%8F%D1%87_%D0%B6%D0%B8%D1%82%D0%B5%D0%BB%D0%B5%D0%B9");
			Document page=Jsoup.parse(hp, 1000);
			Elements tbody=page.select("table[class=wikitable sortable]").select("tbody");
			Elements links=tbody.select("a[href~=/wiki/%.*]");
			for(Element l:links){
				adrs.add(new Pair(l.attributes().get("title"), baseurl.concat(l.attributes().get("href"))));
			}
			List<Pair> l=adrs.subList(0, adrs.size()/3);
			PageAnalysis pa=new PageAnalysis(l,year);
			pa.start();
			adrs.subList(adrs.size()/3, adrs.size()/3*2);
			adrs.subList(adrs.size()/3*2,adrs.size());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		 
	}

}
