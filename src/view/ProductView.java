package view;

import domain.Product;
import service.ProductService;
import utils.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProductView {

    public static void main(String[] args) throws SQLException {

        // 1. 显示菜单
        System.out.println("欢迎来到商品管理系统，请输入以下信息进行操作：");
        while(true) {
            System.out.println("C:新增  U:修改  D:删除  DA:批量删除  I:通过id查询  FA:查询所有  Q:退出");

            // 2. 获取用户输入
            Scanner sc = new Scanner(System.in);
            String userSelect = sc.nextLine();
            userSelect = userSelect.toLowerCase();

            // 3.判断用户输入的是哪一个命令
            switch (userSelect) {
                case "c":
                    // 新增
                    addProduct();
                    break;
                case "u":
                    // 修改
                    editProduct();
                    break;
                case "d":
                    // 删除
                    deleteProduct();
                    break;
                case "da":
                    // 删除所有
                    deleteAllProduct();
                    break;
                case "i":
                    // 按id查询
                    findById();
                    break;
                case "fa":
                    // 查询所有
                    findAll();
                    break;
                case "q":
                    // 退出
                    System.out.println("欢迎下次再来。");
                    System.exit(0);
                    break;

                default:
                    System.out.println("不能识别，请重新输入。");
                    break;
            }
        }
    }

    private static void deleteAllProduct() {

        System.out.println("您选择了批量删除商品功能");
        Scanner sc = new Scanner(System.in);
        ProductService service = new ProductService();
        List<Integer> ids = new ArrayList<>();
        while(true) {
            System.out.println("请输入您要删除的商品的编号（-1表示结束）：");
            int deleteId = Integer.parseInt(sc.nextLine());
            if(deleteId == -1) break;
            Product p = service.findById(deleteId);
            if (p != null && !ids.contains(deleteId)) {
                ids.add(deleteId);
                System.out.println("标记商品:" + deleteId);
            }
            else if(p != null && ids.contains(deleteId))
                System.out.println("已经标记过此商品。");
            else { // 没有此商品
                System.out.println("此商品不存在，请重新输入");
            }
        }

        if(ids.isEmpty())
            System.out.println("批量删除已取消！");
        else {
            System.out.println("您已共标记了" + ids.size() + "个待删除的物品");
            System.out.println("您确定都删除吗？Y/N");
            String isOrNot = sc.nextLine().toLowerCase();
            if(isOrNot.equals("y")) {
                service.deleteAll(ids);
                System.out.println("批量删除" + ids.size() + "个商品成功");
            }
            else
                System.out.println("取消批量删除操作！");
        }
    }

    private static void deleteProduct() {

        System.out.println("您选择了删除商品功能");

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您要删除的商品编号：");
        int id = Integer.parseInt(sc.nextLine());

        // 调用service层的查询方法
        ProductService service = new ProductService();
        Product p = service.findById(id);

        if(p == null) { // 查询失败
            System.out.println("查询的商品不存在，请确认后输入。");
        }
        else {
            System.out.println("您删除的商品信息如下：");
            System.out.println(p);
            System.out.println("是否确认删除？(Y/N)");
            String isOrNot = sc.nextLine().toLowerCase();
            if(isOrNot.equals("y")) {
                service.deleteById(id);
                System.out.println("删除商品成功！");
            }
            else {
                System.out.println("操作取消。");
            }
        }
    }

    private static void addProduct() {

        System.out.println("您选择了新增商品功能");

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入新的商品名：");
        String name = sc.nextLine();
        System.out.println("请输入新的商品价格：");
        int price = Integer.parseInt(sc.nextLine());

        // 封装成商品对象
        Product p = new Product(name, price);
        // 调用service层的方法 添加商品
        ProductService service = new ProductService();
        service.addProduct(p);

        System.out.println("添加商品成功！");
    }

    private static void editProduct() {

        System.out.println("您选择了修改商品功能");

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入新的商品编号：");
        int id = Integer.parseInt(sc.nextLine());

        // 查询数据库，如果有告诉用户的商品信息
        ProductService service = new ProductService();
        Product p = service.findById(id);
        if(p == null) { // 没查询到
            System.out.println("您要修改的商品编号" + id + ", 该商品不存在。");
        }
        else {
            System.out.println("您要修改的商品信息如下：");
            System.out.println(p);
            // 修改
            System.out.println("请输入商品新的名字:");
            String newName = sc.nextLine();
            System.out.println("请输入商品新的价格:");
            int newPrice = Integer.parseInt(sc.nextLine());
            // 设置新的名字 和价格
            p.setPname(newName);
            p.setPrice(newPrice);
            // 调用
            service.updateProduct(p);
            System.out.println("修改成功！修改后的信息如下：");
            System.out.println(p);
        }
    }

    private static void findById() {

        System.out.println("您选择了查询商品功能");

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您要查询的商品编号：");
        int id = Integer.parseInt(sc.nextLine());

        // 调用service层的查询方法
        ProductService service = new ProductService();
        Product p = service.findById(id);

        if(p == null) { // 查询失败
            System.out.println("查询的商品不存在，请确认后输入。");
        }
        else {
            System.out.println("您查询的商品是" + p);
            System.out.println("查询商品成功！");
        }
    }

    private static void findAll() {

        System.out.println("您选择了查询所有功能");

        ProductService service = new ProductService();
        List<Product> ps = service.findAll();
        if(!ps.isEmpty()) {
            for (Product p : ps)
                System.out.println(p);
            System.out.println("查询所有商品成功！");
        }
        else { // 返回的是空list 而不是null
            System.out.println("数据库中没有商品！");
        }
    }

}
