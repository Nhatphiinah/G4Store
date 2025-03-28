/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import model.Category;
import model.Color;
import model.Product;
import model.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import model.Product_Active;
import model.SaleOff;

public class productDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Product> getProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT c.category_name, p.product_id, p.product_name, p.product_price, "
                + "p.product_describe, p.quantity, p.img, p.category_id, "
                + "(SELECT discount_percentage FROM dbo.product_saleOFF "
                + " WHERE product_id = p.product_id "
                + " AND CAST(GETDATE() AS DATE) BETWEEN CAST(start_date AS DATE) AND CAST(end_date AS DATE) "
                + " ) AS discount_percentage "
                + "FROM product p "
                + "INNER JOIN category c ON p.category_id = c.category_id ";

        //String sqlSize = "Select * from listByCategory Where product_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                float discount = rs.getFloat("discount_percentage"); // Lấy giảm giá ngay trong SQL

                Product p = new Product(c, rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7));
                p.setDiscount(discount);

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductBySize() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    c.category_name, \n"
                + "    p.product_id, \n"
                + "    p.product_name, \n"
                + "    p.product_price, \n"
                + "    p.product_describe, \n"
                + "    p.quantity AS product_quantity, \n"
                + "    p.img, \n"
                + "    p.category_id, \n"
                + "    (SELECT discount_percentage \n"
                + "     FROM dbo.product_saleOFF \n"
                + "     WHERE product_id = p.product_id \n"
                + "     AND CAST(GETDATE() AS DATE) BETWEEN CAST(start_date AS DATE) AND CAST(end_date AS DATE)) AS discount_percentage,\n"
                + "    ps.size, \n"
                + "    ps.quantity AS size_quantity\n"
                + "FROM product p\n"
                + "INNER JOIN category c ON p.category_id = c.category_id\n"
                + "LEFT JOIN product_size ps ON p.product_id = ps.product_id;";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                float discount = rs.getFloat("discount_percentage"); // Lấy giảm giá ngay trong SQL

                Product p = new Product(c, rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7));
                p.setDiscount(discount);

                Size s = new Size(p.getProduct_id(), rs.getString("size"), rs.getInt("size_quantity"));
                List<Size> sizes = new ArrayList<>();
                sizes.add(s);
                p.setSize(sizes);
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getSale(String pid) {
        String sql = "SELECT sale_id, product_id, discount_percentage\n"
                + "FROM dbo.product_saleOFF\n"
                + "WHERE product_id = ? ";

        try (Connection conn = new DBContext().getConnection()) {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pid);
            rs = ps.executeQuery();
            double discount = 0;
            while (rs.next()) {
                discount = rs.getDouble("discount_percentage");
            }
            return discount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> getProductA() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name ,  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img, p.category_id from  \n"
                + "product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where pa.active='True'";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));

                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductASoldOut() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name ,  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img, p.category_id from  \n"
                + "product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where p.quantity<=0";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductANoSoldOut() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name ,  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img, p.category_id from  \n"
                + "product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where p.quantity>0";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductSaleOff() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT c.category_name, p.product_id, p.product_name, p.product_price, "
                + "p.product_describe, p.quantity, p.img, p.category_id, "
                + "s.discount_percentage "
                + "FROM product p "
                + "INNER JOIN category c ON p.category_id = c.category_id "
                + "INNER JOIN product_saleOFF s ON p.product_id = s.product_id "
                + "WHERE CAST(GETDATE() AS DATE) BETWEEN CAST(s.start_date AS DATE) AND CAST(s.end_date AS DATE) "
                + "AND s.discount_percentage > 0";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                float discount = rs.getFloat("discount_percentage");

                Product p = new Product(c, rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7));

                p.setDiscount(discount);

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //search by price
    public List<Product> getProductByPrice(double a, double b) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT c.category_name, p.product_id, p.product_name, p.product_price, p.product_describe, p.quantity, p.img, p.category_id "
                + "FROM product p INNER JOIN category c ON p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id "
                + "WHERE pa.active='True' AND p.product_price >= ? AND p.product_price <= ? "
                + "ORDER BY p.product_price";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, a);
            ps.setDouble(2, b);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return list;
    }

    public List<Product> getProductByPrice(int a) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name ,  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img, p.category_id from  \n"
                + "product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' And p.product_price >=" + a + " Order By p.product_price";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // search by color
    public List<Product> getProductByColor(String a) {
        List<Product> list = new ArrayList<>();
        String sql = "Select Distinct c.category_name ,  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img, p.category_id, co.color\n"
                + "FROM category c \n"
                + "INNER JOIN product p ON p.category_id = c.category_id\n"
                + "INNER JOIN product_color co ON co.product_id = p.product_id\n"
                + " Inner join product_active pa on pa.product_id = p.product_id\n"
                + "WHERE pa.active ='True' and co.color = ?\n"
                + "ORDER BY p.product_id";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(8), rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return list;
    }

    public void insertProduct(Product product) {
        String sql = "insert into product (product_id,category_id,product_name,product_price,product_describe,quantity,img) values(?,?,?,?,?,?,?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getProduct_id());
            ps.setInt(2, product.getCate().getCategory_id());
            ps.setString(3, product.getProduct_name());
            ps.setFloat(4, product.getProduct_price());
            ps.setString(5, product.getProduct_describe());
            ps.setInt(6, product.getQuantity());
            ps.setString(7, product.getImg());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        String sql1 = "INSERT INTO product_size (product_id,size,quantity) VALUES(?,?,?)";
        try {
            conn = new DBContext().getConnection();
            for (Size i : product.getSize()) {
                ps = conn.prepareStatement(sql1);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getSize());
                ps.setInt(3, i.getQuantity());
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }
        String sql2 = "INSERT INTO product_color (product_id,color) VALUES(?,?)";
        try {
            conn = new DBContext().getConnection();
            for (Color i : product.getColor()) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getColor());
                ps.executeUpdate();
            }
        } catch (Exception e) {

        }
        String sql3 = "INSERT INTO product_active (product_id,active) VALUES(?,?)";
        try {
            conn = new DBContext().getConnection();
            Product_Active i = product.getActive();
            ps = conn.prepareStatement(sql3);
            ps.setString(1, product.getProduct_id());
            ps.setBoolean(2, true);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void ProductDelete(String product_id) {
        String sql = "delete from product_size where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        String sql1 = "delete from product_color where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql1);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        String sql2 = "delete from product_img where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql2);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        String sql4 = "delete from product_active where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql4);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        String sq3 = "delete from product where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sq3);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateProduct(Product product, int cid, List<Color> listColor, List<Size> listSize) {
        try {
            // Update product details
            String sql = "UPDATE product SET category_id=?, product_name=?, product_price=?, product_describe=?, quantity=?" + (!product.getImg().equalsIgnoreCase("images/") ? ", img=?" : "") + " WHERE product_id=?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setString(2, product.getProduct_name());
            ps.setFloat(3, product.getProduct_price());
            ps.setString(4, product.getProduct_describe());
            ps.setInt(5, product.getQuantity());
            if (!product.getImg().isEmpty()) {
                ps.setString(6, product.getImg());
            }
            ps.setString(!product.getImg().equalsIgnoreCase("images/") ? 7 : 6, product.getProduct_id());
            ps.executeUpdate();

            // Delete existing sizes and colors and actives
            String deleteSizesSQL = "DELETE FROM product_size WHERE product_id=?";
            ps = conn.prepareStatement(deleteSizesSQL);
            ps.setString(1, product.getProduct_id());
            ps.executeUpdate();

            String deleteColorsSQL = "DELETE FROM product_color WHERE product_id=?";
            ps = conn.prepareStatement(deleteColorsSQL);
            ps.setString(1, product.getProduct_id());
            ps.executeUpdate();
            String deleteActiveSQL = "DELETE FROM product_active WHERE product_id=?";
            ps = conn.prepareStatement(deleteActiveSQL);
            ps.setString(1, product.getProduct_id());
            ps.executeUpdate();
            // Insert new sizes
            String insertSizeSQL = "INSERT INTO product_size (product_id, size,[quantity]) VALUES (?, ?,?)";
            for (Size size : listSize) {
                ps = conn.prepareStatement(insertSizeSQL);
                ps.setString(1, product.getProduct_id());
                ps.setString(2, size.getSize());
                ps.setInt(3, size.getQuantity());
                ps.executeUpdate();
            }
            //Insert new Active
            String insertActiveSQL = "INSERT INTO product_active (product_id, active) VALUES (?, ?)";
            ps = conn.prepareStatement(insertActiveSQL);
            ps.setString(1, product.getProduct_id());
            ps.setString(2, product.getActive().getAcitve());
            ps.executeUpdate();

            // Insert new colors
            String insertColorSQL = "INSERT INTO product_color (product_id, color) VALUES (?, ?)";
            for (Color color : listColor) {
                ps = conn.prepareStatement(insertColorSQL);
                ps.setString(1, product.getProduct_id());
                ps.setString(2, color.getColor());
                ps.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product_Active> getActive() {
        List<Product_Active> list = new ArrayList<>();
        String sql = "select * from product_active";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product_Active(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Size> getSize() {
        List<Size> list = new ArrayList<>();
        String sql = "select * from product_size";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Size(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Color> getColor() {
        List<Color> list = new ArrayList<>();
        String sql = "select * from product_color";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Color(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Category> getCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "select * from category";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Category> getCategoryActive() {
        List<Category> list = new ArrayList<>();
        String sql = "select * from category where status = 1";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Category getCategoryByName(String name) {
        String sql = "select * from Category where category_name = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Category(rs.getInt(1), rs.getString(2));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void insertCategory(String name) {
        String sql = " insert into Category (category_name,status) values(?,1)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Product> getTop10Product() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 10 p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img FROM product p inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' ORDER BY NEWID()";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getTrendProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 5 p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img FROM product p inner join bill_detail bd on p.product_id = bd.product_id inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' \n"
                + "ORDER BY bd.quantity DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getNewProducts(int limit) {;
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP  p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img FROM product p inner join bill_detail bd on p.product_id = bd.product_id\n"
                + "ORDER BY bd.quantity";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductLow() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' ORDER BY p.product_price ASC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductHigh() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id ORDER BY p.product_price DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductAZ() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' ORDER BY p.product_name";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getListByPage(List<Product> list,
            int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Product> getProductByCategory(int category_id) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id inner join product_active pa on pa.product_id = p.product_id Where pa.active ='True' And p.category_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Product getProductByID(String product_id) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_id, c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id WHERE p.product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                return (new Product(c, rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Size> getSizeByID(String product_id) {
        List<Size> list = new ArrayList<>();
        String sql = "select * from product_size where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Size(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Size getQuatityBySize(String product_id, String size) {
        String sql = "select * from product_size where product_id=? And size=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            ps.setString(2, size);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Size(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void UpdateQuatityBySize(String product_id, String size, int quantity) {
        String sql = "UPDATE [dbo].[product_size]\n"
                + "   SET [quantity] = ?"
                + " where product_id=? And size = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setString(2, product_id);
            ps.setString(3, size);
            rs = ps.executeQuery();

        } catch (Exception e) {
        }
    }

    public List<Color> getColorByID(String product_id) {
        List<Color> list = new ArrayList<>();
        String sql = "select * from product_color where product_id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Color(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int CountProduct() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM product";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public int CountUser() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM users where isAdmin = 'False' or isAdmin = 'FALSE' ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public int CountBill() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM bill";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public int CountProductLow() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM product where quantity < 50 ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public List<Product> SearchAll(String text) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img \n"
                + "FROM product p inner join category c on c.category_id = p.category_id inner join product_color ps on p.product_id = ps.product_id Inner Join Product_Active pa On pa.product_id =p.product_id\n"
                + "WHERE pa.active ='True' And product_name LIKE ? OR  product_price LIKE ? OR ps.color LIKE ? OR c.category_name LIKE ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ps.setString(3, text);
            ps.setString(4, "_%" + text + "%_");
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void UpdateQuantity(String id, int quantity) {
        String sql = "UPDATE [dbo].[product]\n"
                + "   SET [quantity] = ?\n"
                + " WHERE [product_id] = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Product> searchWithPaging(String txtSearch, String cID, String sort, String priceRange, String saleOff, Integer pageIndex, int pageSize) {
        List<Product> list = new ArrayList<>();
        String query = "select p.*, ps.discount_percentage from product p\n"
                + "left join product_active pa on p.product_id = pa.product_id\n"
                + "left join category c on p.category_id = c.category_id\n"
                + "left join product_saleOFF ps on p.product_id = ps.product_id AND GETDATE() BETWEEN ps.start_date AND ps.end_date\n"
                + "where pa.active = 'True' \n"
                + "and p.product_name like ?\n"
                + "and ( ? is null or c.category_id = ?)\n"
                + "and p.product_price between ? and ?\n"
                + "AND ((? = 'true' AND ps.product_id IS NOT NULL) OR ? = 'false') order by p.product_price " + (sort.equals("desc") ? "DESC" : "ASC");

        if (pageIndex != null) {
            query += " OFFSET (?*?-?) ROWS FETCH NEXT ? ROWS ONLY";
        }
        try {
            conn = new DBContext().getConnection(); //mo ket noi toi sql
            ps = conn.prepareStatement(query);//nem cau lenh query sang sql

            ps.setString(1, "%" + (txtSearch == null ? "" : txtSearch) + "%");
            ps.setString(2, cID);
            ps.setString(3, cID);
            switch (priceRange) {
                case "0":
                    ps.setFloat(4, 0);
                    ps.setFloat(5, 50000);
                    break;
                case "1":
                    ps.setFloat(4, 50000);
                    ps.setFloat(5, 200000);
                    break;
                case "2":
                    ps.setFloat(4, 200000);
                    ps.setFloat(5, 500000);
                    break;
                case "3":
                    ps.setFloat(4, 500000);
                    ps.setFloat(5, 1000000);
                    break;
                case "4":
                    ps.setFloat(4, 1000000);
                    ps.setFloat(5, Float.MAX_VALUE);
                    break;
            }
            if (saleOff != null && saleOff.equals("true")) {
                ps.setString(6, "true");  // Chỉ lấy sản phẩm có sale
                ps.setString(7, "true");
            } else {
                ps.setString(6, "false"); // Lấy tất cả sản phẩm
                ps.setString(7, "false");
            }

            if (pageIndex != null) {
                ps.setInt(8, pageIndex);
                ps.setInt(9, pageSize);
                ps.setInt(10, pageSize);
                ps.setInt(11, pageSize);
            }

            rs = ps.executeQuery();//chay cau lenh query, nhan ket qua tra ve
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7), rs.getFloat(8));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        productDAO dao = new productDAO();

        List<Product> top10Products = dao.getTop10Product();

        // Print the details of each top 10 product
        for (Product product : top10Products) {
            System.out.println("Product ID: " + product.getProduct_id());
            System.out.println("Product Name: " + product.getProduct_name());
            System.out.println("Product Price: " + product.getProduct_price());
            System.out.println("Product Description: " + product.getProduct_describe());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Image: " + product.getImg());
            System.out.println("----------------------------------");
        }
    }
}
