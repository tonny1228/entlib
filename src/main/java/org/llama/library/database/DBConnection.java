package org.llama.library.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
	private static DbConnectionBroker dbBroker;

	private static String dbDriver;

	private static String dbServer;

	private static String dbLogin;

	private static String dbPassword;

	private static String logFileString;

	private static int currConnections;

	private static int maxConns;

	private static double timeOut;

	
	/**
	 * ��ȡ���ӳ��е�����
	 * @return ����
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dbBroker.getConnection();
	}
	
	/**
	 * �ͷ����ӳ��е�����
	 * @param conn Ҫ�ͷŵ�����
	 * @throws SQLException
	 */
	public static void freeConnection(Connection conn) throws SQLException {
		dbBroker.freeConnection(conn);
	}
}
