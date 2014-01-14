/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saxtesting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class AtoBHandler extends DefaultHandler {
    private PrintWriter writer;
    private int indent = 0;
    
    @Override
    public void startDocument() {
            try {
                    writer = new PrintWriter(SAXTesting.oFileName, "UTF-8");
                    writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            } 
            catch (FileNotFoundException ex) {
                    Logger.getLogger(AtoBHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(AtoBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override        
    public void endDocument() {
        writer.close();
    }
    
    @Override
    public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
        
    }
    
    @Override
    public void endElement(String nameSpaceURI, String localName, String qName) {
        
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
        
    }
    
    public String repeat(String str, int times){
        if (times < 1){
                return "";
        }
        return str + repeat(str, times-1);
    }
}
