package com.ips.data.connection;

import java.sql.*;
import java.util.Vector;

/**

Title:       PooledDBManager
Version:     1.5
Copyright:   Copyright (c) 2000
Author:      dott. Ivan Pasquale
Company:     ITC-irst
<p>Description: Classe di gestione delle connessioni a database SQLSERVER
FeedBack:    pasquale@irst.itc.it

 */
public class PooledDBManager
{

    /** Evita inizializzazioni ulteriori del database*/
    private boolean alive = false;
    protected int TotConnection;
    private String ConnectionString;
    protected String UserName;
    protected String Password;
    protected String ApplicationName;
    private String ClassForName;
    protected Vector PooledConnectionsContainer;
    protected int MAX_CONNECTIONS;
    private int currentPointer;
    protected String DbName;
    protected String ServerName;
    protected int ServerPort;

    public void setServerName(String ServerNameParam)
    {
        ServerName = ServerNameParam;
    }

    public void setServerPort(int ServerPortParam)
    {
        ServerPort = ServerPortParam;
    }

    public void setDbName(String DbNameParam)
    {
        DbName = DbNameParam;
    }

    public void setUserName(String UserNameParam)
    {
        this.UserName = UserNameParam;
    }

    public void setPassword(String PasswordParam)
    {
        Password = PasswordParam;

    }

    public void setApplicationName(String ApplicationNameParam)
    {
        ApplicationName = ApplicationNameParam;
    }

    public PooledDBManager()
    {
        alive = false;
        TotConnection = 0;
        PooledConnectionsContainer = new Vector();
        MAX_CONNECTIONS = 1;
        currentPointer = 0;
    }

    public void initDatabase() throws SQLException, Exception
    {
        if (!isAlive())
        {
            PooledConnectionsContainer.removeAllElements();
            try
            {
                for (int i = 0; i < MAX_CONNECTIONS; i++)
                {
                    //System.out.print(this.ApplicationName+": Attempting to allocate connection "+i+"�");
                    initConnection();
                }
            } catch (SQLException sql)
            {
                throw sql;
            } catch (Exception e)
            {
                throw e;
            }
            alive = true;
            currentPointer = 0;
        }

    }

    public java.sql.Connection getDbConnection() throws SQLException, Exception
    {
        try
        {
            while (true)
            {
                if (PooledConnectionsContainer == null || PooledConnectionsContainer.size() == 0)
                {
                    alive = false;
                    this.initDatabase();
                }
                java.sql.Connection myConnection = (java.sql.Connection) PooledConnectionsContainer.get(currentPointer);
                /* Test integrit� connessione*/
                boolean IsSafe = true;
                if (myConnection == null || myConnection.isClosed())
                {
                    IsSafe = false;
                }
                try
                {
                    String test = "Select 'x' as IsSafe";
                    Statement myStatement = myConnection.createStatement();
                    ResultSet myResult = myStatement.executeQuery(test);
                    myResult.close();
                } catch (SQLException sqle)
                {
                    System.out.print(this.ApplicationName + ": Connections " + currentPointer + "� is unavailable!");
                    IsSafe = false;
                }
                if (IsSafe)
                {
                    //System.out.println("Returning "+currentPointer+"� connection");
                    //System.out.println("Size of PooleConnectionsContanier: "+PooledConnectionsContainer.size());
                    updatePointer();
                    return myConnection;
                } else
                {
                    /* rimuove la connessione dal vettore*/
                    PooledConnectionsContainer.remove(currentPointer);
                    /* istanzia una nuova connessione*/
                    initConnection();
                }
            }

        } catch (SQLException sqle)
        {
            alive = false;
            throw sqle;
        } catch (Exception e)
        {
            alive = false;
            throw e;
        }
    }

    private void initConnection() throws SQLException, Exception
    {
        if (getClassForName() == null || getConnectionString() == null || UserName == null || Password == null)
        {
            throw new SQLException("Il parametri della connessione non sono stati specificati!");
        }
        Class.forName(getClassForName());
        try
        {
            java.sql.Connection myConnection = DriverManager.getConnection(getConnectionString(),UserName, Password);
            if (myConnection != null)
            {
                PooledConnectionsContainer.add(myConnection);
                //System.out.println(".........[OK]");
            }
        } catch (SQLException sql)
        {
            throw sql;
        } catch (Exception e)
        {
            throw e;
        }

    }

    public void resetConnections()
    {
        //System.out.println(this.ApplicationName+": Reset connections");
        for (int i = 0; i < PooledConnectionsContainer.size(); i++)
        {
            java.sql.Connection myConnection = (java.sql.Connection) PooledConnectionsContainer.get(i);
            try
            {
                if (myConnection != null)
                {
                    myConnection.close();
                }
            } catch (SQLException sqle)
            {
                System.out.println(this.ApplicationName + "In reset connection: " + sqle.getMessage());
            }
            PooledConnectionsContainer.removeAllElements();
        }
        alive = false;
        currentPointer = 0;

    }

    private void updatePointer()
    {
        if (currentPointer == PooledConnectionsContainer.size() - 1)
        {
            currentPointer = 0;
        } else
        {
            currentPointer++;
        }
    }

    public int getMAX_CONNECTIONS()
    {
        return MAX_CONNECTIONS;
    }

    public void setMAX_CONNECTIONS(int MAX_CONNECTIONS)
    {
        this.MAX_CONNECTIONS = MAX_CONNECTIONS;
    }

    /**
     * @return the ConnectionString
     */
    public String getConnectionString()
    {
        return ConnectionString;
    }

    /**
     * @param ConnectionString the ConnectionString to set
     */
    public void setConnectionString(String ConnectionString)
    {
        this.ConnectionString = ConnectionString;
    }

    /**
     * @return the alive
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * @return the ClassForName
     */
    public String getClassForName()
    {
        return ClassForName;
    }

    /**
     * @param ClassForName the ClassForName to set
     */
    public void setClassForName(String ClassForName)
    {
        this.ClassForName = ClassForName;
    }
}



