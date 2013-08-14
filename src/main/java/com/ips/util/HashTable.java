package com.ips.util;

import com.ips.data.type.SQL_TYPE;
import com.ips.data.PreparedAtomContainer;
import java.sql.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HashTable extends PreparedAtomContainer
{
   private final int version = 10;
    public HashTable() throws Exception
    {
        super();
        try
        {

            super.setTableName("MAIN_CONFIG");            
            super.add("Name", SQL_TYPE.STRING_TYPE, false, false);
            super.add("Value", SQL_TYPE.STRING_TYPE, false, false);
            super.addPrimaryKey("Name");
        } catch (Exception e)
        {
            throw e;
        }

    }

    public String get(String KeyParam)
            throws java.sql.SQLException, Exception
    {
        try
        {
            String KeyValue = "Key not found!";
            this.set("Name", KeyParam);
            if(this.read())
                KeyValue = this.getString("Value");
            return KeyValue;
        } catch (java.sql.SQLException sqlexception)
        {
            throw sqlexception;
        } catch (Exception e)
        {
            throw e;
        }

    }
}





