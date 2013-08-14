/*
 * @author Ivan Pasquale
 * 
 */
package com.ips.data.transactioncoordinator;

import com.ips.data.PreparedAtomContainer;
import com.ips.data.config.Configurator;
import com.ips.data.util.StringFunc;
//import com.ips.data.vfs.CsvDataConverter;
//import com.ips.data.vfs.VfsManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author pasquale
 */
public class TransactionCoordinator {
    private static Log log = LogFactory.getLog(TransactionCoordinator.class);
    
    protected boolean Diagnostic = false;
    protected Configurator myConfigurator;
    public final boolean UPDATE_IF_EXIST = true;
    public final boolean DO_NOT_UPDATE_IF_EXITS = false;
    public final boolean TRUNCATE_TABLE = true;
    public final boolean DO_NOT_TRUNCATE_TABLE = false;
    public final boolean INSERT_IF_NOT_EXIST = true;
    public final boolean DO_NOT_INSERT_IF_NOT_EXIST = false;
    private int RecordsInserted = 0;
    private int RecordsUpdated = 0;
    private int RecordsRead = 0;
    private boolean performTruncateTable = false;
    private boolean performUpdateRecord = false;
    private boolean performInsertRecord = false;
    private boolean SHOW_RECORD = false;
    private final int version = 21;
    DatabaseMetaData dmdFrom = null;
    DatabaseMetaData dmdTo = null;
    
    private String sourceFilter = null;
    private Object[] sourceParams;
    private boolean haltOnError = false;
    
    
    public TransactionCoordinator() {
        this.setSHOW_RECORD(true);
    }

   /* public void replicateDATACsv(Connection ConnectionFrom, String SchemaFrom, String TableFrom, String fileTo) throws Exception {
      replicateDATACsv(ConnectionFrom, SchemaFrom, TableFrom, fileTo, null, null, null, null);
    }*/
    
    /*public void replicateDATACsv(Connection ConnectionFrom, String SchemaFrom, String TableFrom, String fileTo, Boolean printHeader, String fieldSeparator, String fieldEnclose, String lineSeparator) throws Exception {
      try {
        PreparedAtomContainer SourceTable = allocateSourceTable(ConnectionFrom, SchemaFrom, TableFrom, null);
        ResultSet rs = SourceTable.getList(ConnectionFrom);
        
        CsvDataConverter csv = new CsvDataConverter();
        if(printHeader!= null) {
          csv.setPrintHeader(printHeader);
        }
        if(fieldSeparator != null) {
          csv.setFieldSeparator(fieldSeparator);
        }
        if(fieldEnclose != null) {
          csv.setFieldEnclose(fieldEnclose);
        }
        if(lineSeparator != null) {
          csv.setLineSeparator(lineSeparator);
        }
        
        csv.writeFile(rs, fileTo);
        
      } catch (Exception e) {
        throw e;
      } finally {
        
      }
      
    }
    
    public void replicateFile(String source, String destination, Boolean overwrite) throws Exception {
      try {
        VfsManager vfs = new VfsManager();
        if(overwrite != null) {
          vfs.setOverwrite(overwrite);
        }
        vfs.copyFile(source, destination);
      } catch (Exception e) {
        throw e;        
      }
    }*/
    
    /** public entry point */
    public void replicateDATA(Connection ConnectionFrom, String SchemaFrom, String TableFrom, Connection ConnectionTo, String SchemaTo, String TableTo, boolean truncateTable, boolean UpdateParam, boolean InsertParam) throws Exception, SQLException {

        try {
            this.replicateDATA(ConnectionFrom, SchemaFrom, TableFrom, null, ConnectionTo, SchemaTo, TableTo, null, truncateTable, UpdateParam, InsertParam);
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }

    }

    /** public entry point */
    public void replicateDATA(Connection ConnectionFrom, String SchemaFrom, String TableFrom, String PrimaryKeysFrom, Connection ConnectionTo, String SchemaTo, String TableTo, String PrimaryKeysTo, boolean truncateTable, boolean UpdateParam, boolean InsertParam) throws SQLException, Exception {
        try {

            /* DEFINE THE REPLICATION BEHAVIOUR*/
            this.setActions(truncateTable, UpdateParam, InsertParam);

            /* ALLOCATE THE SOURCE AND DESTIONATION SQL OBJECT */
            PreparedAtomContainer SourceTable = allocateSourceTable(ConnectionFrom, SchemaFrom, TableFrom, PrimaryKeysFrom);
            PreparedAtomContainer DestinationTable = allocateDestinationTable(ConnectionTo, SchemaTo, TableTo, PrimaryKeysTo);

            /* EXCLUDE FIELD THAT AREN'T PRESENTE IN BOTH TABLE */
            mapSourceToDestination(SourceTable, DestinationTable);

            /* EXECUTE ALL THE TRANSACTIONS */
            performTransaction(SourceTable, DestinationTable);

        } catch (java.sql.SQLException sqle) {
            notifyMessage(sqle);
            throw sqle;
        } catch (Exception e) {
            notifyMessage(e);
            throw e;
        }
    }

    private void setActions(boolean truncateTable, boolean UpdateParam, boolean InsertParam) {
        if (truncateTable == this.TRUNCATE_TABLE) {
            performTruncateTable = true;
        } else {
            performTruncateTable = false;
        }
        if (UpdateParam == this.UPDATE_IF_EXIST) {
            performUpdateRecord = true;
        } else {
            performUpdateRecord = false;
        }
        if (InsertParam == INSERT_IF_NOT_EXIST) {
            performInsertRecord = true;
        } else {
            performInsertRecord = false;
        };

    }

    private void resetActions() {
        performTruncateTable = false;
        performUpdateRecord = false;
        performInsertRecord = false;
    }

    private void mapSourceToDestination(PreparedAtomContainer SourceTable, PreparedAtomContainer DestinationTable) throws Exception {
        try {
            for (String FromColumnName : SourceTable.getColumnName()) {
                if (DestinationTable.containsColumnName(FromColumnName) == false) {
                    log(FromColumnName + " is not present in destination table, will be ignored");
                }
            }
            String[] DestinationColumnName = DestinationTable.getColumnName();

            /* esclude column that are not presents in source table*/
            for (String DestColumnName : DestinationColumnName) {
                if (StringFunc.match(SourceTable.getColumnName(), DestColumnName) == -1) {
                    /* do not remove primary key*/
                    log(DestColumnName + " is not present in source table, will be excluded.");
                    DestinationTable.remove(DestColumnName);
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private PreparedAtomContainer allocateDestinationTable(Connection ConnectionTo, String SchemaTo, String TableTo, String PrimaryKeysTo) throws SQLException, Exception {
        PreparedAtomContainer DestinationTable = null;
        try {
            DestinationTable = new PreparedAtomContainer();
            DestinationTable.setConnection(ConnectionTo);
            DestinationTable.setCatalog(ConnectionTo.getCatalog());
            DestinationTable.setSchema(SchemaTo);
            DestinationTable.setTableName(TableTo);
            dmdTo = ConnectionTo.getMetaData();
            String CatalogSeparator = DestinationTable.getCatalogSeparator(dmdTo);
            /* set the idenfifier quote string from destination table*/
            DestinationTable.setIdentifierQuoteString(dmdTo.getIdentifierQuoteString());
            log("Autodiscover " + dmdTo.getDatabaseProductName() + " - " + DestinationTable.getTableName());
            DestinationTable.setDiagnostic(this.isDiagnostic());
            DestinationTable.autodiscoverFields();
            /** add primary key specify by the user*/
            if (PrimaryKeysTo != null) {
                DestinationTable.RemoveAllPrimaryKeys();
                String[] PrimaryKeys = PrimaryKeysTo.split(";");
                for (String key : PrimaryKeys) {
                    DestinationTable.addPrimaryKey(key);
                }
            }
            if (DestinationTable.getPrimaryKeyCount() == 0) {
                throw new SQLException("The destination table that you've provided doesn't contain a primary key or unique identifier. Please fix in with .addPrimaryKey(String PrimaryKey)");
            }
            return DestinationTable;

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }

    }

    private PreparedAtomContainer allocateSourceTable(Connection ConnectionFrom, String SchemaFrom, String TableFrom, String PrimaryKeysFrom) throws SQLException, Exception {
        PreparedAtomContainer SourceTable = null;
        try {
            // allocate a new istance of PreparedAtomContanier
            SourceTable = new PreparedAtomContainer();
            SourceTable.setConnection(ConnectionFrom);
            SourceTable.setCatalog(ConnectionFrom.getCatalog());
            SourceTable.setSchema(SchemaFrom);
            SourceTable.setTableName(TableFrom);
            SourceTable.setDiagnostic(this.isDiagnostic());
            dmdFrom = ConnectionFrom.getMetaData();
            SourceTable.autodiscoverFields();
            if (PrimaryKeysFrom != null) {
                SourceTable.RemoveAllPrimaryKeys();
                String[] PrimaryKeys = PrimaryKeysFrom.split(";");
                for (String key : PrimaryKeys) {
                    SourceTable.addPrimaryKey(key);
                }
            }

            return SourceTable;

        } catch (SQLException sqle) {
            this.notifyMessage(sqle);
            throw sqle;
        } catch (Exception e) {
            this.notifyMessage(e);
            throw e;
        }

    }

    public void notifyMessage(Exception e) {
        if (isDiagnostic()) {
            log.error("Transaction Error", e);
            //log(e.getMessage());
            //e.printStackTrace();
        } else {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stacktrace = sw.toString();

            com.ips.mail.MailManager myBlue = new com.ips.mail.MailManager();
            myBlue.setDestination(myConfigurator.get("AdministratorsEmail"));
            myBlue.setMailHost(myConfigurator.get("MailServer"));
            myBlue.setSubject("Transaction coordinator: error!");
            myBlue.setSource(myConfigurator.get("SenderMailNameProgram"));
            myBlue.setContent(stacktrace);
            myBlue.sendMessage();
        }
    }

    public void notifyMessage(String Message) {
        if (isDiagnostic()) {
            log(Message);
        } else {

            com.ips.mail.MailManager myBlue = new com.ips.mail.MailManager();
            myBlue.setDestination(myConfigurator.get("AdministratorsEmail"));
            myBlue.setMailHost(myConfigurator.get("MailServer"));
            myBlue.setSubject("Transaction coordinator: error!");
            myBlue.setSource(myConfigurator.get("SenderMailNameProgram"));
            myBlue.setContent(Message);
            myBlue.sendMessage();
        }
    }

    public void log(String param) {
        if (Diagnostic) {
          log.info(param);
        }
    }

    /**
     * @return the Diagnostic
     */
    public boolean isDiagnostic() {
        return Diagnostic;
    }

    /**
     * @param Diagnostic the Diagnostic to set
     */
    public void setDiagnostic(boolean Diagnostic) {
        this.Diagnostic = Diagnostic;
    }

    /**
     * @return the SHOW_RECORD
     */
    public boolean isSHOW_RECORD() {
        return SHOW_RECORD;
    }

    /**
     * @param SHOW_RECORD the SHOW_RECORD to set
     */
    public void setSHOW_RECORD(boolean SHOW_RECORD) {
        this.SHOW_RECORD = SHOW_RECORD;
    }

    /**
     * @return the performTruncateTable
     */
    public boolean isPerformTruncateTable() {
        return performTruncateTable;
    }

    /**
     * @return the performUpdateRecord
     */
    public boolean isPerformUpdateTable() {
        return performUpdateRecord;
    }

    /**
     * @return the performInsertRecord
     */
    public boolean isPerformInsertTable() {
        return performInsertRecord;
    }

    private void performTransaction(PreparedAtomContainer SourceTable, PreparedAtomContainer DestinationTable) throws SQLException, Exception {
        try {

            setRecordsInserted(0);
            setRecordsUpdated(0);
            setRecordsRead(0);

            DestinationTable.prepareStatements(DestinationTable.getConnection());
            if (isPerformTruncateTable()) {
                log("Truncating table " + DestinationTable.getTableName());
                DestinationTable.truncateTable(DestinationTable.getConnection());
                log("[OK]");

            }
            ResultSet myResult = null;
            myResult = SourceTable.getList(SourceTable.getConnection(),this.getSourceFilter(), null, this.getSourceParams());
            myResult.setFetchSize(1024);
            int LineDotCounter = 0;
            int notify = 0;
            while (myResult.next()) {
                try {
                    DestinationTable.readRecordFields(myResult);
                    setRecordsRead(getRecordsRead() + 1);
                    if (isDiagnostic()) {
                        if (isSHOW_RECORD()) {
                            System.out.println("Copy " + DestinationTable.toString());
                        } else {
                            if (++LineDotCounter > 150) {
                                LineDotCounter = 0;
                                System.out.println(".");
                            } else {
                                System.out.print(".");
                            }
                        }
                    }
                    if (isPerformTruncateTable()) {
                        DestinationTable.create();
                        setRecordsInserted(getRecordsInserted() + 1);

                    } else {

                        if (DestinationTable.checkExists()) {
                            if (isPerformUpdateTable()) {
                                DestinationTable.update();
                                setRecordsUpdated(getRecordsUpdated() + 1);
                            }
                        } else {
                            if (isPerformInsertTable()) {
                                DestinationTable.create();
                                setRecordsInserted(getRecordsInserted() + 1);
                            }
                        }

                    }

                } catch (java.sql.SQLException sqle) {
                    log.error("Transaction error", sqle);
                    if(this.isHaltOnError()) {
                       throw sqle;
                    }
                    if(notify < 10) {
                     notify++;
                     notifyMessage(sqle);
                    }

                }
            }
            log("");
            myResult.close();
            log("Records read: " + getRecordsRead() + ", Records inserted: " + getRecordsInserted() + ", Records Updated: " + getRecordsUpdated());
            this.resetActions();

        } catch (java.sql.SQLException sqle) {
           if(!isHaltOnError()) {
              notifyMessage(sqle);
           }
            throw sqle;
        } catch (Exception e) {
           if(!isHaltOnError()) {
             notifyMessage(e);
           }
            throw e;
        }
    }

  /**
   * @return the sourceFilter
   */
  public String getSourceFilter() {
    return sourceFilter;
  }

  /**
   * @param sourceFilter the sourceFilter to set
   */
  public void setSourceFilter(String sourceFilter) {
    this.sourceFilter = sourceFilter;
  }

  /**
   * @return the sourceParams
   */
  public Object[] getSourceParams() {
    return sourceParams;
  }

  /**
   * @param sourceParams the sourceParams to set
   */
  public void setSourceParams(Object[] sourceParams) {
    this.sourceParams = sourceParams;
  }

   /**
    * @return the haltOnError
    */
   public boolean isHaltOnError() {
      return haltOnError;
   }

   /**
    * @param haltOnError the haltOnError to set
    */
   public void setHaltOnError(boolean haltOnError) {
      this.haltOnError = haltOnError;
   }

   /**
    * @return the RecordsInserted
    */
   public int getRecordsInserted() {
      return RecordsInserted;
   }

   /**
    * @param RecordsInserted the RecordsInserted to set
    */
   public void setRecordsInserted(int RecordsInserted) {
      this.RecordsInserted = RecordsInserted;
   }

   /**
    * @return the RecordsUpdated
    */
   public int getRecordsUpdated() {
      return RecordsUpdated;
   }

   /**
    * @param RecordsUpdated the RecordsUpdated to set
    */
   public void setRecordsUpdated(int RecordsUpdated) {
      this.RecordsUpdated = RecordsUpdated;
   }

   /**
    * @return the RecordsRead
    */
   public int getRecordsRead() {
      return RecordsRead;
   }

   /**
    * @param RecordsRead the RecordsRead to set
    */
   public void setRecordsRead(int RecordsRead) {
      this.RecordsRead = RecordsRead;
   }
}
