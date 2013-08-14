package com.ips.util;


import java.sql.*;

public class Reporter
{
	public String Source;
    public String Ascending;
    public ResultSet myResultSet = null;
    public String OrderBy;
    public String [][] Result;
    public int MatrixColumn;
    public int MatrixRow;
	public Reporter()
	{
		Source ="";
        Ascending ="Asc";
	}
	public void setAscending(boolean asc)
    {
        if(asc)
            Ascending = "Asc";
        else
            Ascending ="Desc";
    }
	public void getList(java.sql.Connection conn, String OrderByParam)
	throws java.sql.SQLException
{
	try
	{
			String IsRecordedQuery = "Select * from " + Source +" order by " + OrderByParam + " " + Ascending;
			Statement myStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			myResultSet = myStmt.executeQuery(IsRecordedQuery);
	}
	catch (java.sql.SQLException sqlexception)
	{
		throw sqlexception;
	}
}

	public void getList(java.sql.Connection conn)
	throws java.sql.SQLException
{
	try
	{
			String IsRecordedQuery = "Select * from " + Source +";";
			Statement myStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			myResultSet = myStmt.executeQuery(IsRecordedQuery);

	}
	catch (java.sql.SQLException sqlexception)
	{
		throw sqlexception;
	}
}


	public void getList(java.sql.Connection conn, String WhereParam, String OrderByParam)
	throws java.sql.SQLException
{
	try
	{

			String IsRecordedQuery = "Select * from " + Source + " where " + WhereParam+" order by " + OrderByParam + " " + Ascending;
			Statement myStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			myResultSet = myStmt.executeQuery(IsRecordedQuery);
	}
	catch (java.sql.SQLException sqlexception)
	{
		throw sqlexception;
	}
}


}
