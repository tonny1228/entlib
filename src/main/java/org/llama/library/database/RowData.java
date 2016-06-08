package org.llama.library.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;


/**
 * �м�¼��
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
public class RowData {
	/**
	 * �м�¼��
	 */
	private ColumnData[] columns;

	/**
	 * ��ݿ���
	 */
	private ResultSet rs;

	/**
	 * ��ݿ���Ϣ
	 */
	private ResultSetMetaData metaData;

	/**
	 * ��ȡ��λ��
	 */
	private HashMap hm = new HashMap();

	/**
	 * �м�¼������
	 */
	private ColumnsProperty[] prop;

	/**
	 * ��ʼ����Ϣ
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 */
	public RowData(ResultSet rs, ColumnsProperty[] prop) throws DAOException,
			IOException, SQLException {
		if (rs == null) {
			throw new DAOException("ResultSet is Null!");
		}

		this.prop = prop;

		try {
			this.rs = rs;
			this.metaData = rs.getMetaData();
			columns = new ColumnData[metaData.getColumnCount()];
			initValue();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * ��ʼ������ѯ�����м�¼��
	 * 
	 * @throws SQLException
	 *             sql��ѯ�쳣
	 * @throws IOException
	 *             sql��ȡ�쳣
	 */
	private void initValue() throws SQLException, IOException {
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new ColumnData();
			columns[i].setProp(prop[i]);
			hm
					.put(columns[i].getColumnName().toUpperCase(), new Integer(
							i + 1));
			this.initColumnValue(i);
		}
	}

	/**
	 * �����е�ֵ��ͨ���ж��е����������е�ֵ
	 * 
	 * @param i
	 *            int ������
	 * @throws SQLException
	 *             ��ݿ��ѯ�쳣
	 * @throws IOException
	 *             ���ֶβ�ѯ�쳣
	 */
	private void initColumnValue(int i) throws SQLException, IOException {
		switch (columns[i].getColumnType()) {
		// ����
		case Types.INTEGER:
			columns[i].setColumnValue(new Integer(rs.getInt(i + 1)));
			break;

		// ������
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.BIT:
			columns[i].setColumnValue(new Short(rs.getShort(i + 1)));
			break;

		// ������
		case Types.BIGINT:
			columns[i].setColumnValue(new Long(rs.getLong(i + 1)));
			break;

		// ˫������
		case Types.REAL:
		case Types.DOUBLE:
			columns[i].setColumnValue(new Double(rs.getDouble(i + 1)));
			break;

		// ��������
		case Types.DECIMAL:
		case Types.NUMERIC:
		case Types.FLOAT:
			columns[i].setColumnValue(new Float(rs.getFloat(i + 1)));
			break;

		// ������
		case Types.BOOLEAN:
			columns[i].setColumnValue(new Boolean(rs.getBoolean(i + 1)));
			break;

		// �ַ���
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.LONGVARCHAR:
		case Types.STRUCT:
		case Types.OTHER:
			String str = rs.getString(i + 1);
			columns[i].setColumnValue(str == null ? "" : str);
			break;

		// ������
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.DATE:
			columns[i].setColumnValue(rs.getTimestamp(i + 1));
			break;

		// ����
		case Types.ARRAY:
			columns[i].setColumnValue(rs.getArray(i + 1));
			break;

		// ��������
		case Types.CLOB:
			java.sql.Clob clob = (java.sql.Clob) rs.getClob(i + 1);
			if(clob==null){
				columns[i].setColumnValue(rs.getString(i+1));
				break;
			}
			
			columns[i].setColumnValue(clob);
			break;

		case Types.LONGVARBINARY:
		case Types.BINARY:
			columns[i].setColumnValue(rs.getBytes(i + 1));
			break;

		case Types.BLOB:
			java.sql.Blob blob = rs.getBlob(i + 1);
			BufferedInputStream bis = new BufferedInputStream(blob
					.getBinaryStream());
			byte[] byt = new byte[(int) blob.length()];
			int c = 0;
			bis.read(byt);
			bis.close();
			columns[i].setColumnValue(byt);

			break;

		case Types.NULL:
			columns[i].setColumnValue(null);
			break;
		case Types.REF:
			columns[i].setColumnValue(rs.getRef(i + 1));
			break;
		}
	}

	/**
	 * ���ص�i�еļ�¼��
	 * 
	 * @param i
	 *            int �к�
	 * @return ColumnData ��¼��
	 */
	public ColumnData getColumns(int i) {
		return columns[i];
	}

	/**
	 * ��ȡ������
	 * 
	 * @return ColumnData[]
	 */
	public ColumnData[] getColumns() {
		return columns;
	}

	/**
	 * �����е�λ��
	 * 
	 * @param name
	 *            String �е�����
	 * @return int �е�λ��
	 */
	public int findColumn(String name) {
		Integer i = (Integer) hm.get(name.toUpperCase());
		return i.intValue();
	}

	public void printColumnsInfo() {
		StringBuffer table = new StringBuffer(
				"---------------------------------------------------------------------------------");
		table.append("\r\n");
		table.append("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
		table.append("\r\n");
		table
				.append("---------------------------------------------------------------------------------");
		table.append("\r\n");
		table
				.append("|name\t\t|label\t\t|type\t\t|size\t\t|null\t\t|class\t\t|autoIncrement\t|primaryKey\t|");
		table.append("\r\n");
		table
				.append("---------------------------------------------------------------------------------");
		table.append("\r\n");
		for (int i = 0; i < columns.length; i++) {
			table.append("|" + columns[i].getColumnName() + "\t\t|");
			table.append(columns[i].getColumnLabel() + "\t\t|");
			table.append(columns[i].getColumnType() + "\t\t|");
			table.append(columns[i].getDisplaySize() + "\t\t|");
			table.append(columns[i].isNullable() + "\t\t|");
			table.append(columns[i].getClassType() + "\t\t|");
			table.append(columns[i].isAutoIncrement() + "\t\t|");
			table.append(columns[i].isPrimaryKey() + "\t\t|");
			table.append("\r\n");
		}
		table
				.append("---------------------------------------------------------------------------------");
		System.out.println(table.toString());
	}

}
