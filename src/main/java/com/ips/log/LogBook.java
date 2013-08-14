package com.ips.log;


import java.io.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.sql.SQLException;
import java.lang.Exception;


public final class LogBook
{
   private final int version = 10;
   LogBook()
   {
      super();
   }
   public static synchronized void record(SQLException sql)
   {
      try
      {
         Date Now = new Date();
         FileOutputStream outStream = new FileOutputStream("LogBook.log",true);
         PrintStream Log = new PrintStream(outStream);
         String Record= Now.toString();
         Log.println(Record);
         Record = "Codice:"+ sql.getErrorCode()+"descr:";
         Log.println(Record);
         sql.printStackTrace(Log);
         Log.close();

      }
      catch(IOException ioe)
      {
         System.out.println(ioe.getMessage());
      }
   }
   public static synchronized void record(Exception e)
   {
      try
      {
         Date Now = new Date();
         FileOutputStream outStream = new FileOutputStream("LogBook.log",true);
         PrintStream Log = new PrintStream(outStream);
         String Record= "\n"+Now.toString();
         Log.println(Record);
         e.printStackTrace(Log);
         Log.close();

      }
      catch(IOException ioe)
      {
         System.out.println(ioe.getMessage());
      }

   }
   public static synchronized void record(IOException ioe)
   {
      try
      {
         Date Now = new Date();
         FileOutputStream outStream = new FileOutputStream("LogBook.log",true);
         PrintStream Log = new PrintStream(outStream);
         String Record= "\n"+ Now.toString();
         Log.println(Record);
         ioe.printStackTrace(Log);
         Log.close();
      }
      catch(IOException ioe2)// errore di scrittura su LogBook
      {
         System.out.println(ioe2.getMessage());
      }

   }

   public static synchronized void record(String param1)
   {
      try
      {
         Date Now = new Date();
         FileOutputStream outStream = new FileOutputStream("LogBook.log",true);
         PrintStream Log = new PrintStream(outStream);
         String Record= "\n"+ Now.toString()+" : " + param1;
         Log.println();
         Log.print(Record);
         Log.close();
      }
      catch(IOException ioe)
      {
         System.out.println(ioe.getMessage());
      }

   }
   public static synchronized void record(String FileSystemPath,String param1)
   {
      try
      {
         Date Now = new Date();
         FileOutputStream outStream = new FileOutputStream(FileSystemPath,true);
         PrintStream Log = new PrintStream(outStream);
         String Record= "\n"+ Now.toString()+" : " + param1;
         Log.println();
         Log.print(Record);
         Log.close();
      }
      catch(IOException ioe)
      {
         System.out.println(ioe.getMessage());
      }

   }




 // End of class LogBook
}
