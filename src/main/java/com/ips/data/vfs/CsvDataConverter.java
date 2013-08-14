/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ips.data.vfs;


// LIBRARY NOT PRESENT IN MAVEN
//import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author orler
 */
public class CsvDataConverter {
  private static Log log = LogFactory.getLog(CsvDataConverter.class); 
  
  private boolean printHeader;
  private char fieldSeparator;
  private String lineSeparator;
  private char fieldEnclose;
  
  public CsvDataConverter () {
    printHeader = true;
    fieldSeparator = ',';
    lineSeparator = System.getProperty("line.separator");
    fieldEnclose = '\"';
  }
  /*

  public boolean writeFile(ResultSet rs, String destination) throws Exception {
    CSVWriter  writer = null;
    try {
      log.info("Write to: " + destination + ", header " + isPrintHeader() + ", fieldseparator [" + getFieldSeparator() + "]" );
      
      writer = new CSVWriter(new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(destination)), "UTF-8")),getFieldSeparator(),getFieldEnclose(),getLineSeparator());
      writer.writeAll(rs, isPrintHeader());
      return true;
      
    } catch (Exception e) {
      log.error("Error converting to text", e);
      throw e;
    } finally {
      if(writer != null) {
        writer.close();
      }
      writer = null;
    }
  }

  /**
   * @return the printHeader
   */
  public boolean isPrintHeader() {
    return printHeader;
  }

  /**
   * @param printHeader the printHeader to set
   */
  public void setPrintHeader(boolean printHeader) {
    this.printHeader = printHeader;
  }
  
  /**
   * @param printHeader the printHeader to set
   */
  public void setPrintHeader(Boolean printHeader) {
    this.printHeader = printHeader.booleanValue();
  }

  /**
   * @return the fieldSeparator
   */
  public char getFieldSeparator() {
    return fieldSeparator;
  }

  /**
   * @param fieldSeparator the fieldSeparator to set
   */
  public void setFieldSeparator(char fieldSeparator) {
    this.fieldSeparator = fieldSeparator;
  }
  
   /**
   * @param fieldSeparator the fieldSeparator to set
   */
  public void setFieldSeparator(String fieldSeparator) {
     if(fieldSeparator == null) {
      this.fieldSeparator = Character.UNASSIGNED;
      return;
    }
    if(fieldSeparator.equals("")) {
      this.fieldSeparator = Character.UNASSIGNED;
      return;
    }
    this.fieldSeparator = fieldSeparator.charAt(0);
  }

  /**
   * @return the lineSeparator
   */
  public String getLineSeparator() {
    return lineSeparator;
  }

  /**
   * @param lineSeparator the lineSeparator to set
   */
  public void setLineSeparator(String lineSeparator) {
    this.lineSeparator = lineSeparator;
  }

  /**
   * @return the fieldEnclose
   */
  public char getFieldEnclose() {
    return fieldEnclose;
  }

  /**
   * @param fieldEnclose the fieldEnclose to set
   */
  public void setFieldEnclose(char fieldEnclose) {
    this.fieldEnclose = fieldEnclose;
  } 
  
    /**
   * @param fieldEnclose the fieldEnclose to set
   */
  public void setFieldEnclose(String fieldEnclose) {
    if(fieldEnclose == null) {
      this.fieldEnclose = Character.UNASSIGNED;
      return;
    }
    if(fieldEnclose.equals("")) {
      this.fieldEnclose = Character.UNASSIGNED;
      return;
    }
    this.fieldEnclose = fieldEnclose.charAt(0);
  } 
}
