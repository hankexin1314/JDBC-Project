package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3P0Utils {

    //  核心类 ComboPooledDataSource
    private static ComboPooledDataSource ds = new ComboPooledDataSource();

    public static DataSource getDataSource() {
        return ds;
    }


    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    // 释放资源
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
