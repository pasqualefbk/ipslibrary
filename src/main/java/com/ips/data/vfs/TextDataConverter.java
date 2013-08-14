/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ips.data.vfs;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author orler
 */
public class TextDataConverter {
  private static Log log = LogFactory.getLog(TextDataConverter.class);
  
  private boolean printHeader; 
  private String fieldSeparator;
  private String lineSeparator;
  private String fieldEnclose;
  
  public TextDataConverter() {
    printHeader = true;
    fieldSeparator = ", ";
    lineSeparator = System.getProperty("line.separator");
    fieldEnclose = "";
  }
  

  public boolean resultSetToTxtFile(ResultSet rs, String destination) throws Exception {
    PrintWriter writer = null;
    ResultSetMetaData meta = null;
    int columnCount = -1;
    StringBuilder sb = new StringBuilder();
    String str;

    try {
      log.info("Write to" + destination + ", header " + isPrintHeader() + ", fieldseparator [" + getFieldSeparator() + "]" );
      
      writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(destination)), "UTF-8"));
      
      meta =  rs.getMetaData();
      columnCount = meta.getColumnCount();
      if(isPrintHeader()) {
          for(int i = 1; i <= columnCount; i++) {
            writer.append(getFieldEnclose())
                  .append(meta.getColumnLabel(i))
                  .append(getFieldEnclose());
            if(i != columnCount) {
              writer.append(getFieldSeparator());
            }
          }
          writer.println();
      }
      while(rs.next()) {
          for(int i = 1; i <= meta.getColumnCount(); i++) {
            writer.append(getFieldEnclose())
                   .append(rs.getString(i))
                   .append(getFieldEnclose());
            if(i != columnCount) {
              writer.append(getFieldSeparator());
            }
          }
          writer.println();
      }
      writer.flush();
      return true;
      
    } catch (Exception e) {
      log.error("Error converting to text", e);
      throw e;
    } finally {
      if(writer != null) writer.close();
      writer = null;
      meta = null;
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
   * @return the fieldSeparator
   */
  public String getFieldSeparator() {
    return fieldSeparator;
  }

  /**
   * @param fieldSeparator the fieldSeparator to set
   */
  public void setFieldSeparator(String fieldSeparator) {
    this.fieldSeparator = fieldSeparator;
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
  public String getFieldEnclose() {
    return fieldEnclose;
  }

  /**
   * @param fieldEnclose the fieldEnclose to set
   */
  public void setFieldEnclose(String fieldEnclose) {
    this.fieldEnclose = fieldEnclose;
  }
  
}
