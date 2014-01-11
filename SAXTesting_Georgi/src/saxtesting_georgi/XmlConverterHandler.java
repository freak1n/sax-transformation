import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XmlConverterHandler extends DefaultHandler {
	
	private PrintWriter writer;
	
	public void startDocument(){
		try {
			writer = new PrintWriter(XmlConverter.oFileName, "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(XmlConverterHandler.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(XmlConverterHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void endDocument() {
		writer.close();
	}
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
		writer.print("<"+qName);
		for (int i = 0; i < atts.getLength(); i++) {
			writer.print(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
		}
		writer.print(">");
	}
	public void endElement(String nameSpaceURI, String localName, String qName) {
		writer.print("</"+qName+">");
	}
	public void characters(char[] ch, int start, int length) {
		for (int i=start;i<(start+length);i++)
		{
			writer.print(ch[i]);
		}
	}
}
