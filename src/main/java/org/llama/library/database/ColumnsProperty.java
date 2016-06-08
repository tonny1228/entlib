package org.llama.library.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ColumnsProperty {

	/**
	 * �е�ֵ��Ӧ��java�������
	 */
	private String classType;


	/**
	 * ��ǰ�е�����
	 */
	private String columnName;

	/**
	 * ��ǰ������ݿ��е��ֶγ���
	 */
	private int displaySize;

	/**
	 * ��ǰ�еı�ʶ
	 */
	private String columnLabel;

	/**
	 * ��ǰ�е�sql���ͣ���Ӧ����java.sql.Types�����е�zhi
	 */
	private int columnType;

	/**
	 * ��ݿ��е�ǰ�е��������
	 */
	private String columnTypeName;

	/**
	 * ��ǰ���Ƿ�Ϊ������
	 */
	private boolean autoIncrement;

	/**
	 * ��ǰ���Ƿ�Ϊ��Сд����
	 */
	private boolean caseSensitive;

	/**
	 * ��ǰ���Ƿ����Ϊ��
	 */
	private boolean nullable;

	/**
	 * ����
	 */
	private String tableName;

	/**
	 * �м�¼������
	 */
	private ColumnsProperty[] prop;

	/**
	 * ��ǰ���Ƿ�Ϊ����
	 */
	private boolean primaryKey;

	public static ColumnsProperty[] init(ResultSet rs) throws DAOException {
		ColumnsProperty[] props=null;
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			props = new ColumnsProperty[metaData
					.getColumnCount()];
			for(int i=0;i<props.length;i++){
				props[i] = new ColumnsProperty();
				props[i].setAutoIncrement(metaData.isAutoIncrement(i+1));
				props[i].setCaseSensitive(metaData.isCaseSensitive(i+1));
				props[i].setClassType(metaData.getColumnClassName(i+1));
				props[i].setColumnLabel(metaData.getColumnLabel(i+1));
				props[i].setColumnName(metaData.getColumnName(i+1));
				props[i].setColumnType(metaData.getColumnType(i+1));
				props[i].setColumnTypeName(metaData.getColumnTypeName(i+1));
				props[i].setDisplaySize(metaData.getColumnDisplaySize(i+1));
				props[i].setNullable(metaData.isNullable(i+1) == 0 ? false : true);
				props[i].setTableName(metaData.getTableName(i+1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e.getMessage());
		}
		return props;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public String getTableName() {
		return tableName;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public String getClassType() {
		return classType;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public int getDisplaySize() {
		return displaySize;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

}
