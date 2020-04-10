package dao;

import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductDao {

    public void addProduct(Product p) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        Object[] params = {p.getPname(), p.getPrice(), "1", "c002"};
        String sql = "insert into products (pname, price, flag, category_id) values (?, ?, ?, ?)";
        qr.update(sql, params);
    }

    public Product findById(int id) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        Object[] params = {id};
        String sql = "select * from products where pid = ?";
        Product p = qr.query(sql, new BeanHandler<>(Product.class), params);

        return p;
    }

    public void updateProduct(Product p) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        Object[] params = {p.getPname(), p.getPrice(), p.getPid()};
        String sql = "update products set pname = ?, price = ? where pid = ?";
        qr.update(sql, params);
    }

    public List<Product> findAll() throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select * from products";
        List<Product> ps = qr.query(sql, new BeanListHandler<>(Product.class));

        return ps;
    }

    public void deleteById(int id) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        Object[] params = {id};
        String sql = "delete from products where pid = ?";
        qr.update(sql, params);
    }

    public void deleteByIds(int id) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        Object[] params = {id};
        String sql = "delete from products where pid = ?";
        qr.update(ConnectionManager.getConnection(), sql, params);
    }
}
