package org.llama.library.database;

import java.sql.*;

public class QueryUpdate {

  private Connection conn;
  private String table;


  public QueryUpdate(Connection conn){
    this.conn = conn;
  }

  public QueryUpdate(Connection conn,String table){
    this.conn = conn;
    this.table = table;
  }

  public int insert(String table,String[] columns,Object[] values) throws
      DAOException {
    String sql = "select * from "+table+" where 1=2";
    DataSet ds = new DataSet(sql);

    for(int i=0;i<columns.length;i++){
      ColumnData col = ds.getColumn(columns[i]);
      if(!values[i].getClass().getName().equals(col.getClassType())){
        throw new DAOException("Invalid column class type!");
      }
    }

    return 0;
  }

}
