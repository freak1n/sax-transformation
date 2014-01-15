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
    private boolean isDescription = false;
    private boolean isFeatureName = false;
    private boolean isFeatureValue = false;
    private boolean isDimenssion = false;
    private boolean isUnit = false;
    private boolean isCoustValue = false;
    private boolean isValue = false;
    private boolean isCurrency = false;
    private boolean isCost = false;
    
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
        
        if (qName.equals("features")) {
            writer.print(repeat("    ", this.indent));
            writer.print("<features>\n");
        }
        
        if (qName.equals("feature")) {
            this.indent++;
            writer.print(repeat("    ", this.indent));
            writer.print("<feature ");
        }
        
        if (qName.equals("feature-name")) {
            this.isFeatureName = true;
        }
        
        if (qName.equals("feature-value")) {
            this.isFeatureValue = true;
        }
        
        if (qName.equals("dimensions")) {
            writer.print(repeat("    ", this.indent));
            writer.print("<dimensions>\n");
        }
        
        if (qName.equals("length") || qName.equals("width") || qName.equals("height") || qName.equals("weight")) {
            this.isDimenssion = true;
            writer.print(repeat("    ", this.indent+1));
            writer.print("<"+qName+" ");
        }
        
        if (qName.equals("unit")) {
            this.isUnit = true;
        }
        
        if (qName.equals("value")) {
            this.isValue = true;
        }
        
         if (qName.equals("price")) {
            writer.print(repeat("    ", this.indent));
            writer.print("<price ");
        }
         
         if (qName.equals("currency")) {
             this.isCurrency = true;
         }
         
        if (qName.equals("cost")) {
            this.isCost = true;
        }
    }
       
    @Override
    public void endElement(String nameSpaceURI, String localName, String qName) {
        if (qName.equals("products")) {
            writer.print("</"+qName+">");
        }
        
        if (qName.equals("description")) {
            writer.print(repeat("    ", this.indent));
            writer.print("</"+qName+">\n");
        }
        
        if (qName.equals("product")) {
           this.indent--;
           writer.print('\n'+repeat("    ", this.indent));
           writer.print("</product>\n");
           this.indent--;
        }
        
        if (qName.equals("features")) {
            writer.print(repeat("    ", this.indent));
            writer.print("</features>\n");
        }
        
        if (qName.equals("feature")) {
            writer.print(repeat("    ", this.indent));
            writer.print("</feature>\n");
            this.indent--;
        }
        
        if (qName.equals("dimensions")) {
            writer.print(repeat("    ", this.indent));
            writer.print("</dimensions>\n");
        }
        
        if (qName.equals("length") || qName.equals("width") || qName.equals("height") || qName.equals("weight")) {
            this.isDimenssion = true;
            writer.print("<"+qName+">\n");            
        }
        
        if (qName.equals("price")) {
            writer.print("</price>");
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
        String tmpString = "";
        
        for (int i=start;i<(start+length);i++) {
            tmpString += ch[i];
        }
        
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
            isDescription = false;
        }
        
        if (this.isFeatureName) {
            this.printStrToAttr("name", tmpString);
            writer.print(">\n");
            this.isFeatureName = false;
        }
        
        if (this.isFeatureValue) {
            writer.print(repeat("    ", this.indent+1));
            writer.print(tmpString + "\n");
            isFeatureValue = false;
        }
        
        if (this.isUnit && this.isDimenssion) {
            this.printStrToAttr("unit", tmpString);
            this.isUnit = false;
            writer.print(">");
        }
        
        if (this.isCoustValue && this.isDimenssion) {
            writer.print(tmpString);
            this.isCoustValue = false;
        }
        
        if (this.isValue) {
            writer.print(tmpString);
            this.isValue = false;
        }
        
        if (this.isCurrency) {
            this.printStrToAttr("currency", "BGN");
            writer.print(">");
            this.isCurrency = false;
        }
        
        if (this.isCost) {
            writer.print(tmpString);
            this.isCost = false;
        }
    }
    
    public void printStrToAttr (String attrName, String attrValue) {
        writer.print(attrName + "=\"" + attrValue + "\"");
        if ( ! attrName.equals("model") &&  ! attrName.equals("name") && ! attrName.equals("unit") && ! attrName.equals("currency")) {
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
