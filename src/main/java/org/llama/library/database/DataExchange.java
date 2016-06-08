package org.llama.library.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;

import oracle.sql.CLOB;

public class DataExchange {
	private DatabaseConnection source;

	private DatabaseConnection target;

	public DataExchange(DatabaseConnection source, DatabaseConnection target) {
		this.source = source;
		this.target = target;
	}

	public void exchange(String table, String table2) throws DAOException {
		exchange("select * from " + table, table2, null);
	}

	public void exchangeQuery(String sql, String table2) throws DAOException {
		exchange(sql, table2, null);
	}

	/**
	 * 
	 * @param query
	 * @param table2
	 * @param notCopy ���������ֶ�
	 * @throws DAOException
	 */
	public void exchange(String query, String table2, String notCopy) throws DAOException {
		if (notCopy == null)
			notCopy = "";
		int nc = notCopy.equals("") ? 0 : notCopy.split(",").length;
		System.out.println("copy data from to " + table2);
		DataSet ds1 = new DataSet();
		DataSet ds2 = new DataSet();
		try {
			ds1.setConnection(source.getConnection());
			ds2.setConnection(target.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ds1.beginTransaction();
		ds1.executeQuery(query);
		ds2.beginTransaction();
		ds2.executeQuery("select * from " + table2 + " where 1=2");
		while (ds1.next()) {
			System.out.println(ds1.getValue(1));
			ColumnsProperty[] columns = ds1.getColumns();

			StringBuffer col = new StringBuffer();
			StringBuffer val = new StringBuffer();
			Object parameter[] = new Object[columns.length - nc - 2];
			for (int i = 0, j = 0; i < columns.length; i++) {
				String name = ds1.getColumn(i + 1).getColumnName().toLowerCase();
				if (notCopy.indexOf("" + name + "") >= 0)
					continue;
				if (name.equals("html_content") || name.equals("text_content")) {
					col.append("").append(name).append("");
					val.append("empty_clob()");
					col.append(",");
					val.append(",");
					continue;
				} else
					parameter[j++] = ds1.getValue(i + 1);
				col.append("").append(name).append("");
				val.append("?");
				if (i < columns.length - 1) {
					col.append(",");
					val.append(",");
				}
			}
			StringBuffer sql = new StringBuffer("insert into ").append(table2).append("(").append(col)
					.append(") values(").append(val).append(")");
			ds2.executeUpdate(sql.toString(), parameter);
			ds2.executeQuery("select html_content,text_content from " + table2 + " where id='" + parameter[0]
					+ "' for update");
			ds2.next();
			oracle.sql.CLOB c = (CLOB) ds2.getValue(1);
			try {
				Writer w = c.getCharacterOutputStream();
				w.write(ds1.getString(13));
				w.flush();
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c = (CLOB) ds2.getValue(2);
			try {
				Writer w = c.getCharacterOutputStream();
				w.write(ds1.getString(14));
				w.flush();
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ds1.isLast()) {
				System.out.println("next page");
				if (!ds1.nextPage())
					break;
			}
		}

		ds1.commitTransaction();
		ds2.commitTransaction();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		DatabaseConnection source = new DatabaseConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/elearning?useUnicode=true&amp;characterEncoding=gbk", "root", "123456");
		DatabaseConnection target = new DatabaseConnection("oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@192.168.100.128:1521:orcl", "TRAINING", "TRAINING");
		DataExchange exchange = new DataExchange(source, target);
		try {
			// exchange.exchange("addresslist", "addresslist");
			exchange.exchangeQuery(
					"select ID, TITLE, KEYWORD, SUMMARY, INPUT_TIME, RELEASE_TIME, EXPIRED_TIME, ORDER_NO, catalog_id, STATUS, CAN_COMMENT, IMAGE, HTML_CONTENT, TEXT_CONTENT, TEXT_COUNT, INPUTER_DEPT, INPUTER, ADMIN, UPDATE_TIME, hit, tag FROM cms_article",
					"cms_article");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
