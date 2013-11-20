package task15;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static TreeMap<String,String> lts,stl;
	private static TreeMap<String,ArrayList<String>> aval;
	static String trquerry="https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
	static String glquerry="https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=";
	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		File f=new File("key");
		if(!f.exists()){
			try {
				f.createNewFile();
				FileOutputStream out=new FileOutputStream(f);
				Scanner sc = new Scanner(System.in);
				System.out.println("Please, enter your key for Yandex API.\n"
						+ "This key will be saved to file \"key\", so you won't need to enter it again");
				String s=sc.nextLine();
				trquerry=trquerry.concat(s);
				glquerry=glquerry.concat(s);
				out.write(s.getBytes());
				out.close();
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				System.out.println("Found file with key for your Yandex API");
				FileInputStream in=new FileInputStream(f);
				String s="";
				int c;
				while((c=in.read())!=-1){
					s=s.concat(Character.toString((char)c));
				}
				trquerry=trquerry.concat(s);
				glquerry=glquerry.concat(s);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		glquerry=glquerry.concat("&ui=ru ");
		aval=new TreeMap<>();
		stl=new TreeMap<>();
		lts=new TreeMap<>();
		String response = Get.executeGet(glquerry);
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(response, JsonObject.class);
		JsonObject langs=json.getAsJsonObject("langs");
		stl=gson.fromJson(langs, stl.getClass());
		for(Entry<String, String> k:stl.entrySet()){
			lts.put(k.getValue(), k.getKey());
		}		
		JsonArray dirs= json.getAsJsonArray("dirs");
		for(JsonElement o:dirs){
			String s=o.getAsString();
			String[] res=s.split("-");			
			res[0]=stl.get(res[0]);
			res[1]=stl.get(res[1]);
			if(aval.get(res[0])!=null){
				ArrayList<String> arr=aval.get(res[0]);
				aval.remove(res[0]);
				arr.add(res[1]);
				Collections.sort(arr);
				aval.put(res[0], arr);
			}
			else{
				ArrayList<String> arr=new ArrayList<>();
				arr.add(res[1]);
				aval.put(res[0], arr);
			}
			
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextArea txtrFrom = new JTextArea("UTF-8");
		txtrFrom.setText("From");
		txtrFrom.setBounds(10, 11, 218, 168);
		contentPane.add(txtrFrom);
		
		final JTextArea txtrTo = new JTextArea();
		txtrTo.setEditable(false);
		txtrTo.setText("To");
		txtrTo.setBounds(10, 193, 218, 168);
		contentPane.add(txtrTo);
		
		final JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(282, 15, 134, 22);
		contentPane.add(comboBox);
		for(String s:aval.keySet()){
			comboBox.addItem(s);
		}
		comboBox.setSelectedItem(0);
				
		final JComboBox<String> comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(282, 48, 134, 22);
		contentPane.add(comboBox_1);
		for(String s:aval.get(comboBox.getSelectedItem())){
			comboBox_1.addItem(s);
		}
		
		JButton btnNewButton = new JButton("Translate");
		
		btnNewButton.setBounds(238, 173, 91, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(238, 19, 46, 14);
		contentPane.add(lblFrom);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(238, 52, 46, 14);
		contentPane.add(lblTo);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_1.removeAllItems();
				for(String s:aval.get(comboBox.getSelectedItem())){
					comboBox_1.addItem(s);
				}
			}
		});
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					String source = URLEncoder.encode(txtrFrom.getText(), "UTF-8");
					String response = Get.executeGet(String.format("%s&text=%s&lang=%s-%s", trquerry,source,lts.get(comboBox.getSelectedItem()),lts.get(comboBox_1.getSelectedItem())));
					Gson gson = new Gson();
					JsonObject json = gson.fromJson(response, JsonObject.class);
					JsonElement code=json.get("code");
					switch(code.getAsInt()){
					case 200:
						JsonArray text=json.getAsJsonArray("text");
						byte ptext1[] = text.getAsString().getBytes();
						String value = new String(ptext1, "UTF-8");
						txtrTo.setText(value);
						break;
					default:
						txtrTo.setText("some problems appeared");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}								
			}
		});
	}
}
