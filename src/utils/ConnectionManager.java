package utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取链接 开启事务 提交事务 回滚事务
 */
public class ConnectionManager {

    // 定义一个ThreadLocal对象 保存当前线程的链接
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    // 获取链接
    public static Connection getConnection() throws SQLException {
        // 1 先从tl获取链接
        Connection conn = tl.get(); // dao层获取是有链接的
        if(conn == null) { // service第一次获取是没有链接的
            conn = C3P0Utils.getConnection();
            tl.set(conn);
        }

        return conn;
    }

    // 开启事务
    public static void start() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    // 提交事务
    public static void end() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    // 回滚事务
    public static void rollBack() throws SQLException {
        ConnectionManager.getConnection().rollback();
    }

    // 释放资源
    public static void close() throws SQLException {
        ConnectionManager.getConnection().close();
    }
}
