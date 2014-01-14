package saxtesting_georgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlConverter {
	public static String iFileName;
	public static String oFileName;
	public static char iFileType;
	
	public static void main(String[] args) throws IOException, SAXException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input file: ");
        iFileName = br.readLine();
		System.out.print("Output file: ");
        oFileName = br.readLine();
		System.out.print("Input file type: ");
        iFileType = br.readLine().toCharArray()[0];
		System.out.println("Parsing: " +iFileName + ". Type " + iFileType + " file.");
		
		
		XMLReader p = XMLReaderFactory.createXMLReader();
		if (iFileType == 'A' || iFileType == 'a'){
			p.setContentHandler(new BtoAHandler()); // TODO: Change the handler
		}
		if (iFileType == 'B' || iFileType == 'b'){
			p.setContentHandler(new BtoAHandler());
		}
		else{
			throw new IllegalArgumentException("Input file type should be either A or B!");
		}
		p.parse(iFileName);
	}
}
