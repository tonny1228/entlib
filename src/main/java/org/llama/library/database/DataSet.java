package org.llama.library.database;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

/**
 * 用于数据库查询
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
public class DataSet {
	
    /**
     * The constant indicating the type for a <code>ResultSet</code> object
     * whose cursor may move only forward.
     * @since 1.2
     */
    public static final int TYPE_FORWARD_ONLY = 1003;

    /**
     * The constant indicating the type for a <code>ResultSet</code> object
     * that is scrollable but generally not sensitive to changes to the data
     * that underlies the <code>ResultSet</code>.
     * @since 1.2
     */
    public static final int TYPE_SCROLL_INSENSITIVE = 1004;

    /**
     * The constant indicating the type for a <code>ResultSet</code> object
     * that is scrollable and generally sensitive to changes to the data
     * that underlies the <code>ResultSet</code>.
     * @since 1.2
     */
    public static final int TYPE_SCROLL_SENSITIVE = 1005;

 	

	/**
	 * 每次查询获取的最大记录数
	 */
	private int fetchSize = 50;

	/**
	 * 现在开始查询的记录,从0开始
	 */
	private int startRow = 0;

	/**
	 * 开始查询的记录页,从1开始
	 */
	private int startPage = 1;

	/**
	 * 与查询相关的所有记录总数
	 */
	private int totalRow = 0;

	/**
	 * 数据库结果集
	 */
	private ResultSet rs = null;

	/**
	 * 数据存储仓库,从数据库取得的记录存在hashmap里
	 */
	private HashMap hm = null;

	/**
	 * 当前记录指针位置，从0开始
	 */
	private int currentRow = -1;

	/**
	 * 此次查询的记录数，应该不大于fetchSize
	 */
	private int currentPageSize = 0;

	/**
	 * 列记录集属性
	 */
	private ColumnsProperty[] prop;

	private String sql;

	private Object[] values;
	
	private boolean isTransaction=false;
	
	private int type = 1004;
	
	
	private Connection conn;
	
	private boolean isOuterConnection;
	
	/**
	 * 初始化数据库查询，并将数据缓存
	 * 
	 * @param conn
	 *            Connection 数据库连接
	 * @param sql
	 *            String 查询语句
	 * @throws SQLException
	 * @throws IOException
	 * @throws DAOException
	 */
	public DataSet(String sql){
		this.sql = sql;
	}

	/**
	 * 初始化数据库查询，并将数据缓存
	 * 
	 * @param conn
	 *            Connection 数据库连接
	 * @param sql
	 *            String 查询语句
	 * @throws SQLException
	 * @throws IOException
	 * @throws DAOException
	 */
	public DataSet(String sql, Object[] value){
		this.sql = sql;
		this.values = value;
	}

	public DataSet(String sql, int fetchRows) {
		this.sql = sql;
		this.fetchSize = fetchRows;
	}

	public DataSet() {
	}

	public DataSet(String sql, Object[] value, int fetchRows) {
		this.sql = sql;
		this.values = value;
		this.fetchSize = fetchRows;
	}
	
	public void getConnection() throws SQLException{
		if(this.conn == null)
			conn = DBConnection.getConnection();
	}
	
	public void freeConnection() throws SQLException{
		if(this.isTransaction)
			return;
		if(!this.isOuterConnection)
			DBConnection.freeConnection(conn);		
		else
			conn.close();
	}
	
	public void executeQuery(String sql) throws DAOException{
		this.sql = sql;
		values = null;
		this.executeQuery();
	}
	public void executeQuery(String sql,Object[] values) throws DAOException{
		this.sql = sql;
		this.values = values;
		this.executeQuery();
	}

	public void executeQuery() throws DAOException {
		reset();
		try {
			this.getConnection();
			System.out.println(sql);
			PreparedStatement stmt = conn.prepareStatement(sql,
					type,
					ResultSet.CONCUR_READ_ONLY); // 生成可滚动查询,便于分页
			for (int i = 0; values != null && i < values.length; i++) {
				stmt.setObject(i + 1, values[i]);
			}
			rs = stmt.executeQuery();
			init(); // 完成数据缓存
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("An error occured while query! Cause by :"
					+ e.getMessage());
		} catch (DAOException e) {
			throw e;
		} finally {
			try {
				this.freeConnection();
			} catch (SQLException ex) {
				throw new DAOException(
						"An error occured while query! Cause by :"
								+ ex.getMessage());
			}
		}
	}

	/**
	 * 完成数据的读取与缓存
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 * @throws SQLException
	 * @throws IOException
	 * @throws DAOException
	 */
	private void init() throws DAOException {
		if (rs == null) {
			throw new DAOException(
					"An error found while query! ResultSet is null！");
		}
		prop = ColumnsProperty.init(rs);
		try {
			this.startRow = (startPage - 1) * fetchSize;// 设置数据库开始指针
			if(this.type != this.TYPE_FORWARD_ONLY ){
				if (rs.last())
					totalRow = rs.getRow();// 获取所有记录数


				if (startRow > 0 && startRow <= totalRow) {
					// 如果不是第一页，也没超出最后一页，数据指针移到设置的指针
					rs.absolute(startRow);
				} else if (startRow <= totalRow) {
					// 如果是第一页，数据指针移到第一条记录前
					rs.beforeFirst();
				} else {
					// 否则返回
					return;
				}
			}else{
				for(int i=0;i<startRow;i++){
					rs.next();
				}
			}

			currentPageSize = 0;
			while (rs.next() && currentPageSize < fetchSize) {
				// 读取一条记录，放到RowData中，放到HashMap中
				
				RowData rd = new RowData(rs, prop);
				hm.put(new Integer(currentPageSize++), rd);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new DAOException("An error found while query! Cause by:"
					+ ex.getMessage());
		} catch (DAOException ex) {
			ex.printStackTrace();
			throw new DAOException("An error found while query! Cause by:"
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new DAOException("An error found while query! Cause by:"
					+ ex.getMessage());
		}
	}

	/**
	 * 移动当前记录集指针
	 * 
	 * @param row
	 *            int 当前行记录位置
	 * @return boolean 位置在记录集中返回true，否则false
	 */
	public boolean absolute(int row) {
		if (row < 0 || row > currentPageSize - 1) {
			return false;
		}
		this.currentRow = row - 1;
		return true;
	}

	/**
	 * 移动当前记录集指针到新下页
	 * 
	 * @param row
	 *            int 当前行记录位置
	 * @return boolean 位置在记录集中返回true，否则false
	 * @throws DAOException 
	 * @throws  
	 */
	public boolean nextPage() throws DAOException {
		try {
			if (conn.isClosed()) {
				throw new DAOException("closed connection.you should use transaction");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		this.startPage++;
		this.executeQuery();
		if(this.hm.size()==0) return false;
		return true;
	}
	
	/**
	 * 重置指针数据
	 *
	 */
	private void reset(){
		startRow = 0;
		totalRow = 0;
		hm = new HashMap();
		currentRow = -1;
		currentPageSize = 0;
	}

	/**
	 * 移动记录指针到最后一条后
	 */
	public void afterLast() {
		// TODO Auto-generated method stub
		this.currentRow = currentPageSize;
	}

	/**
	 * 移动数据库指针到第一条前
	 */
	public void beforeFirst() {
		// TODO Auto-generated method stub
		this.currentRow = -1;
	}

	/**
	 * 删除当前记录，后续版本开发
	 * 
	 * @throws DAOException
	 */
	public void deleteRow() throws DAOException {
		// TODO Auto-generated method stub

	}

	/**
	 * 移动指针到第一条记录
	 * 
	 * @return boolean 如果没有记录返回false
	 */
	public boolean first() {
		// TODO Auto-generated method stub
		if (this.currentPageSize == 0) {
			return false;
		}

		this.currentRow = 0;
		return true;
	}

	/**
	 * 插入一条记录，于后续版本开发
	 * 
	 * @throws DAOException
	 */
	public void insertRow() throws DAOException {
		// TODO Auto-generated method stub
	}

	/**
	 * 判断当前指针是否在最后一条记录之后
	 * 
	 * @return boolean
	 */
	public boolean isAfterLast() {
		// TODO Auto-generated method stub
		if (this.currentRow > this.currentPageSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前指针是否在第一条记录之前
	 * 
	 * @return boolean
	 */
	public boolean isBeforeFirst() {
		// TODO Auto-generated method stub
		if (this.currentRow == -1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前指针是否是第一条
	 * 
	 * @return boolean
	 */
	public boolean isFirst() {
		// TODO Auto-generated method stub
		if (this.currentRow == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前指针是否在最后一条记录
	 * 
	 * @return boolean
	 */
	public boolean isLast() {
		// TODO Auto-generated method stub
		if (this.currentRow == this.currentPageSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * 移动当前指针到最后一条
	 * 
	 * @return boolean 移动是否成功
	 */
	public boolean last() {
		// TODO Auto-generated method stub
		if (this.currentPageSize == 0) {
			return false;
		}

		this.currentRow = this.currentPageSize - 1;
		return true;
	}

	/**
	 * 移动指针到下一条记录
	 * 
	 * @return boolean 移动是否成功
	 */
	public boolean next() {
		// TODO Auto-generated method stub
		if (this.currentPageSize == 0) {
			return false;
		}

		if (this.isLast()) {
			this.afterLast();
			return false;
		}

		if (this.isAfterLast()) {
			return false;
		}

		this.currentRow++;

		return true;
	}

	/**
	 * 移动指针到前一条记录
	 * 
	 * @return boolean 移动是否成功
	 */
	public boolean previous() {
		// TODO Auto-generated method stub
		if (this.currentPageSize == 0) {
			return false;
		}

		if (this.isBeforeFirst()) {
			return false;
		}

		if (this.isFirst()) {
			this.beforeFirst();
			return false;
		}

		this.currentRow--;
		return true;
	}

	/**
	 * 设置查询获取的最大记录数
	 * 
	 * @param rows
	 *            int 记录数
	 */
	public void setFetchSize(int rows) {
		// TODO Auto-generated method stub
		this.fetchSize = rows;
	}

	/**
	 * 记录集是否为空
	 * 
	 * @return boolean
	 */
	public boolean isNull() {
		// TODO Auto-generated method stub
		return this.currentPageSize == 0 ? true : false;
	}

	/**
	 * 打印当前行信息
	 */
	public void printRowInfo() {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		rd.printColumnsInfo();
	}


	/**
	 * 获取当前页的记录数
	 * 
	 * @return int 记录数
	 */
	public int getCurrentPageSize() {
		return currentPageSize;
	}

	/**
	 * 获取每页最大记录数
	 * 
	 * @return int
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * 获取当前的页码
	 * 
	 * @return int
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * 设置当前页码
	 * 
	 * @param startPage
	 *            int
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	/**
	 * 获取所有记录数
	 * 
	 * @return int
	 */
	public int getTotalRow() {
		return totalRow;
	}

	public ColumnsProperty[] getColumns() {
		return prop;
	}


	/**
	 * 获取当前记录的第i列数据的值
	 * 
	 * @param i
	 *            int 列索引，从1开始
	 * @return Object 值
	 */
	public Object getValue(int i) {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		return rd.getColumns(i - 1).getColumnValue();
	}

	/**
	 * 获取当前记录的第i列
	 * 
	 * @param i
	 *            int 列索引,从1开始
	 * @return ColumnData 列记录集
	 */
	public ColumnData getColumn(int i) {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		return rd.getColumns(i - 1);
	}

	/**
	 * 根据列名称获取列的值
	 * 
	 * @param name
	 *            String 列的名称
	 * @return Object 列的值
	 */
	public Object getValue(String name) {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		int i = rd.findColumn(name);
		return rd.getColumns(i - 1).getColumnValue();
	}

	/**
	 * 根据列名称获取列的信息
	 * 
	 * @param name
	 *            String 名称
	 * @return ColumnData 列记录
	 */
	public ColumnData getColumn(String name) {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		int i = rd.findColumn(name);
		return rd.getColumns(i - 1);
	}

	/**
	 * 获取当前记录的所有列的值
	 * 
	 * @return Object[] 存放值得数组
	 */
	public Object[] getValues() {
		RowData rd = (RowData) hm.get(new Integer(this.currentRow));
		Object[] obj = new Object[rd.getColumns().length];
		for (int i = 0; i < obj.length; i++) {
			obj[i] = rd.getColumns(i).getColumnValue();
		}
		return obj;
	}

	public String getString(int i){
		return getColumn(i).stringValue();
	}
	
	public int getInt(int i){
		return getColumn(i).intValue();
	}
	
	public float getFloat(int i){
		return getColumn(i).floatValue();
	}
	
	public Date getDate(int i){
		return getColumn(i).dateValue("yyyy-MM-dd HH:mm:ss");
	}
	
	public double getDouble(int i){
		return getColumn(i).doubleValue();
	}
	
	public boolean getBoolean(int i){
		return getColumn(i).intValue()==0;
	}
	
	public int executeUpdate(String sql,Object[] value) throws DAOException{
		
		int c = StringUtils.countMatches(sql,"?");
		System.out.println(sql);
		
		if(value!= null && c!=value.length){
			throw new DAOException("the count of parameters not mathes the count of values");
		}
		int result=0;
		try {
			this.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (int i = 0;value!= null && i < value.length; i++) {
				if(value[i]==null)
					stmt.setString(i+1,null);
				else if(value[i] instanceof Integer)
					stmt.setInt(i + 1, ((Integer)value[i]).intValue());
				else if(value[i] instanceof Long)
						stmt.setLong(i + 1, ((Long)value[i]).longValue());
				else if(value[i] instanceof BigDecimal)
					stmt.setFloat(i + 1, ((BigDecimal)value[i]).floatValue());
				else if(value[i] instanceof String){
					stmt.setString(i + 1,(String)value[i]);
				}else if(value[i] instanceof StringReader){
					stmt.setClob(i + 1, (StringReader) value[i]);
				}
				else if(value[i] instanceof Double)
					stmt.setDouble(i + 1, ((Double)value[i]).doubleValue());
				else if(value[i] instanceof Float)
					stmt.setFloat(i + 1, ((Float)value[i]).floatValue());
				else if(value[i] instanceof Short)
					stmt.setShort(i + 1, ((Short)value[i]).shortValue());
				else if(value[i] instanceof Clob)
					System.out.println("clob");
				else if(value[i] instanceof byte[]){
					stmt.setBinaryStream(i+1, new ByteArrayInputStream((byte[]) value[i]),((byte[]) value[i]).length);
					//stmt.setBlob(i + 1, new ByteArrayInputStream((byte[]) value[i]));
				}
				else if(value[i] instanceof Timestamp)
					stmt.setTimestamp(i+1, (Timestamp) value[i]);
				else if(value[i] instanceof Date)
					stmt.setTimestamp(i+1, new Timestamp(((Date)value[i]).getTime()));
				else{
					System.out.println(value[i].getClass());
				}
				
			}			
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}finally{
			try {
				this.freeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	

	public int executeUpdate(String sql) throws DAOException{

		return this.executeUpdate(sql, null);
	}
	

	public int executeUpdate() throws DAOException{
		int result=0;
		this.executeUpdate(sql, values);
		return result;
	}
	

	
	public static void main(String[] argv) throws Exception {
	}
	
	public void beginTransaction() throws DAOException{
		isTransaction=true;
		try {
			this.getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Transaction init faild"+e.getMessage());
		}
	}
	
	public void commitTransaction() throws DAOException{
		try {
			conn.commit();
			conn.setAutoCommit(true);
			this.isTransaction=false;
			this.freeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Transaction commit faild:"+e.getMessage());
		}
	}
	
	
	public void rollbackTransaction() throws DAOException{
		try {
			conn.rollback();
			conn.setAutoCommit(true);
			this.isTransaction=false;
			this.freeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Transaction commit faild:"+e.getMessage());
		}
	}
	
	public void updateClob(String content,int i){
		oracle.sql.CLOB clob = (oracle.sql.CLOB)this.getColumn(i).getColumnValue();
        BufferedWriter out;
		try {
			out = new BufferedWriter(clob.getCharacterOutputStream());
	        StringReader in = new StringReader(content);
	        int c;
	        while ( (c = in.read()) != -1) {
	          out.write(c);
	        }
	        in.close();
	        out.close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
		this.isOuterConnection = true;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
