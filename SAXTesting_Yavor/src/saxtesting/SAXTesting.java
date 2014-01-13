/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saxtesting;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
/**
 *
 * @author Betty
 */
public class SAXTesting extends DefaultHandler {

    public static String iFileName = "../example-documents/example_A.xml";
    public static String oFileName = "result.xml";
    
    public static void main(String[] args) throws Exception {
        System.out.println("Here We Go ..");
        
        XMLReader p = XMLReaderFactory.createXMLReader();
        
	p.setContentHandler(new AtoBHandler());
	p.parse(iFileName);
    }
    
    public void read(String fileName) throws Exception {
        XMLReader readerObj = XMLReaderFactory.createXMLReader();
        readerObj.setContentHandler(this);
        readerObj.parse(fileName);
    }
    
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Starting");
    }
    
    @Override
    public void endDocument() throws SAXException {
        System.out.println("...Finished");
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException {
        System.out.println("Element is " + qName);
    }
    
    @Override
    public void endElement(String nameSpaceURI, String localName, String qName) {
        System.out.println("Hello");
    }
}
