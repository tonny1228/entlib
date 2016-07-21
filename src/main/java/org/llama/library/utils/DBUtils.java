package org.llama.library.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库 JDBC支持
 * Created by Tonny on 2016/7/18.
 */
public class DBUtils {

    /**
     * 关闭resultset 不抛出任何异常
     *
     * @param resultSet
     */
    public static void closeQuietly(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            resultSet.close();
        } catch (SQLException e) {

        }
    }

    /**
     * 关闭Statement 不抛出任何异常
     *
     * @param statement
     */
    public static void closeQuietly(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            statement.close();
        } catch (SQLException e) {

        }
    }

    /**
     * 关闭Connection 不抛出任何异常
     *
     * @param connection
     */
    public static void closeQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }
}
