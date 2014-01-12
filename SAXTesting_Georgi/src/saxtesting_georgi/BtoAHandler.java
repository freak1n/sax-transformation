package saxtesting_georgi;

import saxtesting_georgi.XmlConverter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class BtoAHandler extends DefaultHandler {
	
	private PrintWriter writer;
	private int indent = 0;
	private boolean afterTextNode = false;
	
	public void startDocument(){
		try {
			writer = new PrintWriter(XmlConverter.oFileName, "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(BtoAHandler.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(BtoAHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void endDocument() {
            writer.close();
	}
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
		writer.print('\n'+repeat("    ", indent));
		writer.print("<"+qName);
		for (int i = 0; i < atts.getLength(); i++) {
			writer.print(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
		}
		writer.print(">");
		indent++;
	}
	public void endElement(String nameSpaceURI, String localName, String qName) {
		indent--;
		if (!afterTextNode){
			writer.print('\n'+repeat("    ", indent));
		}
		else{
			afterTextNode = false;
		}
		writer.print("</"+qName+">");
	}
	public void characters(char[] ch, int start, int length) {
		for (int i=start;i<(start+length);i++)
		{
			writer.print(ch[i]);
		}
		afterTextNode = true;
	}
	
	public String repeat(String str, int times){
		if (times < 1){
			return "";
		}
		return str + repeat(str, times-1);
	}
}
