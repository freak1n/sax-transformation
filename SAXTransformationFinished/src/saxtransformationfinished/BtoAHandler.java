package saxtransformationfinished;

import saxtransformationfinished.SAXTransformator;
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
	private boolean isAfterTextNode = false;
	private boolean isDimention = false;
	private boolean isFeature = false;
	
	public void startDocument(){
		try {
			writer = new PrintWriter(SAXTransformator.oFileName, "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<!DOCTYPE products SYSTEM \"example_A.dtd\">");
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

		if(isDimention){
			writer.print("<" + qName + ">");
			convertAttributeToElement(atts, "unit");
			writer.print("\n" + repeat("    ", indent+1) + "<value>");
		}
		else if(isFeature){
			writer.println("<" + qName + ">");
			if(atts.getValue("name") != null){
				writer.println(repeat("    ", indent+1) + "<feature-name>" + atts.getValue("name") + "</feature-name>");
			}
			writer.print(repeat("    ", indent+1) + "<feature-value>");
		}
		else if(qName.endsWith("price")){
			writer.println("<" + qName + ">");
			if(atts.getValue("currency") != null){
                            writer.println(repeat("    ", indent+1) + "<currency>" + atts.getValue("currency") + "</currency>");
			}
			writer.print(repeat("    ", indent+1) + "<cost>");
		}
		else{
			writer.print("<"+qName);
			if(atts.getValue("id") != null){
				writer.print(" id=\"" + atts.getValue("id") + "\"");
			}
			writer.print(">");

			convertAttributeToElement(atts, "added-on");
			convertAttributeToElement(atts, "category");
			convertAttributeToElement(atts, "producer");
			convertAttributeToElement(atts, "model");
		}
		
		if (qName.endsWith("dimensions")){
                    isDimention = true;
		}
		else if(qName.endsWith("features")){
                    isFeature = true;
		}
		indent++;
	}
	public void endElement(String nameSpaceURI, String localName, String qName) {
		indent--;
		if (qName.endsWith("dimensions")){
			isDimention = false;
			isAfterTextNode = false;
		}
		else if(qName.endsWith("features")){
			isFeature = false;
			isAfterTextNode = false;
		}
	
		if (isFeature){
			writer.print("</feature-value>");
			writer.print('\n'+repeat("    ", indent));
		}
		else if (isDimention){
			writer.print("</value>");
			writer.print('\n'+repeat("    ", indent));
		}
		else if(qName.endsWith("price")){
			writer.print("</cost>");
			writer.print('\n'+repeat("    ", indent));
			isAfterTextNode = false;
		}
		else{
			if (!isAfterTextNode){
				writer.print('\n'+repeat("    ", indent));
			}
			else{
				isAfterTextNode = false;
			}
		}
		writer.print("</"+qName+">");
	}
	public void characters(char[] ch, int start, int length) {
		for (int i=start;i<(start+length);i++)
		{
			writer.print(ch[i]);
		}
		isAfterTextNode = true;
	}
	
	public String repeat(String str, int times){
		if (times < 1){
			return "";
		}
		return str + repeat(str, times-1);
	}
	
	public void convertAttributeToElement(Attributes atts, String qName){
		if(atts.getValue(qName) != null){
			writer.print('\n' + repeat("    ", indent+1) + "<" + qName +">" + atts.getValue(qName) + "</" + qName + ">");
		}
	}
}
