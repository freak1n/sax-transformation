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
    private boolean isAddedOn = false;
    private boolean isCategory = false;
    private boolean isProducer = false;
    private boolean isModel = false;
    private boolean isDescription;
    
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
    public void startElement(String nameSpaceURI, String localName, String qName, Attributes attr) {
        if (qName.equals("products")) {
            writer.print("<products>");
        }
        
        if (qName.equals("product")) {
           this.indent++;
           writer.print('\n'+repeat("    ", this.indent));
           writer.print("<product id=\""+attr.getValue("id")+"\" ");
        }
        
        if (qName.equals("added-on")) {
            this.isAddedOn = true;
        }
        
        if (qName.equals("category")) {
            this.isCategory = true;
        }
        
        if (qName.equals("producer")) {
            this.isProducer = true;
        }
        
        if (qName.equals("model")) {
            this.isModel = true;
        }
        
        if (qName.equals("description")) {
            this.isDescription = true;
            this.indent++;
            writer.print(repeat("    ", this.indent));
            writer.print("<description>\n");
        }
    }
       
    @Override
    public void endElement(String nameSpaceURI, String localName, String qName) {
        if (qName.equals("products")) {
            writer.print("</"+qName+">");
        }
        
        if (qName.equals("description")) {
            
            writer.print(repeat("    ", this.indent));
            writer.print("</"+qName+">");
        }
        
        if (qName.equals("product")) {
           this.indent--;
           writer.print('\n'+repeat("    ", this.indent));
           writer.print("</product>\n");
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
        String tmpString = "";
        
        for (int i=start;i<(start+length);i++) {
            tmpString += ch[i];
        }
	
        System.out.println(tmpString);
        
        if (this.isAddedOn) {
            this.printStrToAttr("added-on", tmpString);
            this.isAddedOn = false;
        }
        
        if (this.isCategory) {
            this.printStrToAttr("category", tmpString);
            this.isCategory = false;
        }
        
        if (this.isProducer) {
            this.printStrToAttr("producer", tmpString);
            this.isProducer = false;
        }
        
        if (this.isModel) {
            this.printStrToAttr("model", tmpString);
            this.isModel = false;
            writer.print(">\n");
        }
        
        if (this.isDescription) {
            writer.print(repeat("    ", this.indent+1));
            writer.print(tmpString + "\n");
        }
    }
    
    public void printStrToAttr (String attrName, String attrValue) {
        writer.print(attrName + "=\"" + attrValue + "\"");
        if ( ! attrName.equals("model")) {
            writer.print(" ");
        }
    }
    
    public String repeat(String str, int times){
        if (times < 1){
            return "";
        }
        return str + repeat(str, times-1);
    }
}
