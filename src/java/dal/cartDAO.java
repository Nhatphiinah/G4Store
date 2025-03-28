/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Item;
import model.Product;
import model.User;

/**
 *
 * @author PhuLTBCE180808
 */
public class cartDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    public void AddCart(Cart c ){
        String sql = " INSERT INTO [dbo].[cart]\n" +
"           ([product_id]\n" +
"           ,[product_name]\n" +
"           ,[product_img]\n" +
"           ,[product_price]\n" +
"           ,[total]\n" +
"           ,[quantity]\n" +
"           ,[user_id],[size]\n" +
"      ,[color])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?,?,?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            for (Item item : c.getItems()) {
                Product p = item.getProduct();
                ps.setString(1, p.getProduct_id());
                ps.setString(2,p.getProduct_name());
                ps.setString(3, p.getImg());
                ps.setFloat(4, p.getProduct_price());
                ps.setFloat(5, p.getProduct_price() * item.getQuantity());
                ps.setFloat(6, item.getQuantity());
                ps.setString(7, c.getUserId());
                ps.setString(8, item.getSize());
                ps.setString(9, item.getColor());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Cart GetCartByUser(int userId){
        String sql = "SELECT [cart_id]\n" +
                    "      ,[product_id]\n" +
                    "      ,[product_name]\n" +
                    "      ,[product_img]\n" +
                    "      ,[product_price]\n" +
                    "      ,[total]\n" +
                    "      ,[quantity]\n" +
                    "      ,[user_id],[size]\n" +
"      ,[color]\n" +
                    "  FROM [ShopYouAndMeVersionFinal].[dbo].[cart]\n" +
                    "  Where user_id = ?";
        
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            Cart c = new Cart();
            List<Item> items = new ArrayList<>();
            while(rs.next()) {
              c.setId(rs.getInt("cart_id"));
              c.setUserId(rs.getString("user_id"));
              
              Item item = new Item();
              
              Product p = new Product();
              p.setProduct_id(rs.getString("product_id"));
              p.setProduct_name(rs.getString("product_name"));
              p.setImg(rs.getString("product_img"));
              p.setProduct_price(rs.getFloat("product_price"));
              
              item.setProduct(p);
              item.setQuantity(rs.getInt("quantity"));
              item.setColor(rs.getString("color"));
              item.setSize(rs.getString("size"));
              items.add(item);
            }
            c.setItems(items);
            return c;
        } catch (Exception e) {
        }
        return null;
    }
   public Cart GetUserProductInCart(String productId, String userId){
        String sql = "SELECT [cart_id]\n" +
                    "      ,[product_id]\n" +
                    "      ,[product_name]\n" +
                    "      ,[product_img]\n" +
                    "      ,[product_price]\n" +
                    "      ,[total]\n" +
                    "      ,[quantity]\n" +
                    "      ,[user_id]\n" +
                    "  FROM [ShopYouAndMeVersionFinal].[dbo].[cart]\n" +
                    "  Where product_id = ? And user_id = ?";
        
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, userId);
            rs = ps.executeQuery();
            if(rs.next()) {
              Cart c = new Cart();
              c.setId(rs.getInt("cart_id"));
              c.setUserId(rs.getString("user_id"));
              
              List<Item> items = new ArrayList<>();
              
              Item item = new Item();
              Product p = new Product();
              p.setProduct_id(rs.getString("product_id"));
              p.setProduct_name(rs.getString("product_name"));
              p.setImg(rs.getString("product_img"));
              p.setProduct_price(rs.getFloat("product_price"));
              
              item.setProduct(p);
              item.setQuantity(rs.getInt("quantity"));
              
              items.add(item);
              items.add(item);
              c.setItems(items);
              
              return c;
            }
        } catch (Exception e) {
        }
        return null;
    }  
    public void UpdateQuantity(Cart c){
        String sql = "UPDATE [dbo].[cart]\n" +
                        "   SET [quantity] = ?,\n" +
                        "	[total] = ?" +
                        " WHERE product_id = ? And user_id = ?";
        
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            for (Item item : c.getItems()) {
                Product p = item.getProduct();
                ps.setFloat(1, item.getQuantity());
                ps.setFloat(2, p.getProduct_price() * item.getQuantity() );
                ps.setString(3, p.getProduct_id());
                ps.setString(4, c.getUserId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public  void DeleteProductInCart(String productId, String userId){
        String sql = "DELETE FROM [dbo].[cart]\n" +
                        "WHERE product_id = ? And user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, userId);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
}
