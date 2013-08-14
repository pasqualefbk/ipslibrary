/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ips.data.vfs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

/**
 *
 * @author orler
 */
public class VfsManager {
  private static Log log = LogFactory.getLog(VfsManager.class);
  private DefaultFileSystemManager fsm;
  
  private boolean ftpPassive; 
  private boolean ftpUserDirIsRoot;
  private int ftpDataTimeout;
  private boolean overwrite;
  
  public VfsManager() {
        ftpPassive = true;
        ftpUserDirIsRoot = true;
        ftpDataTimeout = 18000;    
        overwrite = true;
  }
  
  public boolean copyFile(String source, String destination) throws Exception {
    try {
      fsm = (DefaultFileSystemManager)VFS.getManager();
      FileSystemOptions sourceOpts = new FileSystemOptions();
      FileSystemOptions destOpts = new FileSystemOptions();
      
      
      if(destination.startsWith("ftp")) {
        setFtpOptions(sourceOpts);
      }
      
      if(destination.startsWith("ftp")) {
        setFtpOptions(destOpts);
      }
      
      //init source and destination files
      log.info("Init files: " + cleanUrl(source) + " > " + cleanUrl(destination));
      FileObject sourceFile = fsm.resolveFile(source, sourceOpts);
      FileObject destinationFile = fsm.resolveFile(destination,destOpts);
      
      if(!isOverwrite() && destinationFile.exists()) {
        throw new Exception("File already exists");
      }
      
      
      //copy file
      log.debug("Copy Start");
      destinationFile.copyFrom(sourceFile, Selectors.SELECT_SELF);
      log.debug("Copy End");
      //uninit files
      sourceFile.close();
      destinationFile.close();
      fsm.closeFileSystem(sourceFile.getFileSystem());
      fsm.closeFileSystem(destinationFile.getFileSystem());
      fsm.freeUnusedResources();
      fsm = null;
      
      return true;
    } catch (Exception e) {
      log.error("File copy error", e);
      throw e;
    }
  }
  
  private String cleanUrl(String s) {
    if(s.contains("//") && s.contains("@")) {
      s = s.replace(s.substring(s.indexOf("//")+2, s.indexOf("@")), "**"); 
    }
    return s;
  }
 
  private void setFtpOptions(FileSystemOptions opts) {
     FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, isFtpPassive());
     FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, isFtpUserDirIsRoot());
     FtpFileSystemConfigBuilder.getInstance().setDataTimeout(opts, getFtpDataTimeout());   
  }

  /**
   * @return the ftpPassive
   */
  public boolean isFtpPassive() {
    return ftpPassive;
  }

  /**
   * @param ftpPassive the ftpPassive to set
   */
  public void setFtpPassive(boolean ftpPassive) {
    this.ftpPassive = ftpPassive;
  }

  /**
   * @return the ftpUserDirIsRoot
   */
  public boolean isFtpUserDirIsRoot() {
    return ftpUserDirIsRoot;
  }

  /**
   * @param ftpUserDirIsRoot the ftpUserDirIsRoot to set
   */
  public void setFtpUserDirIsRoot(boolean ftpUserDirIsRoot) {
    this.ftpUserDirIsRoot = ftpUserDirIsRoot;
  }

  /**
   * @return the ftpDataTimeout
   */
  public int getFtpDataTimeout() {
    return ftpDataTimeout;
  }

  /**
   * @param ftpDataTimeout the ftpDataTimeout to set
   */
  public void setFtpDataTimeout(int ftpDataTimeout) {
    this.ftpDataTimeout = ftpDataTimeout;
  }

  /**
   * @return the overwrite
   */
  public boolean isOverwrite() {
    return overwrite;
  }

  /**
   * @param overwrite the overwrite to set
   */
  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }
}
