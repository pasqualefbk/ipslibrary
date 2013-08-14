/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ips.util;
import java.io.File;
/**
 *
 * @author pasquale
 */
public class UploadedFile {


    public UploadedFile(String dir, String filename, String type)
    {
        this.dir = dir;
        this.filename = filename;
        this.type = type;
    }

    public String getContentType()
    {
        return type;
    }

    public File getFile()
    {
        if(dir == null || filename == null)
            return null;
        else
            return new File(dir + File.separator + filename);
    }

    public String getFilesystemName()
    {
        return filename;
    }

    private String dir;
    private String filename;
    private String type;
}
