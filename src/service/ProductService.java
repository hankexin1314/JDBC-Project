package service;

import dao.ProductDao;
import domain.Product;
import utils.C3P0Utils;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {


    public void addProduct(Product p) {

        ProductDao dao = new ProductDao();
        try {
            dao.addProduct(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product findById(int id) {

        ProductDao dao = new ProductDao();
        Product p = null;
        try {
            p = dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    public void updateProduct(Product p) {

        ProductDao dao = new ProductDao();
        try {
            dao.updateProduct(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {

        ProductDao dao = new ProductDao();
        List<Product> ps = null;
        try {
            ps = dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void deleteById(int id) {

        ProductDao dao = new ProductDao();
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(List<Integer> ids) {

        ProductDao dao = new ProductDao();
        try {
            ConnectionManager.start();
            for(int id: ids) {
                dao.deleteByIds(id);
                System.out.println(1/0);
            }
            ConnectionManager.end();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ConnectionManager.rollBack();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
