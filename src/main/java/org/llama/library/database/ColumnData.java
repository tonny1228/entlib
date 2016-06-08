package org.llama.library.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;

import oracle.sql.CLOB;

import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.utils.DateUtils;

/**
 * ��ȡ��ǰ�е���Ϣ��ֵ
 * <p>
 * Title: TonnyWorks
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Tonny
 * @version 1.0
 */
public class ColumnData {

	private int index;

	private Object columnValue;

	private ColumnsProperty prop;

	/**
	 * �������ֽ�ֵ
	 * 
	 * @return byte����
	 * @throws DAOException
	 */
	public byte[] binaryValue() throws DAOException {
		if (this.columnValue == null)
			return null;

		if (!(columnValue instanceof byte[]))
			throw new DAOException("Can not cast the value " + columnValue + " to [byte[]]");
		return (byte[]) this.columnValue;
	}

	/**
	 * byte ֵ
	 * 
	 * @return byte ֵ
	 * @throws NumberFormatException
	 */
	public byte byteValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		return NumberUtils.toByte(this.columnValue.toString(), (byte) 0);
	}

	/**
	 * ����ֵ
	 * 
	 * @return
	 */
	public java.util.Date dateValue() {
		if (this.columnValue == null)
			return null;

		return this.dateValue("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ����ֵ
	 * 
	 * @param pattern ���ڸ�ʽ
	 * @return
	 */
	public java.util.Date dateValue(String pattern) {
		if (this.columnValue == null)
			return null;

		switch (prop.getColumnType()) {
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.DATE:
			Timestamp time = (Timestamp) this.columnValue;
			time.getTime();
			return new java.util.Date(time.getTime());
		default:
			return DateUtils.toDate(this.columnValue.toString(), pattern);
		}
	}

	/**
	 * doubleֵ
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	public double doubleValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		return NumberUtils.toDouble(this.columnValue.toString(), 0.0);
	}

	/**
	 * ���ʽ��doubleֵ
	 * 
	 * @param pattern
	 * @return
	 * @throws NumberFormatException
	 */
	public double doubleValue(String pattern) throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		DecimalFormat df = new DecimalFormat(pattern);
		return Double.parseDouble(df.format(NumberUtils.toDouble(this.columnValue.toString(), 0.0)));
	}

	/**
	 * floatֵ
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	public float floatValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		return NumberUtils.toFloat(this.columnValue.toString(), (float) 0.0);
	}

	/**
	 * ���ʽ��floatֵ
	 * 
	 * @param pattern ���ָ�ʽ
	 * @return
	 * @throws NumberFormatException
	 */
	public float floatValue(String pattern) throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		DecimalFormat df = new DecimalFormat(pattern);
		return Float.parseFloat(df.format(NumberUtils.toFloat(this.columnValue.toString(), (float) 0.0)));
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * int ��ʽֵ
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	public int intValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		return (int) this.longValue();
	}

	/**
	 * long ֵ
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	public long longValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		switch (prop.getColumnType()) {
		case Types.INTEGER:
		case Types.BIGINT:
		case Types.BIT:
		case Types.SMALLINT:
		case Types.TINYINT:
			return NumberUtils.toLong(this.columnValue.toString(), 0);
		case Types.BOOLEAN:
			Boolean x = (Boolean) columnValue;
			if (x.booleanValue())
				return 1;
			else
				return 0;
		case Types.DECIMAL:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.NUMERIC:
		case Types.REAL:
			double d = this.doubleValue("#0.0");
			return new Double(d).longValue();
		default:
			return NumberUtils.toLong(this.columnValue.toString(), 0);
		}
	}

	/**
	 * ��������
	 * 
	 * @param index
	 */
	public void setIndex(int index) {

		this.index = index;
	}

	/**
	 * short ֵ
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	public short shortValue() throws NumberFormatException {
		if (this.columnValue == null)
			throw new NumberFormatException();

		return (short) this.longValue();
	}

	/**
	 * String ֵ
	 * 
	 * @return
	 */
	public String stringValue() {
		if (this.columnValue == null)
			return null;
		try {
			if (this.columnValue instanceof CLOB) {
				CLOB clob = (CLOB) this.columnValue;
				BufferedReader in;
				try {
					in = new BufferedReader(clob.getCharacterStream());
					StringWriter sw = new StringWriter();
					int c;
					while ((c = in.read()) != -1) {
						sw.write(c);
					}
					in.close();
					return sw.toString();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (java.lang.NoClassDefFoundError e) {
		}
		if (this.columnValue instanceof byte[]) {
			return new String((byte[]) this.columnValue);
		}
		return this.columnValue.toString();
	}

	/**
	 * object
	 * 
	 * @return
	 */
	public Object getColumnValue() {
		return columnValue;
	}

	/**
	 * ����ֵ
	 * 
	 * @param columnValue
	 */
	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	/**
	 * ��ȡ������
	 * 
	 * @return
	 */
	public ColumnsProperty getProp() {
		return prop;
	}

	/**
	 * ����������
	 * 
	 * @param prop
	 */
	public void setProp(ColumnsProperty prop) {
		this.prop = prop;
	}

	public String getClassType() {
		// TODO Auto-generated method stub
		return prop.getClassType();
	}

	public String getColumnLabel() {
		// TODO Auto-generated method stub
		return prop.getColumnLabel();
	}

	public String getColumnName() {
		// TODO Auto-generated method stub
		return prop.getColumnName();
	}

	public int getColumnType() {
		// TODO Auto-generated method stub
		return prop.getColumnType();
	}

	public String getColumnTypeName() {
		// TODO Auto-generated method stub
		return prop.getColumnTypeName();
	}

	public int getDisplaySize() {
		// TODO Auto-generated method stub
		return prop.getDisplaySize();
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return prop.getTableName();
	}

	public boolean isAutoIncrement() {
		// TODO Auto-generated method stub
		return prop.isAutoIncrement();
	}

	public boolean isCaseSensitive() {
		// TODO Auto-generated method stub
		return prop.isCaseSensitive();
	}

	public boolean isNullable() {
		// TODO Auto-generated method stub
		return prop.isNullable();
	}

	public boolean isPrimaryKey() {
		// TODO Auto-generated method stub
		return prop.isPrimaryKey();
	}

	public void setAutoIncrement(boolean autoIncrement) {
		// TODO Auto-generated method stub
		prop.setAutoIncrement(autoIncrement);
	}

	public void setCaseSensitive(boolean caseSensitive) {
		// TODO Auto-generated method stub
		prop.setCaseSensitive(caseSensitive);
	}

	public void setClassType(String classType) {
		// TODO Auto-generated method stub
		prop.setClassType(classType);
	}

	public void setColumnLabel(String columnLabel) {
		// TODO Auto-generated method stub
		prop.setColumnLabel(columnLabel);
	}

	public void setColumnName(String columnName) {
		// TODO Auto-generated method stub
		prop.setColumnName(columnName);
	}

	public void setColumnType(int columnType) {
		// TODO Auto-generated method stub
		prop.setColumnType(columnType);
	}

	public void setColumnTypeName(String columnTypeName) {
		// TODO Auto-generated method stub
		prop.setColumnTypeName(columnTypeName);
	}

	public void setDisplaySize(int displaySize) {
		// TODO Auto-generated method stub
		prop.setDisplaySize(displaySize);
	}

	public void setNullable(boolean nullable) {
		// TODO Auto-generated method stub
		prop.setNullable(nullable);
	}

	public void setPrimaryKey(boolean primaryKey) {
		// TODO Auto-generated method stub
		prop.setPrimaryKey(primaryKey);
	}

	public void setTableName(String tableName) {
		// TODO Auto-generated method stub
		prop.setTableName(tableName);
	}

}
