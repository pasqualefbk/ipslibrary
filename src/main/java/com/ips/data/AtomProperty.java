/*
 * Created on May 17, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ips.data;

import com.ips.data.type.SQL_TYPE;

import java.util.Calendar;
import java.sql.*;
import java.io.ByteArrayInputStream;

/**
 * @author pasquale
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AtomProperty {

    private final int version = 21;
    private int OBJECT_TYPE;
    private String FieldName;
    private long Size;
    private Integer iFieldValue;
    private String sFieldValue;
    private ByteArrayInputStream binFieldValue;
    private Calendar cFieldValue;    
    private Float fFieldValue;
    private Boolean bFieldValue;
    private Long longFieldValue;
    private java.sql.Timestamp timestampFieldValue;
    private Double dFieldValue;    
    private Blob BlobValue;
    private boolean INSERT = false;
    private boolean UPDATE = false;
    private boolean isCustom = false;
    private boolean readOnly = false;
    private String HumanFieldName = null;

    /**
     *
     * @param FieldNameParam name of the field
     * @param objectTypeParam sqltype mapped in SQL_TYPE class
     * @param InsertParam allow field insert
     * @param UpdateParam allow field update
     */
    public AtomProperty(String FieldNameParam, int objectTypeParam, boolean InsertParam, boolean UpdateParam) {
        FieldName = FieldNameParam;
        OBJECT_TYPE = objectTypeParam;
        INSERT = InsertParam;
        UPDATE = UpdateParam;
        switch (OBJECT_TYPE) {
            case SQL_TYPE.INT_TYPE:
                iFieldValue = null;
                break;
            case SQL_TYPE.STRING_TYPE:
                sFieldValue = null;
                Size = 0;
                break;
            case SQL_TYPE.FLOAT_TYPE:
                fFieldValue = null;
                break;
            case SQL_TYPE.DOUBLE_TYPE:
                dFieldValue = null;
                break;
            case SQL_TYPE.CALENDAR_TYPE:
                cFieldValue = null;
                break;
            case SQL_TYPE.BOOLEAN_TYPE:
                bFieldValue = null;
                break;
            case SQL_TYPE.BINARY_TYPE:
                binFieldValue = null;
                break;
            case SQL_TYPE.LONG_TYPE:
                longFieldValue = null;
                break;
            case SQL_TYPE.TIMESTAMP_TYPE:
                timestampFieldValue = null;
                break;
            case SQL_TYPE.BLOB_TYPE:
                BlobValue = null;
                break;

        }
    }

    public AtomProperty(String FieldNameParam, int objectTypeParam, boolean InsertParam, boolean UpdateParam, int SizeParam) {
        FieldName = FieldNameParam;
        OBJECT_TYPE = objectTypeParam;
        INSERT = InsertParam;
        UPDATE = UpdateParam;
        switch (OBJECT_TYPE) {
            case SQL_TYPE.INT_TYPE:
                iFieldValue = null;
                break;
            case SQL_TYPE.STRING_TYPE:
                sFieldValue = null;
                Size = SizeParam;
                break;
            case SQL_TYPE.FLOAT_TYPE:
                fFieldValue = null;
                break;
            case SQL_TYPE.DOUBLE_TYPE:
                dFieldValue = null;
                break;
            case SQL_TYPE.CALENDAR_TYPE:
                cFieldValue = null;
                break;
            case SQL_TYPE.BOOLEAN_TYPE:
                bFieldValue = null;
                break;
            case SQL_TYPE.BINARY_TYPE:
                binFieldValue = null;
                break;
            case SQL_TYPE.LONG_TYPE:
                longFieldValue = null;
                break;
            case SQL_TYPE.TIMESTAMP_TYPE:
                timestampFieldValue = null;
                break;
            case SQL_TYPE.BLOB_TYPE:
                BlobValue = null;
                break;


        }
    }

    public void emptyData() {
        switch (getOBJECT_TYPE()) {
            case SQL_TYPE.INT_TYPE:
                iFieldValue = null;
                break;
            case SQL_TYPE.STRING_TYPE:
                sFieldValue = null;
                break;
            case SQL_TYPE.FLOAT_TYPE:
                fFieldValue = null;
                break;
            case SQL_TYPE.DOUBLE_TYPE:
                dFieldValue = null;
                break;
            case SQL_TYPE.CALENDAR_TYPE:
                cFieldValue = null;
                break;
            case SQL_TYPE.BOOLEAN_TYPE:
                bFieldValue = null;
                break;
            case SQL_TYPE.BINARY_TYPE:
                binFieldValue = null;
                break;
            case SQL_TYPE.LONG_TYPE:
                longFieldValue = null;
                break;
            case SQL_TYPE.TIMESTAMP_TYPE:
                timestampFieldValue = null;
                break;
            case SQL_TYPE.BLOB_TYPE:
                BlobValue = null;
                break;
        }
    }

    public String getFieldName() {
        return FieldName;
    }

    public String getString() {
        return sFieldValue;
    }

    public Float getFloat() {
        return fFieldValue;
    }

    public Double getDouble() {
        return dFieldValue;
    }

    public Calendar getCalendar() {
        return cFieldValue;
    }

    public Timestamp getTimestamp() {

        return timestampFieldValue;

    }

    public Blob getBlob() {
        return BlobValue;
    }

    public Integer getInteger() {
        return iFieldValue;
    }

    public Boolean getBoolean() {
        return bFieldValue;
    }

    public Long getLong() {
        return longFieldValue;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        return binFieldValue;
    }

    public void readRecord(ResultSet myResult) throws SQLException {
        try {
            
            switch (getOBJECT_TYPE()) {
                case SQL_TYPE.INT_TYPE:
                    int IntValue = myResult.getInt(FieldName);
                    if (myResult.wasNull()) {
                        iFieldValue = null;
                    } else {
                        iFieldValue = new Integer(IntValue);
                    }
                    break;
                case SQL_TYPE.STRING_TYPE:
                    sFieldValue = myResult.getString(FieldName);
                    if (myResult.wasNull()) 
                        sFieldValue = null;                    
                    break;
                case SQL_TYPE.FLOAT_TYPE:
                    float FloatValue = myResult.getFloat(FieldName);
                    if (myResult.wasNull()) {
                        fFieldValue = null;
                    } else {
                        fFieldValue = new Float(FloatValue);
                    }
                    break;
                case SQL_TYPE.DOUBLE_TYPE:
                    double DoubleValue = myResult.getDouble(FieldName);
                    if (myResult.wasNull()) {
                        dFieldValue = null;
                    } else {
                        dFieldValue = new Double(DoubleValue);
                    }
                    break;
                case SQL_TYPE.CALENDAR_TYPE:

                    Timestamp myTimestamp = myResult.getTimestamp(FieldName);
                    if (myResult.wasNull()) {
                        cFieldValue = null;
                    } else {
                        cFieldValue = null;                        
                        if (myTimestamp != null) {
                            cFieldValue = java.util.Calendar.getInstance();
                            cFieldValue.setTimeInMillis(myTimestamp.getTime());
                        }
                    }
                    break;
                case SQL_TYPE.BOOLEAN_TYPE:
                    boolean BooleanValue = myResult.getBoolean(FieldName);
                    if (myResult.wasNull()) {
                        bFieldValue = null;
                    } else {
                        bFieldValue = new Boolean(BooleanValue);
                    }
                    break;
                case SQL_TYPE.BINARY_TYPE:
                    byte []buffer = myResult.getBytes(FieldName);
                    if (myResult.wasNull()) {
                        binFieldValue = null;
                    } else {
                        if(myResult.getBytes(FieldName) == null)
                            binFieldValue = null;
                        else
                        {
                            this.setSize(myResult.getBytes(FieldName).length);
                            binFieldValue = new ByteArrayInputStream(myResult.getBytes(FieldName));
                        }
                    }
                    break;
                case SQL_TYPE.LONG_TYPE:
                    long LongValue = myResult.getLong(FieldName);
                    if (myResult.wasNull()) {
                        longFieldValue = null;
                    } else {
                        longFieldValue = new Long(LongValue);
                    }
                    break;
                case SQL_TYPE.TIMESTAMP_TYPE:
                    timestampFieldValue = myResult.getTimestamp(FieldName);
                    break;

                case SQL_TYPE.BLOB_TYPE:
                    BlobValue = myResult.getBlob(FieldName);
                    break;


            }

        } catch (SQLException sqle) {
            throw sqle;
        }
    }
     public void readRecord(ResultSet myResult, int IndexParam) throws SQLException {
        try {

            switch (getOBJECT_TYPE()) {
                case SQL_TYPE.INT_TYPE:
                    if (myResult.wasNull()) {
                        iFieldValue = null;
                    } else {
                        iFieldValue = new Integer(myResult.getInt(IndexParam));
                    }
                    break;
                case SQL_TYPE.STRING_TYPE:
                    if (myResult.wasNull()) {
                        sFieldValue = null;
                    } else {
                        sFieldValue = myResult.getString(IndexParam);
                    }
                    break;
                case SQL_TYPE.FLOAT_TYPE:
                    if (myResult.wasNull()) {
                        fFieldValue = null;
                    } else {
                        fFieldValue = new Float(myResult.getFloat(IndexParam));
                    }
                    break;
                case SQL_TYPE.DOUBLE_TYPE:
                    if (myResult.wasNull()) {
                        dFieldValue = null;
                    } else {
                        dFieldValue = new Double(myResult.getDouble(IndexParam));
                    }
                    break;
                case SQL_TYPE.CALENDAR_TYPE:
                    if (myResult.wasNull()) {
                        cFieldValue = null;
                    } else {
                        cFieldValue = null;
                        Timestamp myTimestamp = myResult.getTimestamp(IndexParam);
                        if (myTimestamp != null) {
                            cFieldValue = java.util.Calendar.getInstance();
                            cFieldValue.setTimeInMillis(myTimestamp.getTime());
                        }
                    }
                    break;
                case SQL_TYPE.BOOLEAN_TYPE:
                    if (myResult.wasNull()) {
                        bFieldValue = null;
                    } else {
                        bFieldValue = new Boolean(myResult.getBoolean(IndexParam));
                    }
                    break;
                case SQL_TYPE.BINARY_TYPE:
                    if (myResult.wasNull()) {
                        binFieldValue = null;
                    } else {
                        this.setSize(myResult.getBytes(IndexParam).length);
                        binFieldValue = new ByteArrayInputStream(myResult.getBytes(IndexParam));
                    }
                    break;
                case SQL_TYPE.LONG_TYPE:
                    if (myResult.wasNull()) {
                        longFieldValue = null;
                    } else {
                        longFieldValue = new Long(myResult.getLong(IndexParam));
                    }
                    break;
                case SQL_TYPE.TIMESTAMP_TYPE:
                    timestampFieldValue = myResult.getTimestamp(IndexParam);
                    break;

                case SQL_TYPE.BLOB_TYPE:
                    BlobValue = myResult.getBlob(IndexParam);
                    break;


            }

        } catch (SQLException sqle) {
            throw sqle;
        }
    }
    
    @Override
    public String toString() {
        String Description = "";
        switch (getOBJECT_TYPE()) {
            case SQL_TYPE.INT_TYPE:
                Description += FieldName + ": " + strCoalesce(iFieldValue);
                break;
            case SQL_TYPE.STRING_TYPE:
                Description += FieldName + ": " + strCoalesce(sFieldValue);
                break;
            case SQL_TYPE.FLOAT_TYPE:
                Description += FieldName + ": " + strCoalesce(fFieldValue);
                break;
            case SQL_TYPE.DOUBLE_TYPE:
                Description += FieldName + ": " + strCoalesce(dFieldValue);
                break;
            case SQL_TYPE.CALENDAR_TYPE:
                Description += FieldName + ": " + strCoalesce(cFieldValue);
                break;
            case SQL_TYPE.BOOLEAN_TYPE:
                Description += FieldName + ": " + strCoalesce(bFieldValue);
                break;
            case SQL_TYPE.BINARY_TYPE:
                Description += FieldName + ": " + strCoalesce(binFieldValue);
                break;
            case SQL_TYPE.LONG_TYPE:
                Description += FieldName + ": " + strCoalesce(longFieldValue);
                break;
            case SQL_TYPE.TIMESTAMP_TYPE:
                Description += FieldName + ": " + strCoalesce(timestampFieldValue);
                break;
            case SQL_TYPE.BLOB_TYPE:
                Description += FieldName + ": " + strCoalesce(BlobValue);
                break;
        }

        return Description;

    }

    private String strCoalesce(Object ObjectParam) {
        if (ObjectParam == null) {
            return "";
        }
        return ObjectParam.toString();
    }

    public void fillCallableStatement(PreparedStatement PreparedStatementParam, int i) throws SQLException, Exception {
        try {
            switch (getOBJECT_TYPE()) {
                case SQL_TYPE.INT_TYPE:
                    if(iFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.INTEGER);
                    else
                        PreparedStatementParam.setInt(i, iFieldValue.intValue());
                    break;
                case SQL_TYPE.STRING_TYPE:
                    if(sFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.LONGVARCHAR);
                    else
                        PreparedStatementParam.setString(i, sFieldValue);
                    break;
                case SQL_TYPE.FLOAT_TYPE:
                    if(fFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.FLOAT);
                    else
                        PreparedStatementParam.setFloat(i, fFieldValue.floatValue());
                    break;
                case SQL_TYPE.DOUBLE_TYPE:
                    if(dFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.DOUBLE);
                    else
                        PreparedStatementParam.setDouble(i, dFieldValue.doubleValue());
                    break;
                case SQL_TYPE.CALENDAR_TYPE:
                    if (cFieldValue != null) {
                        Timestamp myTimestamp = new Timestamp(cFieldValue.getTimeInMillis());
                        PreparedStatementParam.setTimestamp(i, myTimestamp);
                    } else {
                        PreparedStatementParam.setNull(i, java.sql.Types.TIMESTAMP);
                    }
                    break;
                case SQL_TYPE.BOOLEAN_TYPE:
                        if(bFieldValue == null)
                            PreparedStatementParam.setNull(i, java.sql.Types.BOOLEAN);
                        else
                            PreparedStatementParam.setBoolean(i, bFieldValue.booleanValue());
                    break;
                case SQL_TYPE.BINARY_TYPE:                    
                    if(binFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.LONGVARBINARY);
                    else
                    {       binFieldValue.mark(0);                                                                                           
                            int Size = binFieldValue.available();
                            this.setSize(Size);
                            PreparedStatementParam.setBinaryStream(i, binFieldValue, Size);                    

                    }
                    break;
                case SQL_TYPE.LONG_TYPE:
                    if(longFieldValue == null)
                        PreparedStatementParam.setNull(i,java.sql.Types.INTEGER);
                    else
                        PreparedStatementParam.setLong(i, longFieldValue);
                    break;
                case SQL_TYPE.TIMESTAMP_TYPE:
                    if(timestampFieldValue == null)
                        PreparedStatementParam.setNull(i, java.sql.Types.TIMESTAMP);
                    else
                        PreparedStatementParam.setTimestamp(i, timestampFieldValue);
                    break;
                case SQL_TYPE.BLOB_TYPE:
                    if(BlobValue == null)
                        PreparedStatementParam.setNull(i,java.sql.Types.BLOB);
                    else
                        PreparedStatementParam.setBlob(i, BlobValue);
                    break;
            }



        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }

    }

    public boolean takeInsert() {
        return INSERT;
    }

    public boolean takeUpdate() {
        return UPDATE;
    }

    public void set(Object ValueParam) {
        switch (getOBJECT_TYPE()) {
            case SQL_TYPE.INT_TYPE:
                if(ValueParam == null)
                    iFieldValue = null;
                else
                    iFieldValue = (Integer) ValueParam;
                break;
            case SQL_TYPE.STRING_TYPE:
                if (ValueParam == null) {
                    sFieldValue = null;
                } else 
                    sFieldValue = (String) (ValueParam);                
                break;
            case SQL_TYPE.FLOAT_TYPE:
                if(ValueParam == null)
                    fFieldValue = null;
                else
                    fFieldValue = (Float) (ValueParam);
                break;
            case SQL_TYPE.DOUBLE_TYPE:
                if(ValueParam == null)
                    dFieldValue = null;
                else
                    dFieldValue = (Double) (ValueParam);
                break;
            case SQL_TYPE.CALENDAR_TYPE:
                if (ValueParam == null) {
                    cFieldValue = null;
                } else {
                    cFieldValue = (Calendar) (ValueParam);
                }
                break;
            case SQL_TYPE.BOOLEAN_TYPE:
                if(bFieldValue == null)
                    bFieldValue = null;
                else
                    bFieldValue = (Boolean)(ValueParam);
                break;
            case SQL_TYPE.BINARY_TYPE:
                if (ValueParam == null) {
                    binFieldValue = null;
                } else 
                    binFieldValue = (ByteArrayInputStream) (ValueParam);                
                break;
            case SQL_TYPE.LONG_TYPE:
                if(ValueParam == null)
                    longFieldValue = null;
                else
                    longFieldValue = (Long) (ValueParam);
                break;
            case SQL_TYPE.TIMESTAMP_TYPE:
                if(ValueParam == null)
                    timestampFieldValue = null;
                else
                    timestampFieldValue = (Timestamp) ValueParam;
                break;
            case SQL_TYPE.BLOB_TYPE:
                if(ValueParam == null)
                    BlobValue = null;
                else
                    BlobValue = (Blob) ValueParam;
                break;
        }
    }

  
    /**
     * @return the OBJECT_TYPE
     */
    public int getOBJECT_TYPE() {
        return OBJECT_TYPE;
    }

    /**
     * @param OBJECT_TYPE the OBJECT_TYPE to set
     */
    public void setOBJECT_TYPE(int OBJECT_TYPE) {
        this.OBJECT_TYPE = OBJECT_TYPE;
    }

    /**
     * @return the isCustom
     */
    public boolean getIsCustom() {
        return isCustom;
    }

    /**
     * @param isCustom the isCustom to set
     */
    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.INSERT = false;
        this.UPDATE = false;
    }

    /**
     * @return the Size
     */
    public long getSize() {
        return Size;
    }

    /**
     * @param Size the Size to set
     */
    public void setSize(long Size) {
        this.Size = Size;
    }

    /**
     * @return the HumanFieldName
     */
    public String getHumanFieldName() {
        return HumanFieldName;
    }

    /**
     * @param HumanFieldName the HumanFieldName to set
     */
    public void setHumanFieldName(String HumanFieldName) {
        this.HumanFieldName = HumanFieldName;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }
}
