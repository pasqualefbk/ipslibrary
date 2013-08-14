package com.ips.data;

import java.sql.DatabaseMetaData;
import java.sql.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package com.ips.data:
//            AtomContainer, AtomProperty
public class PreparedAtomContainer extends AtomContainer {
    private static Log log = LogFactory.getLog(PreparedAtomContainer.class);
    private final int version = 22;
    private String IsRecordedQuery = null;
    private String InsertCommand = null;
    private String DeleteQuery = null;
    private String UpdateCommand = null;

    public PreparedAtomContainer() {
        Diagnostic = false;
    }

    public boolean read()
            throws SQLException, Exception {
        boolean Found = false;
        checkCatalog();
        setEmpty(true);
        try {
            ResultSet myResult = read(getConnection());
            if (myResult.next()) {
                setEmpty(false);
                readRecordFields(myResult);
                Found = true;
            }
            myResult.close();
            return Found;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public void checkCatalog()
            throws SQLException {
        try {
            if (getCatalog() != null && getCatalog().trim().length() > 0) {
                getConnection().setCatalog(getCatalog());
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    public ResultSet read(Connection conn)
            throws SQLException, Exception {
        try {
            checkCatalog();
            if (ReadStatement == null) {
                prepareStatements(conn);
            }
            int index = 1;
            fillWhereCondition(ReadStatement, index);
            ResultSet myResultSet = ReadStatement.executeQuery();
            return myResultSet;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkExists()
            throws SQLException, Exception {
        boolean Exists = false;
        try {
            if (Connection == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            } else {
                Exists = this.checkExists(Connection);
            }
            return Exists;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkExists(Connection conn)
            throws SQLException, Exception {
        boolean Exists = false;
        try {
            checkCatalog();
            if (CheckExists == null) {
                prepareStatements(conn);
            }
            int index = 1;
            fillWhereCondition(CheckExists, index);
            ResultSet myResultSet = CheckExists.executeQuery();
            if (myResultSet.next()) {
                Exists = true;
            }
            myResultSet.close();
            return Exists;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet getList(Connection conn, String OrderByParam)
            throws SQLException {
        try {
            checkCatalog();
            IsRecordedQuery = null;
            if (OrderByParam == null || OrderByParam.trim().length() == 0) {
                IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).toString();
            } else {
                IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).append(" order by ").append(OrderByParam).toString();
            }
            Statement myStmt = conn.createStatement();
            ResultSet myResultSet = myStmt.executeQuery(IsRecordedQuery);
            return myResultSet;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        }
    }

    public ResultSet getList(String OrderByParam)
            throws SQLException, Exception {
        try {
            if (Connection == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            } else {
                ResultSet myResultSet = getList(Connection, OrderByParam);
                return myResultSet;
            }
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet getFilteredList(Connection conn, String FilterParam)
            throws SQLException, Exception {
        try {
            if (conn == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            } else {
                checkCatalog();
                IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).append(" where ").append(FilterParam).toString();
                Statement myStmt = getConnection().createStatement();
                log(IsRecordedQuery);
                ResultSet myResultSet = myStmt.executeQuery(IsRecordedQuery);
                return myResultSet;
            }
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet getList(Connection conn, String WhereParam, String OrderByParam)
            throws SQLException {
        try {
            checkCatalog();
            IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).toString();
            if (WhereParam != null) {
                IsRecordedQuery = (new StringBuilder()).append(IsRecordedQuery).append(" where ").append(WhereParam).toString();
            }
            if (OrderByParam != null) {
                IsRecordedQuery = (new StringBuilder()).append(IsRecordedQuery).append(" order by ").append(OrderByParam).toString();
            }
            log(IsRecordedQuery);
            PreparedStatement myStmt = conn.prepareStatement(IsRecordedQuery);
            this.setStatementTimeout(myStmt);
            ResultSet myResultSet = myStmt.executeQuery();
            return myResultSet;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        }
    }
    
    public ResultSet getList(Connection conn, String WhereParam, String OrderByParam, Object ... whereValues)
            throws SQLException {
        try {
            checkCatalog();
            IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).toString();
            if (WhereParam != null) {
                IsRecordedQuery = (new StringBuilder()).append(IsRecordedQuery).append(" where ").append(WhereParam).toString();
            }
            if (OrderByParam != null) {
                IsRecordedQuery = (new StringBuilder()).append(IsRecordedQuery).append(" order by ").append(OrderByParam).toString();
            }
            log(IsRecordedQuery);
            PreparedStatement myStmt = conn.prepareStatement(IsRecordedQuery);
            this.setStatementTimeout(myStmt);
            if(whereValues != null) {
              int i = 1;
              for (Object object : whereValues) {
                myStmt.setObject(i, object);
                i++;
              }
            }

            ResultSet myResultSet = myStmt.executeQuery();
            return myResultSet;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        }
    }

    public ResultSet getList(Connection conn)
            throws SQLException {
        try {
            checkCatalog();
            IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(getTableName()).append(" ").toString();
            Statement myStmt = conn.createStatement();
            this.setStatementTimeout(myStmt);
            ResultSet myResultSet = myStmt.executeQuery(IsRecordedQuery);
            log(IsRecordedQuery);
            return myResultSet;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        }
    }

    public int getTableRows(Connection conn)
            throws SQLException, Exception {
        int TotRows = 0;
        try {
            if (TableRowsStatement == null) {
                prepareStatements(conn);
            }
            checkCatalog();
            ResultSet myResult = TableRowsStatement.executeQuery();
            if (myResult.next()) {
                TotRows = myResult.getInt(1);
            }
            myResult.close();
            return TotRows;
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete()
            throws SQLException, Exception {
        if (Connection == null) {
            throw new Exception("Connection is null, please provide a valid connection");
        }
        try {
            checkCatalog();
            delete(Connection);
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(Connection conn)
            throws SQLException, Exception {
        try {

            log(DeleteQuery);
            checkCatalog();
            if (DeleteStatement == null) {
                prepareStatements(conn);
            }
            int index = 1;
            fillWhereCondition(DeleteStatement, index);
            DeleteStatement.execute();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void insert(Connection conn)
            throws SQLException, Exception {
        try {

            log((new StringBuilder()).append("Executing insert on tablename ").append(getTableName()).toString());

            if (conn == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            if (InsertStatement == null) {
                prepareStatements(conn);
            }
            int index = 1;
            Collection c = Container.values();
            boolean first = true;
            Iterator e = c.iterator();
            do {
                if (!e.hasNext()) {
                    break;
                }
                AtomProperty myAtomProperty = (AtomProperty) e.next();
                if (myAtomProperty.takeInsert()) {
                    myAtomProperty.fillCallableStatement(InsertStatement, index++);
                }
            } while (true);
            log(InsertCommand + this.toString());
            InsertStatement.executeUpdate();
            if (!AutoGeneratedKeyContainer.isEmpty()) {
                Collection AutoGeneratedKeyCollection = AutoGeneratedKeyContainer.values();
                Iterator i = AutoGeneratedKeyCollection.iterator();
                ResultSet AutoGeneratedKeys;
                String myAutoGeneratedKey;
                AtomProperty myAtom;
                for (AutoGeneratedKeys = InsertStatement.getGeneratedKeys(); AutoGeneratedKeys.next(); setAtomObject(myAutoGeneratedKey, myAtom)) {
                    log((new StringBuilder()).append("New autogenerated key value ").append(AutoGeneratedKeys.getInt(1)).toString());
                    myAutoGeneratedKey = (String) i.next();
                    if (myAutoGeneratedKey == null) {
                        throw new Exception("AutoGeneratedKey is null");
                    }
                    myAtom = getAtomObject(myAutoGeneratedKey);
                    myAtom.readRecord(AutoGeneratedKeys, 1);
                }

                AutoGeneratedKeys.close();
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

   public void log(String param)
    {
        if(Diagnostic) {
            log.info(param);
        }
    }

    public void create(Connection conn)
            throws SQLException, Exception {
        try {
            insert(conn);
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void insert()
            throws SQLException, Exception {
        try {
            if (getConnection() == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            insert(getConnection());
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void prepareStatements(Connection conn)
            throws SQLException, Exception {
        try {
            if (conn == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            checkCatalog();
            log((new StringBuilder()).append("Executing prepareStatament on table ").append(getTableName()).toString());
            boolean first = true;
            InsertCommand = (new StringBuilder()).append("insert into ").append(getTableName()).append(" (").toString();
            Collection c = Container.values();
            Iterator i = c.iterator();
            do {
                if (!i.hasNext()) {
                    break;
                }
                AtomProperty myAtomProperty = (AtomProperty) i.next();
                if (myAtomProperty.takeInsert()) {
                    if (first) {
                        InsertCommand = (new StringBuilder()).append(InsertCommand).append(escape(myAtomProperty.getFieldName())).toString();
                        first = false;
                    } else {
                        InsertCommand = (new StringBuilder()).append(InsertCommand).append(",").append(escape(myAtomProperty.getFieldName())).toString();
                    }
                }
            } while (true);
            InsertCommand = (new StringBuilder()).append(InsertCommand).append(") values (").toString();
            c = Container.values();
            first = true;
            i = c.iterator();
            do {
                if (!i.hasNext()) {
                    break;
                }
                AtomProperty myAtomProperty = (AtomProperty) i.next();
                if (myAtomProperty.takeInsert()) {
                    if (first) {
                        InsertCommand = (new StringBuilder()).append(InsertCommand).append("?").toString();
                        first = false;
                    } else {
                        InsertCommand = (new StringBuilder()).append(InsertCommand).append(",?").toString();
                    }
                }
            } while (true);
            InsertCommand = (new StringBuilder()).append(InsertCommand).append(")").toString();
            InsertStatement = conn.prepareStatement(InsertCommand, 1);
            this.setStatementTimeout(InsertStatement);
            String WhereCondition = "";
            Collection pk = PrimaryKeysContainer.values();
            first = true;
            for (Iterator j = pk.iterator(); j.hasNext();) {
                AtomProperty myAtomProperty = (AtomProperty) Container.get((String) j.next());
                if (first) {
                    first = false;
                    WhereCondition = (new StringBuilder()).append(WhereCondition).append(" where ").append(escape(myAtomProperty.getFieldName())).append(" = ?").toString();
                } else {
                    WhereCondition = (new StringBuilder()).append(WhereCondition).append(" and ").append(escape(myAtomProperty.getFieldName())).append(" = ?").toString();
                }
            }

            UpdateCommand = (new StringBuilder()).append("update ").append(getTableName()).append(" set ").toString();
            c = Container.values();
            first = true;
            Iterator k = c.iterator();
            do {
                if (!k.hasNext()) {
                    break;
                }
                AtomProperty myAtomProperty = (AtomProperty) k.next();
                if (myAtomProperty.takeUpdate()) {
                    if (first) {
                        UpdateCommand = (new StringBuilder()).append(UpdateCommand).append(escape(myAtomProperty.getFieldName())).append("=?").toString();
                        first = false;
                    } else {
                        UpdateCommand = (new StringBuilder()).append(UpdateCommand).append(",").append(escape(myAtomProperty.getFieldName())).append("=?").toString();
                    }
                }
            } while (true);
            UpdateCommand = (new StringBuilder()).append(UpdateCommand).append(WhereCondition).toString();
            UpdateStatement = conn.prepareStatement(UpdateCommand);
            this.setStatementTimeout(UpdateStatement);
            String IsRecordedQuery = (new StringBuilder()).append("Select * from ").append(super.getTableName()).toString();
            IsRecordedQuery = (new StringBuilder()).append(IsRecordedQuery).append(WhereCondition).toString();
            ReadStatement = conn.prepareStatement(IsRecordedQuery);
            this.setStatementTimeout(ReadStatement);
            DeleteQuery = (new StringBuilder()).append("delete from ").append(getTableName()).toString();
            DeleteQuery = (new StringBuilder()).append(DeleteQuery).append(WhereCondition).toString();
            DeleteStatement = conn.prepareStatement(DeleteQuery);
            this.setStatementTimeout(DeleteStatement);
            String CheckExistsQuery = "Select ";
            first = true;
            pk = PrimaryKeysContainer.values();
            for (Iterator it = pk.iterator(); it.hasNext();) {
                String PrimaryKey = (String) it.next();
                if (first) {
                    first = false;
                    CheckExistsQuery = (new StringBuilder()).append(CheckExistsQuery).append(PrimaryKey).toString();
                } else {
                    CheckExistsQuery = (new StringBuilder()).append(CheckExistsQuery).append(", ").append(PrimaryKey).toString();
                }
            }

            CheckExistsQuery = (new StringBuilder()).append(CheckExistsQuery).append(" from ").append(getTableName()).toString();
            CheckExistsQuery = (new StringBuilder()).append(CheckExistsQuery).append(WhereCondition).toString();
            CheckExists = conn.prepareStatement(CheckExistsQuery);
            String myQuery = (new StringBuilder()).append("delete from ").append(getTableName()).toString();
            TruncateStatement = conn.prepareStatement(myQuery);
            this.setStatementTimeout(TruncateStatement);
            String CountQuery = (new StringBuilder()).append("select count(*) from ").append(getTableName()).toString();
            TableRowsStatement = conn.prepareStatement(CountQuery);
            this.setStatementTimeout(TableRowsStatement);

            log((new StringBuilder()).append("CHECK EXISTS STATEMENT: ").append(CheckExistsQuery).toString());
            log((new StringBuilder()).append("TRUNCATE STATEMENT: ").append(myQuery).toString());
            log((new StringBuilder()).append("COUNT  STATEMENT: ").append(CountQuery).toString());
            log((new StringBuilder()).append("SELECT STATEMENT: ").append(IsRecordedQuery).toString());
            log((new StringBuilder()).append("INSERT STATEMENT: ").append(InsertCommand).toString());
            log((new StringBuilder()).append("UPDATE STATEMENT: ").append(UpdateCommand).toString());
            log((new StringBuilder()).append("DELETE STATEMENT: ").append(DeleteQuery).toString());

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void truncateTable(Connection conn)
            throws SQLException, Exception {
        try {
            if (TruncateStatement == null) {
                checkCatalog();
                prepareStatements(conn);
            }
            TruncateStatement.execute();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(Connection conn)
            throws SQLException, Exception {
        try {
            if (UpdateStatement == null) {
                checkCatalog();
                prepareStatements(conn);
            }
            log((new StringBuilder()).append("Executing UPDATE on tablename ").append(getTableName()).toString());
            int index = 1;
            Collection c = Container.values();
            Iterator e = c.iterator();
            do {
                if (!e.hasNext()) {
                    break;
                }
                AtomProperty myAtomProperty = (AtomProperty) e.next();
                if (myAtomProperty.takeUpdate()) {
                    myAtomProperty.fillCallableStatement(UpdateStatement, index++);
                }
            } while (true);
            fillWhereCondition(UpdateStatement, index);
            log(UpdateCommand + this.toString());
            UpdateStatement.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    private void fillWhereCondition(PreparedStatement PreparedStatementParam, int indexParam)
            throws SQLException, Exception {
        try {
            checkCatalog();
            Collection pk = PrimaryKeysContainer.values();
            AtomProperty myAtomProperty;
            for (Iterator i = pk.iterator(); i.hasNext(); myAtomProperty.fillCallableStatement(PreparedStatementParam, indexParam++)) {
                myAtomProperty = (AtomProperty) Container.get((String) i.next());
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void create()
            throws SQLException, Exception {
        try {
            insert();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void update()
            throws SQLException, Exception {
        try {
            update(getConnection());
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void autodiscoverFields()
            throws SQLException, Exception {
        try {
            if (getConnection() == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            checkCatalog();
            autodiscoverFields(getConnection());
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void autodiscoverFields(Connection conn)
            throws SQLException, Exception {
        try {
            Container.clear();
            AutoGeneratedKeyContainer.clear();
            DatabaseMetaData dmd = conn.getMetaData();

            if (conn == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            checkCatalog();
            String myQuery = (new StringBuilder()).append("select * from ").append(getTableName()).append(" where 1 = 0").toString();
            log(myQuery);            
            AutoDiscoverStatement = conn.prepareStatement(myQuery);
            ResultSet myResult = AutoDiscoverStatement.executeQuery();
            ResultSetMetaData rsmd = myResult.getMetaData();
            DatabaseMetaData meta = conn.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            if (dmd.getIdentifierQuoteString().equals(" ") == false) {
                this.setIdentifierQuoteString(dmd.getIdentifierQuoteString());
            }
            log("Product Name: " + dmd.getDatabaseProductName() + ", Version: " + dmd.getDatabaseProductVersion());
            log("Character to quote string: " + dmd.getIdentifierQuoteString());
            
            boolean first = true;
            for (int i = 1; i <= numberOfColumns; i++) {
                boolean precision = false;
                boolean scale = false;
                int SqlType = rsmd.getColumnType(i);
                boolean found = true;
                String TypeMapped = (new StringBuilder()).append(rsmd.getColumnName(i)).append(" - ").toString();
                switch (SqlType) {
                    case -5:
                        SqlType = 8;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.BIGINT mapped to SQL_TYPE.LONG_TYPE").toString();
                        break;

                    case -7:
                        SqlType = 1;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.BIT mapped to SQL_TYPE.INT_TYPE").toString();
                        break;

                    case 16: // '\020'
                        SqlType = 6;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.BOOLEAN mapped to SQL_TYPE.BOOLEAN_TYPE").toString();
                        break;

                    case 1: // '\001'
                        SqlType = 2;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.CHAR mapped to SQL_TYPE.STRING_TYPE;").toString();
                        precision = true;
                        break;

                    case 91: // '['
                        SqlType = 3;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.DATE mapped to SQL_TYPE.CALENDAR_TYPE;").toString();
                        break;

                    case 3: // '\003'
                        SqlType = 5;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.DECIMAL mapped to SQL_TYPE.DOUBLE_TYPE;").toString();
                        precision = true;
                        scale = true;
                        break;

                    case 8: // '\b'
                        SqlType = 5;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.DOUBLE mapped to SQL_TYPE.DOUBLE_TYPE").toString();
                        break;

                    case 6: // '\006'
                        SqlType = 4;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.FLOAT mapped to SQL_TYPE.FLOAT_TYPE").toString();
                        break;

                    case 4: // '\004'
                        SqlType = 1;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.INTEGER mapped to SQL_TYPE.INT_TYPE").toString();
                        break;

                    case -16:
                        SqlType = 2;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.LONGNVARCHAR mapped to SQL_TYPE.STRING_TYPE").toString();
                        precision = true;
                        break;

                    case 2004:
                        SqlType = 7;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.BLOB mapped to SQL_TYPE.BINARY_TYPE").toString();
                        break;

                    case -4:
                        SqlType = 7;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.LONGVARBINARY mapped to SQL_TYPE.BINARY_TYPE").toString();
                        break;

                    case -1:
                        SqlType = 2;
                        precision = true;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.LONGVARCHAR mapped to SQL_TYPE.STRING_TYPE").toString();
                        break;

                    case -15:
                        SqlType = 2;
                        precision = true;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.NCHAR mapped to SQL_TYPE.STRING_TYPE").toString();
                        break;

                    case 2: // '\002'
                        SqlType = 5;
                        precision = true;
                        scale = true;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.NUMERIC mapped to SQL_TYPE.DOUBLE_TYPE").toString();
                        break;

                    case -9:
                        SqlType = 2;
                        precision = true;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.NVARCHAR mapped to SQL_TYPE.STRING_TYPE").toString();
                        break;

                    case 7: // '\007'
                        SqlType = 5;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.REAL mapped to SQL_TYPE.DOUBLE_TYPE").toString();
                        break;

                    case 5: // '\005'
                        SqlType = 1;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.SMALLINT mapped to SQL_TYPE.INT_TYPE").toString();
                        break;

                    case 92: // '\\'
                        SqlType = 3;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.TIME mapped to SQL_TYPE.TIMESTAMP_TYPE").toString();
                        break;

                    case 93: // ']'
                        SqlType = 9;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.TIMESTAMP mapped to SQL_TYPE.TIMESTAMP_TYPE").toString();
                        break;

                    case -6:
                        SqlType = 1;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.TINYINT mapped to SQL_TYPE.INT_TYPE").toString();
                        break;

                    case -3:
                        SqlType = 7;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.VARBINARY mapped to SQL_TYPE.BINARY_TYPE").toString();
                        break;

                    case 12: // '\f'
                        SqlType = 2;
                        precision = true;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.VARCHAR mapped to SQL_TYPE.STRING_TYPE").toString();
                        break;

                    case 2005:
                        SqlType = 2;
                        TypeMapped = (new StringBuilder()).append(TypeMapped).append("Java Types.CLOB mapped to SQL_TYPE.STRING_TYPE").toString();
                        break;

                    default:
                        found = false;
                        break;
                }
                if (found) {
                    if (isDiagnostic()) {
                        System.out.println(TypeMapped);
                    }

                    if (rsmd.isAutoIncrement(i)) {                        
                        log((new StringBuilder()).append(rsmd.getColumnName(i)).append(" is AUTOINCREMENT").toString());                        
                        add(rsmd.getColumnName(i), SqlType, false, false);
                        addAutoGeneratedKey(rsmd.getColumnName(i));
                    } else {
                        add(rsmd.getColumnName(i), SqlType, true, true);
                    }
                } else {
                    throw new Exception((new StringBuilder()).append("Data Type number ").append(SqlType).append(" non yet supported.").toString());
                }
            }

            myResult.close();
            autodiscoverPrimaryKeys(conn);
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void autodiscoverPrimaryKeys(Connection conn)
            throws SQLException, Exception {
        try {
            PrimaryKeysContainer.clear();
            checkCatalog();

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = null;
            String CatalogSeparator = this.getCatalogSeparator(meta);
            rs = meta.getPrimaryKeys(this.getCatalog(), this.getSchema(), this.getTableName());
            do {
                if (!rs.next()) {
                    break;
                }
                String columnName = rs.getString("COLUMN_NAME");
                addPrimaryKey(columnName);                
                log((new StringBuilder()).append("getPrimaryKeys(): columnName=").append(columnName).toString());                
            } while (true);
            rs.close();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public void autodiscoverPrimaryKeys()
            throws SQLException, Exception {
        try {
            if (getConnection() == null) {
                throw new Exception("Connection is null, please provide a valid connection");
            }
            checkCatalog();
            autodiscoverPrimaryKeys(getConnection());
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }

    public int getVersion() {
        return this.version;
    }

    public Connection getConnection() {
        return Connection;
    }

    public void setConnection(Connection Connection) {
        this.Connection = Connection;
    }

    public boolean isDiagnostic() {
        return Diagnostic;
    }

    public void setDiagnostic(boolean Diagnostic) {
        this.Diagnostic = Diagnostic;
    }
    
    private void setStatementTimeout(Statement s) throws SQLException {
      if(this.getQueryTimeout() > 0) {
          s.setQueryTimeout(this.getQueryTimeout());
      } 
    }
    
    protected PreparedStatement ReadStatement;
    protected PreparedStatement DeleteStatement;
    protected PreparedStatement InsertStatement;
    protected PreparedStatement UpdateStatement;    
    protected PreparedStatement CheckExists;
    private Connection Connection;
    protected PreparedStatement TruncateStatement;
    protected PreparedStatement AutoDiscoverStatement;
    protected PreparedStatement TableRowsStatement;
    private boolean Diagnostic;
}
