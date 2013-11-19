package task14;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args){
		try {
			URL hp = new URL("http://ru.m.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D0%BE%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B8_%D1%81_%D0%BD%D0%B0%D1%81%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5%D0%BC_%D0%B1%D0%BE%D0%BB%D0%B5%D0%B5_100_%D1%82%D1%8B%D1%81%D1%8F%D1%87_%D0%B6%D0%B8%D1%82%D0%B5%D0%BB%D0%B5%D0%B9");
			URLConnection hpCon = hp.openConnection();
			InputStream stream=hpCon.getInputStream();
			InputStreamReader inp=new InputStreamReader(stream,"UTF-8");
			Pattern ptr=Pattern.compile("<a(\\s*\\w+=\"\\w+\")*>");
			int c;
			while ((c=inp.read())!=-1) {
				String s="";
				while((c!=-1)&&(char)c!='\n'){
					s=s.concat(Character.toString((char) c));
					c=inp.read();
				}
				Matcher m=ptr.matcher(s);
				if(m.find()){
					System.out.println(s);
				}
            }
		} catch (IOException e) {
			e.printStackTrace();
		}		 
	}

}
