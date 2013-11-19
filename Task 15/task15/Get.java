package task15;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;

public class Get {

    public static String executeGet(String targetURL){
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = rd.readLine()) != null) {

                response.append(line);
                response.append("\r\n");
            }

            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        String outString = executeGet("http://www.flickr.com/search/?q=cats");

        FileOutputStream out = null;
        try{
            out = new FileOutputStream("D:/Test/get_out_example.txt");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            out.write(outString.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
