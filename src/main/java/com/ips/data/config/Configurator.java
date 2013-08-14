package com.ips.data.config;

import java.io.*;
import java.util.Properties;

public class Configurator
{

    private String PropertiesFile;
    private Properties properties;
    public boolean isReady = false;
    FileInputStream myFile;

    public Configurator()
    {
    }

    public void setPropertiesFile(String PropertiesFileParam)
    {
        PropertiesFile = PropertiesFileParam;
    }

    public void readConfig() throws java.io.IOException
    {
        properties = new Properties();
        try
        {
            properties.load(new FileInputStream(this.PropertiesFile));
            isReady = true;
        } catch (IOException ioe)
        {
            throw ioe;
        }
    }

    public void readConfigFromJar() throws java.io.IOException
    {
        properties = new Properties();
        try
        {
            properties.load(this.getClass().getResourceAsStream(PropertiesFile));
            isReady = true;
        } catch (IOException ioe)
        {
            throw ioe;
        }
    }

    public String get(String PropertyParam)
    {
        return properties.getProperty(PropertyParam);
    }

    public void closeFile() throws java.io.IOException
    {
        try
        {
            myFile.close();
        } catch (IOException ioe)
        {
            throw ioe;
        }
    }
}



