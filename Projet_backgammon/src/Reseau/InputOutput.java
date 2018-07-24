package Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class InputOutput {
	
	private InputStream input;
	private OutputStream output;
	private PrintWriter print;
	private BufferedReader scan;
	
	public InputOutput(InputStream in, OutputStream out) {
		this.input = in;
		this.output = out;
		this.print = new PrintWriter(out, true);
		this.scan = new BufferedReader(new InputStreamReader(in));
	}
	
	/*envoi un Map sur le reseau*/
	public void write(Map<String,String> message) {
		print.println(message.size());
		for(String key:message.keySet()) {
			print.println(key);
			print.println(message.get(key));
		}	
	}
	/*recupere un  Map depuis le reseau*/
	public Map<String,String> read() throws Exception {
		Map<String, String> r = new TreeMap<>();
		String line = scan.readLine();
		if ( line == null ) throw new IOException();
		
		int nb = Integer.parseInt(line);
		for(int i =0; i< nb; i++){
			String key = scan.readLine();
			String value = scan.readLine();
			r.put(key, value);
		}
		return r;
		
	}
	
	public void close()
	{
		try {
			input.close();
			output.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
